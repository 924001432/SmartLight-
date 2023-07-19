package com.example.light.controller;

import com.example.light.common.ResultMapUtil;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return
     */
    @RequestMapping("/Login")
    public String Login(){

        Session session = SecurityUtils.getSubject().getSession();
        String username = (String) session.getAttribute("username");


        if (username == null){
            return "/login";
        }else {
            return "redirect:/Index";
        }

    }

    /**
     * 登录判断
     * @return
     */
    @RequestMapping("/LoginIn")
    @ResponseBody
    public Object LoginIn(String username,String password){

        if(username==null||password==null){
            return ResultMapUtil.getHashMapLogin("用户名密码不能为空","2");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            return ResultMapUtil.getHashMapLogin("用户名不存在","2");
        }catch (IncorrectCredentialsException e){
            return ResultMapUtil.getHashMapLogin("密码错误","2");
        }

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("username",username);                      //默认30min



        return ResultMapUtil.getHashMapLogin("验证成功","1");

    }

    /**
     * 主页跳转
     * @return
     */
    @RequestMapping("/Index")
    public Object Index(){
        return "/index";
    }


    /**
     * 退出登录
     */
    @RequestMapping(value = "/Logout")
    public String Logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }


    /**
     * 当前用户信息界面
     * @return
     */
    @RequestMapping("/userInfo")
    public Object userInfo(Model model){
        Session session = SecurityUtils.getSubject().getSession();

        User user = userService.queryUserByName((String) session.getAttribute("username"));

        model.addAttribute("obj",user);

        return "/userInfo1";
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(){
        Session session = SecurityUtils.getSubject().getSession();

        return (String) session.getAttribute("username");
    }


}
