package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.light.entity.Alarm;
import com.example.light.entity.Device;


import java.util.List;


public interface AlarmService extends IService<Alarm> {

    public Alarm queryAlarmById(Alarm alarm);

    public List<Alarm> queryAlarmList();

    public List<Alarm> alarmListByalarmStatus(Integer alarmStatus);

    public List<Alarm> alarmListBydeviceSerial(Integer deviceSerial);

    public void insertAlarm(Alarm alarm);

    public void removeAlarm(Integer alarmId);
}
