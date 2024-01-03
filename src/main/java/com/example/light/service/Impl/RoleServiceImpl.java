package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Permission;
import com.example.light.entity.Role;
import com.example.light.entity.User;
import com.example.light.mapper.PermissionMapper;
import com.example.light.mapper.RoleMapper;
import com.example.light.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Role> queryRoleList() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,true,"role_level");
        return roleMapper.selectList(wrapper);
    }

    @Override
    public Role queryRoleById(Integer roleId) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        return roleMapper.selectOne(wrapper);
    }


    @Override
    public List<Role> queryRoleByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like("role_name",roleName);
        return roleMapper.selectList(wrapper);
    }

    @Override
    public Role queryRoleByRoleLevel(Integer roleLevel){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_level",roleLevel);
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
    public Integer deleteRole(Integer roleId){

        //查询是否已分配角色给用户，如果是就不能删除
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_role",roleId);

        System.out.println(userMapper.selectList(wrapper).isEmpty());

        if(userMapper.selectList(wrapper).isEmpty()){//没有元素返回true，才能删除

            return roleMapper.deleteById(roleId);

        }else {

            return -1;

        }


    }


}
