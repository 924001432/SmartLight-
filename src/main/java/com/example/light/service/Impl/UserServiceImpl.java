package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByNameAndPassword(String username,String password) {
        QueryWrapper<User> QueryWrapper = new QueryWrapper<>();

        QueryWrapper.eq("user_name",username)
                .eq("user_password", password);

        //User QueryUser = userMapper.selectOne(QueryWrapper1);

        return userMapper.selectOne(QueryWrapper);
    }

    @Override
    public User queryUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        return userMapper.selectOne(wrapper);
    }

}