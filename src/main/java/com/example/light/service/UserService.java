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

    public User queryUserByName(String username);

    public User queryUserByNameAndPassword(String username,String password);

    public List<User> queryUserList();

    public Integer userEdit(User user);

    public Integer userAdd(User user);

    public Integer userDelete(Integer userId);



}
/**
 * for(i=0,j=0,i<re_len;i++){
 *         if(result[i] between A-Z) {//统计
 *             nterm[j] = result[i];
 *             j++;
 *         }
 *     }
 */