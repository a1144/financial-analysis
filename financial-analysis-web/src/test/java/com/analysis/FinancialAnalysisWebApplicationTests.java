package com.analysis;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FinancialAnalysisWebApplicationTests {


    @Test
    void contextLoads() {
    }
    @Test
    public void testJsoup(){
        try {
            //Connection connection = Jsoup.connect("http://m.baidu.com/s?word=%E7%A7%91%E6%8A%80")
            //Connection connection = Jsoup.connect("http://www.baidu.com")
            while(true){
                Connection connection = Jsoup.connect("http://push2.eastmoney.com/api/qt/stock/get?fields=f57,f58,f43,f169,f170,f44,f45,f46,f51,f52,f60,f47,f48,f49,f161,f50,f71,f80,f127,f128,f129,f140,f143,f146,f149,f193,f194,f195,f196,f197,f168,f191,f192&invt=2&fltt=2&secid=0.002385")
                        //.cookies(cookiesMap)
                        .userAgent("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36")
                        .maxBodySize(0)
                        .ignoreContentType(true)
                        .postDataCharset("UTF-8")
                        //.proxy("47.95.210.26",8080)
                        //.proxy("14.204.74.189",80)
                        //.proxy("172.25.228.46",3369)
                        .timeout(60000);
                Connection.Response response = connection.execute();
            /*String html = Jsoup.connect("http://push2.eastmoney.com/api/qt/stock/get?fields=f57,f58,f43,f169,f170,f44,f45,f46,f51,f52,f60,f47,f48,f49,f161,f50,f71,f80,f127,f128,f129,f140,f143,f146,f149,f193,f194,f195,f196,f197,f168,f191,f192&invt=2&fltt=2&secid=0.002385")
                    .maxBodySize(0)
                    .followRedirects(true)
                    .method(Connection.Method.GET)
                    //.proxy(proxy)
                    .execute()
                    .body();*/
                String html = response.body();
                System.out.println(html);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

