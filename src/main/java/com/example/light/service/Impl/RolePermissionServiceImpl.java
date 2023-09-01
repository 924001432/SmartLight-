package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Role;
import com.example.light.entity.RolePermission;

import com.example.light.mapper.RoleMapper;
import com.example.light.mapper.RolePermissionMapper;
import com.example.light.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> queryRolePermissionById(Integer roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return rolePermissionMapper.selectList(wrapper);
    }

    @Override
    public void addRolePermission(Integer roleId, List<Integer> permissionIds) {

//        System.out.println(roleId);
//        for (Integer permissionId : permissionIds) {
//            System.out.println(permissionId);
//        }

        RolePermission rolePermission = new RolePermission();
        for (Integer permissionId : permissionIds) {
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);

            rolePermissionMapper.insert(rolePermission);
        }



    }

    @Override
    public void deleteRolePermission(Integer roleId) {
        rolePermissionMapper.deleteById(roleId);
    }
}
