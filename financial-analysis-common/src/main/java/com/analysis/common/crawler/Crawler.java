package com.analysis.common.crawler;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * @author lvshuzheng
 * @description
 * @date 2020/4/22
 */
public interface Crawler {
    /**
    * @Description: 根据url获取document对象
    * @param: url
    * @return: org.jsoup.nodes.Document
    * @Date: 2020/4/22
    */
    Document crawling(String url);

    /**
     * @Description: 解析，获取所需信息
     * @param: document
     * @return: java.lang.Object
     * @Date: 2020/4/22
     */
    Object analysis(Object object);

    /**
    * @Description: httpGet爬取信息
    * @param: url
    * @return: java.lang.Object
    * @Date: 2020/4/26
    */
    JSONObject httpGet(String url);

    /**
    * @Description:  httpPost通过jsonObject获取信息
    * @param: url
    * @param: jsonParam
    * @return: com.alibaba.fastjson.JSONObject
    * @Date: 2020/4/26
    */
    JSONObject httpPost(String url,JSONObject jsonParam);

    /**
    * @Description:  httpPost通过jsonStr获取信息
    * @param: url
    * @param: jsonParamStr
    * @return: com.alibaba.fastjson.JSONObject
    * @Date: 2020/4/26
    */
    JSONObject httpPost(String url,String jsonParamStr);

}
