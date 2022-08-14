package com.analysis.service.scheduletask;

import com.analysis.common.utils.RedisUtil;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author lvshuzheng
 * @className CheckAgentIpPoolTask
 * @description 定时检查redis代理池中ip可用性
 * @date 2020/4/24
 */
//@Component
public class CheckAgentIpPoolTask {
    @Autowired
    private RedisUtil redisUtil;

    static ThreadFactory timeThreadFactory = new ThreadFactoryBuilder().setNameFormat("financial-analysis-proxyIp-time-%d").build();
    static ThreadPoolExecutor timeExecutor = new ThreadPoolExecutor(5, 5, 100000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1000), timeThreadFactory);

    static Logger LOGGER = LoggerFactory.getLogger(CheckAgentIpPoolTask.class);

    @Scheduled(cron = "*/30 * 9-12,13-16 * * ?")
    public void checkIPAndPort(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("financial-analysis-proxyIp-check-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 100000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1000), threadFactory);
        Set<String> ipSet = (Set)redisUtil.sGet("proxyIpPool");
        Set<String> ipSetByCheck = Sets.newConcurrentHashSet();

        ipSet.stream().forEach(host -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Callable<Result> work = new Callable<Result>(){
                        @Override
                        public Result call() throws Exception {
                            Socket socket = new Socket();
                            String ip = host.split(":")[0];
                            Integer port = Integer.valueOf(host.split(":")[1]);
                            socket.connect(new InetSocketAddress(ip,port));
                            return new Result() {
                                @Override
                                public void setSystemId(String systemId) {
                                }
                                @Override
                                public String getSystemId() {
                                    return "123";
                                }
                            };
                        }
                    };
                    Future<Result> future = timeExecutor.submit(work);
                    try {
                        Result result = future.get(3000,TimeUnit.MILLISECONDS);
                        ipSetByCheck.add(host);
                        System.out.println("ip可联通");
                    }catch (TimeoutException e) {
                        LOGGER.info(host + ":联通超时");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        executor.shutdown();
        while (true){
            if (executor.isTerminated()) {
                break;
            }
        }
        redisUtil.del("proxyIpPool");
        ipSetByCheck.stream().forEach(host->{
            redisUtil.sSet("proxyIpPool",host);
        });
    }

}
