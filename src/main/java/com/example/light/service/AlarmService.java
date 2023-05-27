package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.light.entity.Alarm;


import java.util.List;


public interface AlarmService extends IService<Alarm> {

    public Alarm queryAlarmById(Alarm alarm);

    public List<Alarm> queryAlarmList();

    public void insertAlarm(Alarm alarm);
}
