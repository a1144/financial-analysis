package com.analysis.financialanalysis;

//import com.analysis.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class FinancialAnalysisApplicationTests {
    /*@Autowired
    private UserController userController;*/

    @Test
    void contextLoads() {
    }

    @Test
    void login(){
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("userCode","001");
        paramsMap.put("password","123");
        /*Map<String,Object> user = userController.login(paramsMap);
        System.out.println(user);*/
    }

}
