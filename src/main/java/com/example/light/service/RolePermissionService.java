package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Role;
import com.example.light.entity.RolePermission;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 查询
     */
    public List<RolePermission> queryRolePermissionById(Integer roleId);


    /**
     * 插入
     */
    public void addRolePermission(Integer roleId, List<Integer> permissionIds);


    /**
     * 删除
     */
    public void deleteRolePermission(Integer roleId);






}
