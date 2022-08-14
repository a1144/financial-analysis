package com.analysis.common;

import com.analysis.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class FinancialAnalysisCommonApplicationTests {
    @Resource
    private RedisUtil redisUtil;
    @Value("${financial.proxyip.pool.url}")
    private String proxyIpUrl;
    @Test
    void contextLoads() {
    }

    @Test
    void testRedisAdd(){
        //redisUtil.set("testKey","testValue");
        System.out.println(redisUtil.get("testKey"));
        /*Set<String> set = new HashSet<>();
        set.add("2");*/
        redisUtil.sSet("testSet",2);
        System.out.println(proxyIpUrl);
    }

}
