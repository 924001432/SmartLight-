package com.example.light.controller;

import com.example.light.common.ResultMapUtil;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("/Login")
    public String Login(){
        return "/login";
    }

    @RequestMapping("/LoginIn")
    @ResponseBody
    public Object LoginIn(String username,String password){




        User QueryUser = userService.queryUserByNameAndPassword(username,password);

        if(QueryUser != null){
            return ResultMapUtil.getHashMapLogin("验证成功","1");
        }else {
            return ResultMapUtil.getHashMapLogin("验证失败","2");
        }

    }

    @RequestMapping("/Index")
    public Object Index(){
        return "/index";
    }
}
