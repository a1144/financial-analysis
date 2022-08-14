package com.analysis.controller;

import com.analysis.service.IStockDailyBasicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lvshuzheng
 * @className StockDailyBasicMessageController
 * @description
 * @date 2020/4/27
 */
@RestController
@RequestMapping("/stockDailyBasicMessage")
public class StockDailyBasicMessageController {
    @Autowired
    private IStockDailyBasicMessageService stockDailyBasicMessageService;

    @RequestMapping("selectByCodeAndName")
    public Map<String,Object> selectByStockCodeAndName(@RequestBody Map<String,Object> paramsMap){
        return stockDailyBasicMessageService.selectByStockCodeAndName(paramsMap);
    }
}
