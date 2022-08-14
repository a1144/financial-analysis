package com.analysis.common.crawler;

import com.alibaba.fastjson.JSONObject;
import com.analysis.common.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author lvshuzheng
 * @className CrawlerWrapper
 * @description
 * @date 2020/4/22
 */
public class CrawlerWrapper implements Crawler{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Document crawling(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(3000).get();
        } catch (IOException e) {
            logger.error(url + " : 爬取失败");
            e.printStackTrace();
        }
        return document;
    }

    @Override
    public Object analysis(Object object) {
        return null;
    }

    @Override
    public JSONObject httpGet(String url) {
        try{
            JSONObject jsonObject = HttpClientUtils.httpGet(url);
            return jsonObject;
        }catch (Exception e){
            logger.error("httpGet请求: " + url + "请求失败");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject httpPost(String url, JSONObject jsonParam) {
        return null;
    }

    @Override
    public JSONObject httpPost(String url, String jsonParamStr) {
        return null;
    }
}
