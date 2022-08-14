package com.analysis.financialanalysis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lvshuzheng
 * @className Test
 * @description
 * @date 2020/4/20
 */
@RestController
public class Test {
    @RequestMapping("test")
    public String test(){
        return "123";
    }
}
