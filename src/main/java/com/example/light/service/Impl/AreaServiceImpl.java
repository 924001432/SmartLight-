package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Alarm;
import com.example.light.entity.Area;
import com.example.light.mapper.AreaMapper;
import com.example.light.service.AreaService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

        System.out.println("dasd");
        System.out.println("area.getParentId():"+area.getParentId());

        int[] parentRanks = new int[5];
        int maxRank ;
        //找相同父节点中Rank最大的
        //@Select("select * from area a where a.parent_id = #{parentId} order by a.area_rank desc limit 1")
        Area maxRankArea = areaMapper.findMaxRankByparentId(area.getParentId());

        if(maxRankArea == null){
            maxRank = 0;
        }else {
            maxRank =  maxRankArea.getAreaRank();
        }
//        System.out.println("maxRankArea.getAreaRank():"+maxRankArea.getAreaRank());
        System.out.println(maxRank);
        System.out.println("area.getAreaLevel():"+area.getAreaLevel());

        area.setAreaRank(maxRank+1);
        parentRanks[area.getAreaLevel()-1] = maxRank+1;
        //获取所有父节点的Rank，

        System.out.println(area.getParentId());
        findParentRanks(area.getParentId(),parentRanks);

        System.out.println(Arrays.toString(parentRanks));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parentRanks.length; i++) {
            String numStr = String.format("%02d", parentRanks[i]);
            sb.append(numStr);
        }

        String result = sb.toString();

        System.out.println(result); // 输出：1234050607

        area.setAreaSerial(result);

        System.out.println(area.toString());

        return areaMapper.insert(area);
//        return 0;

    }

    public void findParentRanks(Integer parentId,int[] parentRanks){

//        int[] parentRanks = new int[5];


        if(parentId != 0){
            QueryWrapper<Area> wrapper = new QueryWrapper<>();
            wrapper.eq("area_id",parentId);
            Area area = areaMapper.selectOne(wrapper);
//            if(area == null){
//
//            }
            System.out.println("area.getAreaRank():"+area.getAreaRank());
            parentRanks[area.getAreaLevel()-1] = area.getAreaRank();
            findParentRanks( area.getParentId() , parentRanks);
        }






    }


}
