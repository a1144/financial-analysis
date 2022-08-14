package com.analysis.service;

import com.analysis.dal.CheckDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lvshuzheng
 * @className Check
 * @description
 * @date 2020/4/20
 */
@Service
public class CheckService {
    @Autowired
    private CheckDal checkDal;
    public String check(){
        return checkDal.check();
    }
}
