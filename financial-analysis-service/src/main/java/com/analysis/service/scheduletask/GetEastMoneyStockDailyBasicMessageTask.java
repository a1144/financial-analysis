package com.analysis.service.scheduletask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.analysis.common.crawler.CrawlerWrapper;
import com.analysis.common.crawler.EastMoneyStockDailyBasicMessageCrawler;
import com.analysis.common.utils.HttpClientUtils;
import com.analysis.common.utils.RabbitMQSender;
import com.analysis.common.utils.RedisUtil;
import com.analysis.service.IStockDailyBasicMessageService;
import com.analysis.service.IUserService;
import com.analysis.service.IUserSubscribeService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lvshuzheng
 * @className GetEastMoneyStockDailBasicMessageTask
 * @description
 * @date 2020/4/26
 */
@Component
public class GetEastMoneyStockDailyBasicMessageTask {
    private static Logger LOGGER = LoggerFactory.getLogger(GetEastMoneyStockDailyBasicMessageTask.class);
    @Autowired
    private IStockDailyBasicMessageService stockDailBasicMessageService;
    @Autowired
    private IUserSubscribeService userSubscribeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Value("${eastMoney.daily.basic.message.url}")
    private String eastMoneyBasicMessageUrl;
    @Value("${eastMoney.stock.riseOrFall.basic.message.url}")
    private String eastStockRiseOrFallUrl;

    //@Scheduled(cron = "0 10 15 ? * MON-FRI")
    @Scheduled(cron = "0 35 16 ? * MON-FRI")
    //@Scheduled(cron = "*/10 * * * * ? ")
    //@Scheduled(cron = "0/10 * 9-12,13-16 * * ? ")
    public void getEastMoneyStockDailyBasicMessage() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("financial-analysis-daily-message-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 100000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1000), threadFactory);
        Map<String, Object> paramsMap = new HashMap<>();
        //查询超级账户订阅的内容
        paramsMap.put("userCode", "000");
        Map<String, Object> userSubscribeMap = userSubscribeService.select(paramsMap);
        String stockCodesStr = (String) ((Map) ((List) userSubscribeMap.get("rows")).get(0)).get("stock_code");
        List<String> stockCodeList = JSON.parseArray(stockCodesStr, String.class);
        List<String> stockCodeListByFormat = new ArrayList<>();
        stockCodeList.stream().forEach(stockCode -> {
            //前缀补齐0
            while (stockCode.length() < 6) {
                stockCode = 0 + stockCode;
            }
            stockCodeListByFormat.add(stockCode);
        });

        for (String stockCode : stockCodeListByFormat) {
            executor.execute(() -> {
                System.out.println(stockCode);
                String url = "";
                //6开头 沪市
                if ("6".equals(stockCode.substring(0, 1))) {
                    url = eastMoneyBasicMessageUrl + "1." + stockCode;
                } else {
                    url = eastMoneyBasicMessageUrl + "0." + stockCode;
                }
                CrawlerWrapper eastMoneyStockDailyBasicMessageCrawler = new EastMoneyStockDailyBasicMessageCrawler();
                JSONObject eastMoneyStockDailBasicMessageJsonObject = eastMoneyStockDailyBasicMessageCrawler.httpGet(url);
                //JSONObject eastMoneyStockDailBasicMessageJsonObject = eastMoneyStockDailyBasicMessageCrawler.httpGet("https://in-search-appstore.vivoglobal.com/app/2.0/search/search.do?pageIndex=1&searchType=206&screensize=1080_2340&ver=1180&density=3.0&nt=WIFI&langCode=zh&elapsedtime=1818016045&mcc=460&an=9&st2=0&st1=1817887091&av=28&u=1234567890&countryCode=IN&sn2=46002&sn1=46002&imei=295428413700011&model=V1829A&dataVer=2&s=2%257C622055182&keyWord=aa");
                Map<String, Object> dataMap = (Map<String, Object>) eastMoneyStockDailyBasicMessageCrawler.analysis(eastMoneyStockDailBasicMessageJsonObject);
                stockDailBasicMessageService.insert(dataMap);
                LOGGER.info(stockCode + ": 爬取写入成功");


            });
        }
        //String url = "http://push2.eastmoney.com/api/qt/stock/get?fields=f57,f58,f43,f169,f170,f44,f45,f46,f51,f52,f60,f47,f48,f49,f161,f50,f71,f80,f127,f128,f129,f140,f143,f146,f149,f193,f194,f195,f196,f197,f168,f191,f192&secid=0.300292&invt=2&fltt=2";
        //return;
    }

