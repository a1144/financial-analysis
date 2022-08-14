package com.analysis.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className UserMapper
 * @description
 * @date 2020/4/20
 */
@Component
@Mapper
public interface UserMapper {
    /**
    * @Description:  查询用户名+密码是否存在
    * @param: paramsMap
    * @return: java.lang.Integer
    * @Date: 2020/4/20
    */
    Map<String,Object> selectByNameAndPassword(Map<String,Object> paramsMap);
    
    /**
    * @Description:  根据userCodes批量查询
    * @param: list
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @Date: 2020/4/30
    */
    List<Map<String,Object>> selectByUserCodes(List<String> list);
}
