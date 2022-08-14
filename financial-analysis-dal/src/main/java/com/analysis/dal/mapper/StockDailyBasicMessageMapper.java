package com.analysis.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @description
 * @date 2020/4/26
 */
@Component
@Mapper
public interface StockDailyBasicMessageMapper {
    void insert(Map<String,Object> paramsMap);
    List<Map<String,Object>> select(Map<String,Object> paramsMap);
    int count(Map<String,Object> paramsMap);

}
