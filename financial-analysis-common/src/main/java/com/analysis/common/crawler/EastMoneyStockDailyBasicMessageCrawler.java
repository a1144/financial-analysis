package com.analysis.common.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.analysis.common.enums.EastMoneyStockDailyBasicMessageEnum;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className EastMoneyStockDailyBasicMessage
 * @description
 * @date 2020/4/26
 */
public class EastMoneyStockDailyBasicMessageCrawler extends CrawlerWrapper {

    @Override
    public Object analysis(Object object) {
        JSONObject jsonObject = ((JSONObject) object).getJSONObject("data");
        Map<String, Object> dataMap = new HashMap<>();
        for(String paramCode : jsonObject.keySet()){
            if("f127".equals(paramCode)||"f128".equals(paramCode)||"f129".equals(paramCode)){
                paramCode = "f127";
                EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum = EastMoneyStockDailyBasicMessageEnum.valOf(paramCode);
                System.out.println(jsonObject.getString("f127"));
                System.out.println(jsonObject.getString("f128"));
                System.out.println(jsonObject.getString("f129"));
                dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(),jsonObject.getString("f127") + "," +
                        jsonObject.getString("f128") + "," + jsonObject.getString("f129"));
                //dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(),"456");
                continue;
            }else if("f80".equals(paramCode)){
                EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum = EastMoneyStockDailyBasicMessageEnum.valOf(paramCode);
                dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(), JSONArray.toJSONString(jsonObject.getJSONArray(paramCode)));
                //dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(), "123");
                continue;
            }
            EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum = EastMoneyStockDailyBasicMessageEnum.valOf(paramCode);
            if(eastMoneyStockDailyBasicMessageEnum != null){
                dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(),jsonObject.get(paramCode));
            }
        }
        return dataMap;
    }

    /*public static void main(String[] args){
        CrawlerWrapper crawlerWrapper = new EastMoneyStockDailyBasicMessageCrawler();
        String url = "http://push2.eastmoney.com/api/qt/stock/get?fields=f57,f58,f43,f169,f170,f44,f45,f46,f51,f52,f60,f47,f48,f49,f161,f50,f71,f80,f127,f128,f129,f140,f143,f146,f149,f193,f194,f195,f196,f197,f168,f191,f192&secid=0.300292&invt=2&fltt=2";
        JSONObject jsonObject = crawlerWrapper.httpGet(url).getJSONObject("data");
        Map<String, Object> dataMap = new HashMap<>();
        System.out.println(jsonObject);
        for(String paramCode : jsonObject.keySet()){
            if("f127".equals(paramCode)||"f128".equals(paramCode)||"f129".equals(paramCode)){
                paramCode = "f127";
                EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum = EastMoneyStockDailyBasicMessageEnum.valOf(paramCode);
                System.out.println(jsonObject.getString("f127"));
                System.out.println(jsonObject.getString("f128"));
                System.out.println(jsonObject.getString("f129"));
                dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(),jsonObject.getString("f127") + "," +
                        jsonObject.getString("f128") + "," + jsonObject.getString("f129"));
                continue;
            }
            EastMoneyStockDailyBasicMessageEnum eastMoneyStockDailyBasicMessageEnum = EastMoneyStockDailyBasicMessageEnum.valOf(paramCode);
            if(eastMoneyStockDailyBasicMessageEnum != null){
                dataMap.put(eastMoneyStockDailyBasicMessageEnum.getParamName(),jsonObject.get(paramCode));
            }
        }
        System.out.println(dataMap);
    }*/
}
