package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Permission;
import com.example.light.entity.Role;
import com.example.light.entity.User;
import com.example.light.mapper.PermissionMapper;
import com.example.light.mapper.RoleMapper;
import com.example.light.service.PermissionService;
import com.example.light.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> queryRoleList() {
        return roleMapper.selectList(null);
    }

    @Override
    public Role queryRoleById(Integer roleId) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return roleMapper.selectOne(wrapper);
    }


    @Override
    public Role queryRoleByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public void updateRole(Role role, Integer roleId){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update_time = sf.format(date);

        role.setRoleUpdatetime(update_time);

        roleMapper.update(role,wrapper);

    }

    @Override
    public void addRole(Role role){



        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = sf.format(date);

        role.setRoleCreatetime(create_time);

        roleMapper.insert(role);


    }

    @Override
    public void deleteRole(Integer roleId){
        roleMapper.deleteById(roleId);
    }


}
