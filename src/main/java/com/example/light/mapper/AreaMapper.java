package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.light.entity.Area;
import org.apache.ibatis.annotations.Select;


/**
 * 设备Mapper
 */
public interface AreaMapper extends BaseMapper<Area> {

    @Select("select * from area a where a.parent_id = #{parentId} order by a.area_rank desc limit 1")
    Area findMaxRankByparentId(Integer parentId);
}