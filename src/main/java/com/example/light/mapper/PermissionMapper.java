package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 设备Mapper
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select * from permission t order by t.sort")
    List<Permission> listAll();


}