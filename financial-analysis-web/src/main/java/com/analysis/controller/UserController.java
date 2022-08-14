package com.analysis.controller;

import com.analysis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className UserController
 * @description
 * @date 2020/4/20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String, Object> map) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userCode", map.get("userCode"));
        paramsMap.put("password", map.get("password"));
        return userService.login(paramsMap);
    }
}
