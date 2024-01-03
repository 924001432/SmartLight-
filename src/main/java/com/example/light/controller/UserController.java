package com.example.light.controller;

import com.alibaba.excel.util.StringUtils;
import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ResultMapUtil;
import com.example.light.common.UserUtil;
import com.example.light.dto.curUserDto;
import com.example.light.dto.handleResultDto;
import com.example.light.entity.Area;
import com.example.light.entity.Role;
import com.example.light.entity.User;
import com.example.light.mapper.UserMapper;
import com.example.light.service.AreaService;
import com.example.light.service.RoleService;
import com.example.light.service.UserService;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.light.common.UserUtil.getSession;

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
//        User user = UserUtil.getCurrentUser();
//        System.out.println(user);
//
//        if (user == null){
//            return "/main/login";
//        }else {
//            return "/main/index";
//        }

        return "/main/login";

    }

    /**
     * 登录判断
     * @return
     */
//    @LogAnnotation
//    @ApiOperation(value = "用户登录")
    @RequestMapping("/LoginIn")
    @ResponseBody
    public Object LoginIn(String username,String password){

        if(username==null||password==null){
            return ResultMapUtil.getHashMapLogin("用户名密码不能为空","2","");
        }



        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
            //设置session会话过期时间
//            SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());
        }catch (UnknownAccountException e){
            return ResultMapUtil.getHashMapLogin("用户名不存在","2","");
        }catch (IncorrectCredentialsException e){
            return ResultMapUtil.getHashMapLogin("密码错误","2","");
        }


        return ResultMapUtil.getHashMapLogin("验证成功","1","");

    }


    /**
     * 微信登录
     * @return
     */
    @PostMapping("/LoginByWxAccount")
    @ResponseBody
    public Object LoginByWxAccount(@RequestBody HashMap<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        System.out.println("即将登录用户为:"+username+", "+password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        System.out.println(token);
        try{
            subject.login(token);

        }catch (UnknownAccountException e){
            return ResultMapUtil.getHashMapLogin("用户名不存在","2","");
        }catch (IncorrectCredentialsException e){
            return ResultMapUtil.getHashMapLogin("密码错误","2","");
        }

        String sessionId = (String) getSession().getId();
        return ResultMapUtil.getHashMapLogin("验证成功","1",sessionId);

    }

    /**
     * 微信账号绑定
     * @param appid
     * @param code
     * @param userId
     * @return
     */
    @RequestMapping ("/wxBind/{appid}/{code}/{userId}")
    @ResponseBody
    public Object wxBind(@PathVariable(name = "appid",required = true)String appid, @PathVariable(name = "code",required = true)String code, @PathVariable(name = "userId",required = true)Integer userId) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String grantType = "authorization_code";

        // 构造请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发送POST请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url + "?appid=" + appid + "&secret=5ca584e3aecd7bb36242d6cd6dcf027a"  + "&js_code=" + code + "&grant_type=" + grantType,
                HttpMethod.POST,
                entity,
                String.class
        );


        String responseBody = response.getBody();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String openId = jsonObject.get("openid").getAsString();



        try{
            int i = userService.wxBindUser(userId, openId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 解绑微信
     * @param userId
     * @return
     */
    @RequestMapping ("/wxUnbind/{userId}")
    @ResponseBody
    public Object wxUnbind( @PathVariable(name = "userId",required = true)Integer userId) {

        try{
            int i = userService.wxUnbindUser(userId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 微信账号登录
     * @param code
     * @return
     */
    @RequestMapping ("/wxLogin/{code}")
    @ResponseBody
    public Object wxLogin(@PathVariable(name = "code",required = true)String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String grantType = "authorization_code";

        // 构造请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发送POST请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url + "?appid=wx3287ca8f13d9679a" + "&secret=5ca584e3aecd7bb36242d6cd6dcf027a"  + "&js_code=" + code + "&grant_type=" + grantType,
                HttpMethod.POST,
                entity,
                String.class
        );


        String responseBody = response.getBody();
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String openId = jsonObject.get("openid").getAsString();
        System.out.println(openId);
        String session_key = jsonObject.get("session_key").getAsString();

        List<User> userList = userService.getAllUser();
        System.out.println(userList);
        for(User user : userList){
            if(user.getUserwxId() != null) {
                if (user.getUserwxId().equals(openId)) {

                    Subject subject = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPassword());
                    System.out.println(token);

                    subject.login(token);
                    String sessionId = (String) getSession().getId();
                    return ResultMapUtil.getHashMapLogin("验证成功", "1", sessionId);
                }
            }
        }
       return ResultMapUtil.getHashMapLogin("还未绑定微信账号","2","");

    }


    /**
     * 主页跳转
     * @return
     */

    @RequestMapping("/Index")
    public Object Index(){
        return "/main/index";
    }

    /**
     * 地图
     * @return
     */
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
//        return "/Login";
    }


    /**
     * 当前用户信息界面
     * @return
     */
//    @LogAnnotation
//    @ApiOperation(value = "查看用户的信息")
    @RequestMapping("/curUserInfo")
    public Object userInfo( Model model ){

        curUserDto curUser = UserUtil.getCurrentUser();

        User user = userService.queryUserById(curUser.getUserId());

        model.addAttribute("obj",user);

        return "/self/selfInfo";
    }

    /**
     * 修改密码界面
     */
    @RequestMapping("/pswEditPage")
    public Object pswEditPage( Model model ){

        curUserDto curUser = UserUtil.getCurrentUser();

        User user = userService.queryUserById(curUser.getUserId());

        model.addAttribute("obj",user);


        return "/self/pswEditPage";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/passwordEdit/{userPassword}")
    @ResponseBody
    public Object pswEdit( @PathVariable(name = "userPassword",required = true)String userPassword){


        curUserDto curUser = UserUtil.getCurrentUser();

        try{
            int i = userService.passwordEdit(curUser.getUserId(),userPassword);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

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
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("/querySingleUserByName/{userName}")
    @ResponseBody
    public Integer querySingleUserByName(@PathVariable(name = "userName",required = true)String userName){

//        System.out.println("userName:" + userName);
//        List<User> list = userService.querySingleUserByName(userName);
//        for (User user : list) {
//            System.out.println(user);
//        }

        if(!userService.querySingleUserByName(userName).isEmpty()){
//            System.out.println("已存在");

            return -1;
        }

        return 1;

    }

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("/queryUserById/{userId}")
    @ResponseBody
    public Object queryUserById(@PathVariable(name = "userId",required = true)Integer userId){


        return userService.queryUserById(userId);

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
        user.setUserRoleLevel(role.getRoleLevel());

        Area area = areaService.queryAreaById(user.getUserArea());
        user.setUserAreaName(area.getAreaName());

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
        //获取基本信息，角色ID，区域ID，创建人ID；获取数据填充到VO中，

        return userService.queryUserList();
    }

    /**
     * 通过区域ID来获取该用户下属的全部的用户信息
     * @par
     * @return
     */
    @GetMapping("/userListByuserArg/{type}&{userArg}")
    @ResponseBody
    public List<User> userListByuserArea(@PathVariable(name = "type",required = true)Integer type,
                                         @PathVariable(name = "userArg",required = true)String userArg){
        //获取基本信息，角色ID，区域ID，创建人ID；获取数据填充到VO中，
        //获取当前用户的区域ID
        curUserDto curUser = UserUtil.getCurrentUser();
        Integer userArea = curUser.getUserArea();
        //获取当前用户的角色级别
        Integer userRoleLevel = curUser.getUserRoleLevel();
        //返回的用户集合
        List<User> userList = new ArrayList<>();
        //如果角色级别为1，即为超级管理员，将级别为2的用户也加入到集合中
        if (userRoleLevel == 1){
            //在角色表中查询角色级别为2的角色
            List<User> users =  userService.queryUserByuserRoleId(roleService.queryRoleByRoleLevel(2).getRoleId());//获取角色级别为2的用户

            userList.addAll(users);//将角色级别为2的用户加入到集合中

        }

        List<Area> areaList = areaService.areaList();

        List<Area> matchingAreas = new ArrayList<>();
        //获取当前用户的区域下的所有区域
        setAreaList(userArea, areaList, matchingAreas);


        //遍历，构造用户集合，包含了该区域下的所有用户，通过用户的角色编号来确定角色级别，把级别低的用户加入到集合中
        for (Area area : matchingAreas) {
//            userList.addAll(userService.queryUserByuserArea(area.getAreaId()));
            //获取该区域下的所有用户
            List<User> users = userService.queryUserByuserArea(area.getAreaId());
            //遍历用户集合，把级别低的用户加入到集合中
            for (User user : users) {
                //判断用户的角色级别是否低于当前用户的角色级别
                if(roleService.queryRoleById( user.getUserRole() ).getRoleLevel() > userRoleLevel){
                    userList.add(user);
                }
            }

        }




        if(type == -1){//默认全部列表，该列表包含了该区域下的所有用户，通过用户的角色编号来确定角色级别，把级别低的用户加入到集合中
//            System.out.println("userList:" + userList);
            return userList;

        }else if( type == 1){//用户名查询


            return userList.stream()
                    .filter(user -> user.getUserName().contains(userArg))
                    .collect(Collectors.toList());


        }else{//用户角色查询
            return null;
        }

    }

    //递归获取区域列表
    private void setAreaList(Integer pId, List<Area> areaListAll, List<Area> list) {
        for (Area area : areaListAll) {
            if (area.getParentId().equals(pId)) {
                list.add(area);
                if (areaListAll.stream().filter(p -> p.getParentId().equals(area.getAreaId())).findAny() != null) {
                    setAreaList(area.getAreaId(), areaListAll, list);
                }
            }
        }
    }

    /**
     * 新增用户信息页面
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
            System.out.println(user.toString());
            int i = userService.userEdit(user);
//            int i = 1;
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
            curUserDto curUser = UserUtil.getCurrentUser();
            user.setUserBuilder(curUser.getUserId());//当前登录用户创建


            System.out.println(user.toString());
            int i = userService.userAdd(user);
//            int i = 1;
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


    /**
     * 修改用户信息
     * @param dataMap
     * @return
     */
    @RequestMapping("/modifyUserInfoWX")
    @ResponseBody
    public Object modifyUserInfoWX(@RequestBody Map<String, String> dataMap) {
        System.out.println("更新用户信息:"+dataMap);
        try{
            Integer areaId = areaService.queryAreaByName(dataMap.get("userArea")).getAreaId();
            User user = userService.queryUserById(Integer.parseInt(dataMap.get("userId")));
            user.setUserName(dataMap.get("userName"));
            user.setUserPhone(dataMap.get("userPhone"));
            user.setUserEmail(dataMap.get("userEmail"));
            user.setUserArea(areaId);
            int i = userService.userEdit(user);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }




}
