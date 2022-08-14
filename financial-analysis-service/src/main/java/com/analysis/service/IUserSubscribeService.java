package com.analysis.service;

import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @description
 * @date 2020/4/27
 */
public interface IUserSubscribeService {
    /**
    * @Description:
    * @param: paramsMap 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @Date: 2020/4/27 
    */ 
    Map<String,Object> select(Map<String,Object> paramsMap);
}
