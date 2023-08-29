package com.example.light.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.light.common.ResultMapUtil;
import com.example.light.entity.Area;
import com.example.light.entity.Permission;
import com.example.light.entity.Role;
import com.example.light.service.PermissionService;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class permissionController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/permissionAll")
    @ResponseBody
    public void permissions() {
        List<Permission> permissions = permissionService.permissions();
        //读取数据

        for (Permission permission : permissions) {
            System.out.println(permission.toString());
        }


    }

    @RequestMapping("/newIndex")
    public Object Index(){
        return "/main/newIndex";
    }


    @RequestMapping("/menuInfo")
    public Object manuInfo(){
        return "/menu/menuInfo";
    }

    @RequestMapping("/menuAddPage")
    public Object menuAddPage(){
        return "/menu/menuAddPage";
    }

    @RequestMapping("/menuAdd")
    @ResponseBody
    public Object menuAdd(Permission permission) {

        try{
            int i = permissionService.menuAdd(permission);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 编辑菜单信息页面
     */
    @RequestMapping("/menuEditPage/{id}")
    public Object roleEditPage(@PathVariable(name = "id",required = true)Integer id, Model model){

        Permission permission = permissionService.queryPermission(id);
        model.addAttribute("obj",permission);


        return "/menu/menuEditPage";
    }

    /**
     * 编辑菜单
     * @param permission
     * @return
     */
    @RequestMapping("/menuEdit")
    @ResponseBody
    public Object menuEdit(Permission permission) {

        try{
            int i = permissionService.menuEdit(permission);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    /**
     * 删除菜单项
     */
    @RequestMapping("/menuDelete/{id}")
    @ResponseBody
    public Object menuDelete(@PathVariable(name = "id",required = true)Integer id){

        try{
            int i = permissionService.menuDelete(id);

            if(i == -1){//存在子节点
                HashMap<String,Object> resultMap = new HashMap<>();

                resultMap.put("msg","该节点存在子节点，不能删除");
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

    /**
     * 一级菜单
     * @return
     */
    @RequestMapping("/permissions/parents")
    @ResponseBody
    public List<Permission> permissionsParents() {

        List<Permission> permissions = permissionService.listParents();
        //获取所有一级记录，

        return permissions;
    }

    @RequestMapping("/permission/current")
    @ResponseBody
    public List<Permission> permissionsCurrent() {

        List<Permission> permissions = permissionService.permissionList();
        //获取所有记录，

//        for (Permission permission : permissions) {
//            System.out.println(permission);
//        }

        List<Permission> list = Lists.newArrayList();
        setPermissionsList(0, permissions, list);

        return list;
    }

    /**
     * 菜单列表
     *
     * @param pId
     * @param permissionsAll
     * @param list
     */
    private void setPermissionsList(Integer pId, List<Permission> permissionsAll, List<Permission> list) {
        for (Permission per : permissionsAll) {
            if (per.getParentId().equals(pId)) {
                list.add(per);
                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }

    @RequestMapping("/permission/all")
    @ResponseBody
    public JSONArray permissionAll() {
        List<Permission> permissions = permissionService.permissionList();
        //读取数据

        JSONArray array = new JSONArray();
        setPermissionsTree(0, permissions, array);

        return array;
    }

    private void setPermissionsTree(Integer pId, List<Permission> permissionsAll, JSONArray array) {
        for (Permission per : permissionsAll) {

            if (per.getParentId().equals(pId)) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);

                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getId(), permissionsAll, child);
                }
            }
        }
    }

    @RequestMapping("/permissionEdit/{roleId}&{permissionIds}")
    @ResponseBody
    public void permissionEdit(@PathVariable(name = "roleId",required = true)Integer roleId,
                               @PathVariable(name = "permissionIds",required = true)List<Integer> permissionIds){

        System.out.println(roleId);
        for (Integer permissionId : permissionIds) {
            System.out.println(permissionId);
        }
        //获取角色编号，权限编号，修改数据库

    }

    @RequestMapping("/permissionAdd/{permissionIds}")
    @ResponseBody
    public void permissionAdd(@PathVariable(name = "permissionIds",required = true)List<Integer> permissionIds){


        for (Integer permissionId : permissionIds) {
            System.out.println(permissionId);
        }
        //先用角色再有权限，所以角色权限表的id不用管，获取角色编号，权限编号，修改数据库

    }


}
