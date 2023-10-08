package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Role;
import com.example.light.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * 设备Mapper
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {



}