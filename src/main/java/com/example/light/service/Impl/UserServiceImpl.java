package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<User> queryUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.apply("CONCAT('%',name,'%')like{0}",username);
        wrapper.like("user_name",username);
        return userMapper.selectList(wrapper);
    }

    @Override
    public List<User> querySingleUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);
        return userMapper.selectList(wrapper);
    }

    @Override
    public List<User> queryUserByuserArea(Integer userArea){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_area",userArea);
        return userMapper.selectList(wrapper);

    }

    @Override
    public List<User> queryUserByuserRoleId(Integer roleId){

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_role",roleId);
            return userMapper.selectList(wrapper);

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

        //设置默认密码
        user.setUserPassword("123456");

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

    @Override
    public Integer passwordEdit(Integer userId,String password){

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);

            User user = new User();
            user.setUserPassword(password);

            return userMapper.update(user , wrapper);
    }

    @Override
    public Integer wxBindUser(Integer userId, String openId) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId).
                set("user_wx_id", openId);
        return userMapper.update(null, wrapper);
    }

    @Override
    public Integer wxUnbindUser(Integer userId) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId).
                set("user_wx_id", null);
        return userMapper.update(null, wrapper);
    }

    @Override
    public List<User> getAllUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return userMapper.selectList(wrapper);
    }


}