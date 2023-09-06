package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Area;

import java.util.List;
import java.util.Map;

public interface AreaService extends IService<Area> {


    public List<Area> findByParentId(Integer parentId);

    public List<Area> areaListByuserArea(Integer userArea);

    public List<Map<String,Object>> getParent(List<Area> list, Integer parentId);

    public boolean hasChild(Integer areaId);

    public List<Area> areaList();

    public List<Area> parentList(Integer areaLevel);

    public Integer areaAdd(Area area);

    public Integer areaEdit(Area area);


}
