package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Alarm;
import com.example.light.entity.Area;
import com.example.light.mapper.AreaMapper;
import com.example.light.service.AreaService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    /**
     * 根据父ID找到所有子节点
     * @param parentId
     * @return
     */
    @Override
    public List<Area> findByParentId(Integer parentId) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        return areaMapper.selectList(wrapper);
    }

    @Override
    public List<Area> areaListByuserArea(Integer userArea){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",userArea);
        return areaMapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> getParent(List<Area> list, Integer parentId) {
        return null;
    }

    @Override
    public boolean hasChild(Integer areaId) {
        return false;
    }

    @Override
    public List<Area> areaList(){
        return areaMapper.selectList(null);
    }

    @Override
    public List<Area> parentList(Integer areaLevel){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_level",areaLevel);
        return areaMapper.selectList(wrapper);
    }

    @Override
    public Integer areaAdd(Area area){

        return areaMapper.insert(area);

    }


}
