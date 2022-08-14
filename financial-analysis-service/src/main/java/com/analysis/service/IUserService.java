package com.analysis.service;

import java.util.List;
import java.util.Map;

/**
 * @author lvshuzheng
 * @className IUserService
 * @description
 * @date 2020/4/20
 */
public interface IUserService {
    Map<String,Object> login(Map<String,Object> map);
    Map<String,Object> selectByUserCodes(List<String> lists);
}
