package com.example.light.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.light.entity.testEntity;
import com.example.light.mapper.testMapper;
import com.example.light.service.testService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 */
@Service
public class testServiceImpl extends ServiceImpl<testMapper, testEntity> implements testService {

    @Autowired
    private testMapper userMapper;

    @Override
    public testEntity queryUserById(testEntity user) {
        QueryWrapper<testEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userId",user.getTestId());
        return userMapper.selectOne(wrapper);
    }

}
