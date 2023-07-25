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
    private InfoMapper infoMapper;

    @Override
    public Info queryInfoById(Integer id) {
        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.eq("info_id",id);
        return infoMapper.selectOne(wrapper);
    }

    @Override
    public List<Info> queryInfoList(){
        //return this.list();

        return infoMapper.selectList(null);
    }

    @Override
    public List<Info> queryInfoListByDeviceSerial(Integer deviceSerial){

        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.eq("info_serial",deviceSerial);
        return infoMapper.selectList(wrapper);

    }
}
