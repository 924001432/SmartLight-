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

import java.util.*;

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
    public List<Area> areaListByAreaLevel( Integer areaLevel ){
        //查找小于等于areaLevel的所有区域
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.le("area_level",areaLevel);
        return areaMapper.selectList(wrapper);


    }

    @Override
    public List<Area> parentList(Integer areaLevel){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_level",areaLevel);
        return areaMapper.selectList(wrapper);
    }

    @Override
    public Integer areaAdd(Area area){


        System.out.println("area.getAreaLevel():"+area.getAreaLevel());
        int[] parentRanks = new int[5];
        int maxRank = 0;
        //找相同父节点中Rank最大的
        //@Select("select * from area a where a.parent_id = #{parentId} order by a.area_rank desc limit 1")
//        Area maxRankArea = areaMapper.findMaxRankByparentId(area.getParentId());
        Area maxRankArea = areaMapper.findMaxRankByparentId(area.getParentId());

        if(maxRankArea == null){
            maxRank = 0;
        }else {
            maxRank =  maxRankArea.getAreaRank();
        }

        maxRank = (maxRankArea == null) ? 0 : maxRankArea.getAreaRank();

        System.out.println("maxRank:" + maxRank);
        System.out.println("area.getAreaLevel():"+area.getAreaLevel());

        area.setAreaRank(maxRank+1);
        parentRanks[area.getAreaLevel()-1] = maxRank+1;//获取当前节点的Rank列表，用于后面拼接
        //获取所有父节点的Rank，

        System.out.println("area.getParentId():" + area.getParentId());//父节点ID
        findParentRanks(area.getParentId(),parentRanks);//获取所有父节点的Rank，用于后面拼接

        System.out.println(Arrays.toString(parentRanks));//输出[1, 1, 1, 2, 0]

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

    @Override
    public Integer areaEdit(Area area){

        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",area.getAreaId());

        if(Objects.equals(areaMapper.selectOne(wrapper).getParentId(), area.getParentId())) {//如果父节点结构没有改变，不更新区域位次和区域序列号

            return areaMapper.updateById(area);

        }

        //如果父节点结构改变，更新区域位次和区域序列号
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
        System.out.println("maxRank:" + maxRank);
        System.out.println("area.getAreaLevel():" + area.getAreaLevel());

        area.setAreaRank(maxRank+1);//已存在的区域位次最大值再加一，就是新区域的位次
        parentRanks[area.getAreaLevel()-1] = maxRank+1;//减一是因为级别从1开始计算，而数组下标从0开始
        //获取所有父节点的Rank，

        System.out.println("area.getParentId():"+area.getParentId());
        findParentRanks(area.getParentId(),parentRanks);

        System.out.println(Arrays.toString(parentRanks));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parentRanks.length; i++) {

            //判断到达道路级，构造四位十六进制数，插入序列，同时网络编号更新

            String numStr = String.format("%02d", parentRanks[i]);
            sb.append(numStr);

        }

        String result = sb.toString();

        System.out.println(result); // 输出：1234050607

        area.setAreaSerial(result);

        System.out.println(area.toString());



        return areaMapper.update(area,wrapper);
//        return 0;

    }

    @Override
    public Area queryAreaById(Integer areaId) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        return areaMapper.selectOne(wrapper);
    }

    @Override
    public Area queryAreaByName(String areaName) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_name",areaName);
        return areaMapper.selectOne(wrapper);
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

    @Override
    public Integer areaDelete(Integer areaId){

        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",areaId);
        Area area = areaMapper.selectOne(wrapper);

        if(area != null){//已经有子节点
            System.out.println(area.getAreaName());
            return -1;
        }else {
            return areaMapper.deleteById(areaId);
        }



    }

    @Override
    public Area queryAreaById(Integer areaId) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        return areaMapper.selectOne(wrapper);
    }


}
