package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Area;
import com.example.light.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {


    public List<Permission> permissions();

    public List<Permission> permissionList();


    public List<Permission> listParents();

    public Permission queryPermission(Integer id);

    public Permission queryPermissionBypermissionId(Integer id);

    public Integer menuAdd(Permission permission);

    public Integer menuEdit(Permission permission);


    public Integer menuDelete(Integer id);




}
