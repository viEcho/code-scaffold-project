package com.datasource.sample.service;

import com.datasource.sample.annotation.DataSource;
import com.datasource.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/17
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int queryMasterUserCount(){
        return userMapper.count();
    }

    @DataSource("slave")
    public int querySlaveUserCount(){
        return userMapper.count();
    }

}
