package com.example.light.controller;

import com.example.light.dto.RoleDto;
import com.example.light.entity.Role;
import com.example.light.entity.RolePermission;
import com.example.light.service.RolePermissionService;
import com.example.light.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class rolePermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;





    /**
     * 获取某个角色信息
     * @return
     */
    @GetMapping("/rolePermissionsById/{roleId}")
    @ResponseBody
    public List<RolePermission> listByRoleId(@PathVariable(name = "roleId",required = true)Integer roleId){

        return rolePermissionService.queryRolePermissionById(roleId);

    }



}
