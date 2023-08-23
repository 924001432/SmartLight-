package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Area;
import com.example.light.entity.Permission;
import com.example.light.entity.RolePermission;
import com.example.light.mapper.PermissionMapper;
import com.example.light.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;








    @Override
    public List<Permission> permissionList() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,true,"sort");

        return permissionMapper.selectList(wrapper);
    }

    @Override
    public List<Permission> listParents(){
        //@Select("select * from sys_permission t where t.type = 1 order by t.sort")
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("type",1).orderBy(true,true,"sort");

        return permissionMapper.selectList(wrapper);
    }

    @Override
    public Permission queryPermission(Integer id){
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);

        return permissionMapper.selectOne(wrapper);
    }

    @Override
    public Integer menuAdd(Permission permission){

        return permissionMapper.insert(permission);
    }

    @Override
    public Integer menuEdit(Permission permission){

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("id",permission.getId());

        return permissionMapper.update(permission,wrapper);
    }

    @Override
    public Integer menuDelete(Integer id){

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);


        QueryWrapper<Permission> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parentId",id);
        List<Permission> list = permissionMapper.selectList(wrapper1);


        if(list != null){//已经有子节点
            System.out.println(list.get(0).getName());
            return -1;
        }



        return permissionMapper.deleteById(id);
    }


}
