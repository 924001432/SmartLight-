package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.SysLogs;
import com.example.light.entity.User;
import org.springframework.ui.Model;

import java.util.List;


/**
 * 用户表的service接口
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户对象
     */
    public User queryUserById(Integer userId);

    public List<User> queryUserByName(String username);

    public List<User> querySingleUserByName(String username);

    public List<User> queryUserByuserArea(Integer userArea);

    public User queryUserByNameAndPassword(String username,String password);

    public List<User> queryUserList();

    public Integer userEdit(User user);

    public Integer userAdd(User user);

    public Integer userDelete(Integer userId);

    public Integer passwordEdit(Integer userId,String password);

}
