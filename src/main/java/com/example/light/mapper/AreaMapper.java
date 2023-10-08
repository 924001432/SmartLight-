package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.light.entity.Area;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * 设备Mapper
 */
@Repository
public interface AreaMapper extends BaseMapper<Area> {

    @Select("select * from area a where a.parent_id = #{parentId} order by a.area_rank desc limit 1")
    Area findMaxRankByparentId(Integer parentId);

    @Select("select * from area a where a.area_level = #{area_level} order by a.area_rank desc limit 1")
    Area findMaxRankByareaLevel(Integer areaLevel);
}