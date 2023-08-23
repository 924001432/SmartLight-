package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Alarm;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public User queryUserById(Integer userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User queryUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> queryUserList(){

        return userMapper.selectList(null);

    }

    @Override
    public Integer userEdit(User user) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());

        System.out.println(user.getUserId());

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uodate_time = sf.format(date);

        user.setUserUpdatetime(uodate_time);


        return userMapper.update(user , wrapper);
    }

    @Override
    public Integer userAdd(User user) {

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = sf.format(date);

        user.setUserCreatetime(create_time);


        return userMapper.insert(user);
    }

    @Override
    public Integer userDelete(Integer userId){
        return userMapper.deleteById(userId);
    }


}