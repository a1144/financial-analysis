package com.analysis.service.impl;

import com.analysis.dal.mapper.UserMapper;
import com.analysis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className UserServiceImpl
 * @description
 * @date 2020/4/20
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, Object> login(Map<String, Object> paramsMap) {
        Map<String,Object> responseMap = new HashMap<>();
        Map<String,Object> userMap = userMapper.selectByNameAndPassword(paramsMap);
        if(userMap != null){
            HttpSession session= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("userName",userMap.get("user_name"));
            responseMap.put("code",200);
            responseMap.put("userMessage",userMap);
        }else {
            responseMap.put("code",500);
            responseMap.put("error","登陆失败");
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> selectByUserCodes(List<String> list) {
        Map<String,Object> responseMap = new HashMap<>();
        List<Map<String,Object>> users = userMapper.selectByUserCodes(list);
        responseMap.put("users",users);
        return responseMap;
    }

}