    @Scheduled(cron = "0 0 9 ? * MON-FRI")
    //@Scheduled(cron = "*/10 * * * * MON-FRI")
    public void getSubscribeMessage() {
        Map<String, Object> responseMap = userSubscribeService.select(new HashMap<>());
        List<Map<String, Object>> subscribeMessageList = (List) responseMap.get("rows");

        Map<String, List<String>> userSubscribeMap = new HashMap<>();
        subscribeMessageList.stream().forEach(subscribeMessage -> {

            String userCode = (String) subscribeMessage.get("user_code");
            if ("000".equals(userCode)) {
                return;
            }
            JSONArray stockCodeArray = JSONArray.parseArray((String) subscribeMessage.get("stock_code"));
            stockCodeArray.stream().forEach(stockCode -> {
                //List<String> userCodes = userSubscribeMap.getOrDefault(stockCode + "",new ArrayList<>());
                while (stockCode.toString().length() < 6) {
                    stockCode = "0" + stockCode;
                }
                List<String> userCodes = userSubscribeMap.getOrDefault(stockCode.toString(), new ArrayList<>());
                userCodes.add(userCode);
                userSubscribeMap.put(stockCode + "", userCodes);
            });
        });
        redisUtil.del("userSubscribe");
        userSubscribeMap.forEach((stockCode, userCodes) -> {
            redisUtil.hset("userSubscribe", stockCode, userCodes);
        });
    }

    @Scheduled(cron = "*/10 * * * * MON-FRI")
    public void getRatioAndSendEmail() {
        /*rabbitMQSender.sendByRoutingKey("test1","this is test1");
        rabbitMQSender.sendByExchangeAndRoutingKey("fanout.test.exchange","","this is fanoutTest");
        rabbitMQSender.sendByExchangeAndRoutingKey("direct.exchange.test","direct12345","this is directTest");
        rabbitMQSender.sendByExchangeAndRoutingKey("topic.exchange.test","test.top","this is topTest");
        System.out.println();*/
        //redisUtil.hget("userSubscribe");
        Map<Object, Object> userSubscribeMap = redisUtil.hmget("userSubscribe");
        Set<String> stockCodeSet = new HashSet<>();

        userSubscribeMap.forEach((stockCode,userCodes) -> {
            String url = "";
            //6开头 沪市
            if ("6".equals((stockCode + "").substring(0, 1))) {
                url = eastStockRiseOrFallUrl + "1." + stockCode;
            } else {
                url = eastStockRiseOrFallUrl + "0." + stockCode;
            }
            CrawlerWrapper eastMoneyStockDailyBasicMessageCrawler = new EastMoneyStockDailyBasicMessageCrawler();
            JSONObject response = eastMoneyStockDailyBasicMessageCrawler.httpGet(url);
            Object riseOrFallBasicMessage = eastMoneyStockDailyBasicMessageCrawler.analysis(response);
            BigDecimal riseOrFallRate = (BigDecimal) ((Map) riseOrFallBasicMessage).get("riseOrFallRate");
            if (riseOrFallRate.intValue() >= 2 || riseOrFallRate.intValue() <= -2) {
                System.out.println("波动较大: " + riseOrFallBasicMessage);
                Set abnormalStockCodeSet = redisUtil.sGet("ratioAbnormal");
                if (abnormalStockCodeSet != null) {
                    if(!abnormalStockCodeSet.contains(stockCode)){
                        redisUtil.sSetAndTime("ratioAbnormal", 10 * 60, stockCode);
                        //封装map到rabbitmq
                        Map<String,Object> rabbitmqMessage = new HashMap<>();
                        rabbitmqMessage.put("name","股票波动过大发送邮件消息体");
                        rabbitmqMessage.put("message",riseOrFallBasicMessage);
                        List<Map<String,String>> userMessages = new ArrayList<>();
                        //通过userCode查询邮箱
                        List<Map<String,Object>> users = (List)userService.selectByUserCodes((List)userCodes).get("users");
                        users.stream().forEach(user -> {
                            String userEmail = (String)user.get("email");
                            String userName = (String)user.get("user_name");
                            Map<String,String> userMessage = new HashMap<>();
                            userMessage.put("userName",userName);
                            userMessage.put("userEmail",userEmail);
                            userMessages.add(userMessage);
                        });
                        rabbitmqMessage.put("userMessages",userMessages);
                        rabbitMQSender.sendByExchangeAndRoutingKey("financial.analysis.abnormal.ratio.exchange","abnormal.ratio.routingKey",rabbitmqMessage);

                    }else{
                        LOGGER.info("十分钟之内已发送过邮件");
                    }

                } else {
                    redisUtil.sSetAndTime("ratioAbnormal", 10 * 60, stockCode);
                }


            }

        });

    }

}
