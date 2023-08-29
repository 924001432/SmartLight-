package com.example.light.controller;

import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ResultMapUtil;
import com.example.light.common.UserUtil;
import com.example.light.dto.curUserDto;
import com.example.light.entity.Area;
import com.example.light.entity.Role;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.AreaService;
import com.example.light.service.RoleService;
import com.example.light.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * 登录
     * @return
     */
    @RequestMapping("/Login")
    public String Login(){

//        Session session = SecurityUtils.getSubject().getSession();
//        String username = (String) session.getAttribute("username");
        User user = UserUtil.getCurrentUser();
        System.out.println(user);

        if (user == null){
            return "/main/login";
        }else {
            return "/main/index";
        }

    }

    /**
     * 登录判断
     * @return
     */
    @LogAnnotation
    @ApiOperation(value = "用户登录")
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
            //设置session会话过期时间
//            SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());
        }catch (UnknownAccountException e){
            return ResultMapUtil.getHashMapLogin("用户名不存在","2");
        }catch (IncorrectCredentialsException e){
            return ResultMapUtil.getHashMapLogin("密码错误","2");
        }

        Session session = SecurityUtils.getSubject().getSession();
                              //默认30min
        User user = userService.queryUserByName(username);
        //可以放在UserUtil中，创建userDto
        session.setAttribute("username",username);
        session.setAttribute("userRole",user.getUserRole());
        session.setAttribute("userArea",user.getUserArea());

        return ResultMapUtil.getHashMapLogin("验证成功","1");

    }

    /**
     * 主页跳转
     * @return
     */

    @RequestMapping("/Index")
    public Object Index(){
        return "/main/index";
    }

    @RequestMapping("/baiduMap")
    public Object baiduMap(){
        return "/main/baiduMap";
    }


    /**
     * 退出登录
     */
//    @LogAnnotation
//    @ApiOperation(value = "用户退出")
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
//    @LogAnnotation
//    @ApiOperation(value = "查看用户的信息")
    @RequestMapping("/curUserInfo")
    public Object userInfo(Model model){
        Session session = SecurityUtils.getSubject().getSession();

        User user = userService.queryUserByName((String) session.getAttribute("username"));

        model.addAttribute("obj",user);

        return "/main/userInfo1";
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("/queryUserByName/{userName}")
    @ResponseBody
    public Integer queryUserByName(@PathVariable(name = "userName",required = true)String userName){


        if(userService.queryUserByName(userName)!=null){
//            System.out.println("已存在");
            return -1;
        }
        return 1;

    }

    /**
     * 根据用户姓名获取用户信息
     * @return
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public Object getUser(){

        curUserDto user = UserUtil.getCurrentUser();

//        Session session = SecurityUtils.getSubject().getSession();
//        Map<String,String> user = new HashMap<>();
//        user.put("userName",(String)session.getAttribute("username"));
//        Role role = roleService.queryRoleById((Integer) session.getAttribute("userRole"));
//        user.put("userRole",role.getRoleName());

        Role role = roleService.queryRoleById(user.getUserRole());
        user.setUserRoleName(role.getRoleName());

        return  user;
    }

    @RequestMapping("/getUserArea")
    @ResponseBody
    public Object getUserArea(){
//        Session session = SecurityUtils.getSubject().getSession();
//        Map<String,Object> user = new HashMap<>();

//        user.put("areaId",(Integer)session.getAttribute("userArea"));
//        Area area = areaService.getById((Integer) session.getAttribute("userArea"));

//        user.put("areaName",area.getAreaName());

        curUserDto user = UserUtil.getCurrentUser();
        Area area = areaService.getById(user.getUserArea());
        user.setUserAreaName(area.getAreaName());

        return  user;
    }

    /**
     * 所有用户信息页面
     */
    @RequestMapping("/allUserInfo")
    public Object allUserInfo(){
        return "/user/allUserInfo";
    }

    /**
     * 获取全部的用户信息
     * @return
     */
    @GetMapping("/userList")
    @ResponseBody
    public List<User> userList(){

        return userService.queryUserList();
    }

    /**
     * 新增用户界面
     * @return
     */
    @RequestMapping("/userAddPage")
    public Object userAddPage(){

        return "/user/userAddPage";
    }

    /**
     * 编辑用户信息页面
     */
    @RequestMapping("/userEditPage/{userId}")
    public Object userEditPage(@PathVariable(name = "userId",required = true)Integer userId, Model model){

        User user = userService.queryUserById(userId);
        model.addAttribute("obj",user);


        return "/user/userEditPage";
    }



    /**
     * 编辑用户信息
     * @return
     */
    @GetMapping("/userEdit")
    @ResponseBody
    public Object userEdit(User user){

        try{
            int i = userService.userEdit(user);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    /**
     * 新增用户信息
     * @return
     */
    @GetMapping("/userAdd")
    @ResponseBody
    public Object userAdd(User user){

        try{
            int i = userService.userAdd(user);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    /**
     * 删除用户
     */
    @RequestMapping("/userDelete/{userId}")
    @ResponseBody
    public void userDelete(@PathVariable(name = "userId",required = true)Integer userId){

        userService.userDelete(userId);

    }




}
