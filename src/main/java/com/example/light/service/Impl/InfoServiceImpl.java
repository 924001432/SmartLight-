package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.light.entity.Info;
import com.example.light.mapper.InfoMapper;
import com.example.light.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

    @Autowired
    private  InfoMapper infoMapper;

    @Override
    public Info queryInfoById(Integer id) {
        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.eq("info_id",id);
        return infoMapper.selectOne(wrapper);
    }

    @Override
    public List<Info> queryInfoList(){
        //return this.list();
        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,false,"info_updatetime");
        return infoMapper.selectList(wrapper);
    }

    @Override
    public List<Info> queryInfoListByDeviceSerial(String deviceSerial){

        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.eq("info_serial",deviceSerial).orderBy(true,false,"info_updatetime");
        return infoMapper.selectList(wrapper);

    }

    @Override
    public List<Info> queryInfoListByDate(String uptime){



        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.like("info_updatetime",uptime).orderBy(true,false,"info_updatetime");
//        wrapper.eq("info_updatetime",uptime);
        return infoMapper.selectList(wrapper);

    }


}
