package com.analysis.service.scheduletask;

import com.analysis.common.crawler.CrawlerWrapper;
import com.analysis.common.crawler.ProxyIpCrawler;
import com.analysis.common.utils.RedisUtil;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * @author lvshuzheng
 * @className GetAgentIPPool
 * @description 获取代理ip，写入redis
 * @date 2020/4/23
 */
//@Component
public class GetAgentIPPoolTask {
    @Value("${financial.proxyip.pool.url}")
    private String proxyIpPoolUrl;
    @Autowired
    private RedisUtil redisUtil;

    private static Logger LOGGER = LoggerFactory.getLogger(GetAgentIPPoolTask.class);

    @Scheduled(cron = "0 */1 9-12,13-16 * * ? ")
    public void getProxyIp(){
        LOGGER.info("开始爬取。。。");
        CrawlerWrapper proxyIpCrawler = new ProxyIpCrawler();
        for(int i = 1;i <= 5;i++){
            Document document = proxyIpCrawler.crawling(proxyIpPoolUrl + i);
            if(document == null){
                LOGGER.info(proxyIpPoolUrl + i + "，爬取失败，进入重试");
                document = proxyIpCrawler.crawling(proxyIpPoolUrl + i);
            }
            if (document == null){
                LOGGER.info(proxyIpPoolUrl + i + "，爬取失败");
            }else {
                Set<String> ipSet =  (Set)proxyIpCrawler.analysis(document);
                //redisUtil.sSet("proxyIpPool",ipSet);
                ipSet.stream().forEach(host->{
                    String ip = host.split(":")[1].replace("//","");
                    String port = host.split(":")[2];
                    redisUtil.sSet("proxyIpPool",ip + ":" + port);
                });
                LOGGER.info("ipSet插入redis成功。。。");
            }

        }


    }
}
