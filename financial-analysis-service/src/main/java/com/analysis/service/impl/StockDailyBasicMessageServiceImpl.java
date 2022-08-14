package com.analysis.service.impl;

import com.analysis.dal.mapper.StockDailyBasicMessageMapper;
import com.analysis.service.IStockDailyBasicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className StockDailyBasicMessageServiceImpl
 * @description
 * @date 2020/4/26
 */
@Service
public class StockDailyBasicMessageServiceImpl implements IStockDailyBasicMessageService {
    @Autowired
    private StockDailyBasicMessageMapper stockDailyBasicMessageMapper;
    @Override
    public void insert(Map<String, Object> paramsMap) {
        stockDailyBasicMessageMapper.insert(paramsMap);
    }

    @Override
    public Map<String, Object> selectByStockCodeAndName(Map<String, Object> paramsMap) {
        Map<String,Object> responseMap = new HashMap<>();
        try{
            int total = stockDailyBasicMessageMapper.count(paramsMap);
            List<Map<String,Object>> stockMessageList =  stockDailyBasicMessageMapper.select(paramsMap);
            responseMap.put("code",200);
            responseMap.put("rows",stockMessageList);
            responseMap.put("total",total);
        }catch (Exception e){
            e.printStackTrace();
            responseMap.put("code",500);
            responseMap.put("error",e.getMessage());
        }

        return responseMap;
    }
}
