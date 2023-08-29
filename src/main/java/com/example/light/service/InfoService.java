package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Info;

import java.util.List;


public interface InfoService extends IService<Info> {

    public Info queryInfoById(Integer id);

    public List<Info> queryInfoList();

    public List<Info> queryInfoListByDeviceSerial(String deviceSerial);

    public List<Info> queryInfoListByDate(String uptime);

    //public void insertIdea(Idea idea);
}