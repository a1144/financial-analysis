package com.analysis.controller;

import com.analysis.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvshuzheng
 * @className Check
 * @description
 * @date 2020/4/20
 */
@RestController
public class Check {
    @Autowired
    private CheckService checkService;
    @RequestMapping("check")
    public String check(){
        return checkService.check();
    }
}
