package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Permission;
import com.example.light.entity.Role;
import com.example.light.entity.User;
import io.swagger.models.auth.In;

import java.util.List;

public interface RoleService extends IService<Role> {




    public List<Role> queryRoleList();

    public Role queryRoleById(Integer roleId);


    public Role queryRoleByName(String roleName);


    public void updateRole(Role role, Integer roleId);


    public void addRole(Role role);

    public void deleteRole(Integer roleId);



}
