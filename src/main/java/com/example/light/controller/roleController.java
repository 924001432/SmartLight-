package com.example.light.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.light.common.ResultMapUtil;
import com.example.light.dto.RoleDto;
import com.example.light.entity.*;
import com.example.light.service.PermissionService;
import com.example.light.service.RolePermissionService;
import com.example.light.service.RoleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class roleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;



    @RequestMapping("/roleInfo")
    public Object roleInfo(){
        return "/role/roleInfo";
    }

    /**
     * 获取全部的角色信息
     * @return
     */
    @GetMapping("/roleList")
    @ResponseBody
    public List<Role> roleList(){

        return roleService.queryRoleList();
    }

    @RequestMapping("/queryRoleById/{roleId}")
    @ResponseBody
    public Object queryRoleById(@PathVariable(name = "roleId",required = true)Integer roleId){

        Role role = roleService.queryRoleById(roleId);



        return role;
    }

    /**
     *
     * @param roleName
     * @return
     */
    @RequestMapping("/queryRoleByRoleName/{roleName}")
    @ResponseBody
    public Object queryRoleByRoleName(@PathVariable(name = "roleName",required = true)String roleName){

//        Role role =
        List<Role> list = roleService.queryRoleByName(roleName);

//        if(role != null){
//            list.add(role);
//            return list;
//        }else {
//            return null;
//        }

        return list;

    }

    /**
     * 编辑角色信息页面
     */
    @RequestMapping("/roleEditPage/{roleId}")
    public Object roleEditPage(@PathVariable(name = "roleId",required = true)Integer roleId, Model model){

        Role role = roleService.queryRoleById(roleId);
        model.addAttribute("obj",role);


        return "/role/roleEditPage";
    }

    /**
     * 编辑用户信息
     * @return
     */
    @GetMapping("/roleEdit")
    @ResponseBody
    public void roleEdit(Role role){

        System.out.println(role.getRoleId());
        System.out.println(role.getRoleName());
        System.out.println(role.getRoleRemark());

    }

    /**
     * 新增角色信息页面
     */
    @RequestMapping("/roleAddPage")
    public Object roleAddPage(){

        return "/role/roleAddPage";
    }

    /**
     * 编辑用户信息
     * @return
     */
    @RequestMapping("/roleAdd")
    @ResponseBody
    public void roleAdd( RoleDto roleDto){

        Role role = roleDto;

        if (role.getRoleId() != null) {// 修改，查询是否存在
            //更新数据库
            roleService.updateRole(role,role.getRoleId());

        } else {// 新增
            List<Role> list = roleService.queryRoleByName(role.getRoleName());
            if (list != null) {
                throw new IllegalArgumentException(role.getRoleName() + "已存在");
            }

//            roleDao.save(role);
            //更新数据库
            roleService.addRole(role);

        }

        //更新权限数据库
        saveRolePermission(role.getRoleId(), roleDto.getPermissionIds());

    }

    private void saveRolePermission(Integer roleId, List<Integer> permissionIds) {
        //删除
        rolePermissionService.deleteRolePermission(roleId);
        permissionIds.remove(0);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            //插入
            rolePermissionService.addRolePermission(roleId, permissionIds);
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping("/roleDelete/{roleId}")
    @ResponseBody
    public Object roleDelete(@PathVariable(name = "roleId",required = true)Integer roleId){

        try{
            int i = roleService.deleteRole(roleId);

            if(i == -1){//已经分配角色给用户
                HashMap<String,Object> resultMap = new HashMap<>();

                resultMap.put("msg","已有用户使用该角色，请先解绑再删除");
                resultMap.put("code",1);
                resultMap.put("icon",5);
                resultMap.put("anim",6);
                return resultMap;

            }else {
                return ResultMapUtil.getHashMapSave(i);
            }


        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }



    }





}
