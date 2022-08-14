package com.analysis.service;

import java.util.Map;

/**
 * @author lvshuzheng
 * @description
 * @date 2020/4/26
 */
public interface IStockDailyBasicMessageService {
    void insert(Map<String,Object> paramsMap);
    Map<String,Object> selectByStockCodeAndName(Map<String,Object> paramsMap);
}
