package com.analysis.service.impl;

import com.analysis.dal.mapper.UserSubscribeMapper;
import com.analysis.service.IUserSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className UserSubscribeServiceImpl
 * @description
 * @date 2020/4/27
 */
@Service
public class UserSubscribeServiceImpl implements IUserSubscribeService {
    @Autowired
    private UserSubscribeMapper userSubscribeMapper;
    @Override
    public Map<String, Object> select(Map<String, Object> paramsMap) {
        Map<String,Object> responseMap = new HashMap<>();
        try{
            String userCode = (String)paramsMap.getOrDefault("userCode",null);
            List<Map<String,Object>> dataList = userSubscribeMapper.select(paramsMap);
            int count = userSubscribeMapper.count(userCode);
            responseMap.put("code",200);
            responseMap.put("rows",dataList);
            responseMap.put("total",count);
        }catch (Exception e){
            responseMap.put("code",500);
            responseMap.put("error",e.getMessage());
        }
        return responseMap;
    }
}
