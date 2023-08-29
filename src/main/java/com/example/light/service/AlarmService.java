package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.light.entity.Alarm;
import com.example.light.entity.Device;


import java.util.List;


public interface AlarmService extends IService<Alarm> {

    public Alarm queryAlarmById(Integer alarmId);

    public List<Alarm> queryAlarmList();

    public List<Alarm> alarmListByalarmStatus(Integer alarmStatus);

    public List<Alarm> alarmListBydeviceSerial(Integer deviceSerial);

    public void insertAlarm(Alarm alarm);

    public Integer removeAlarm(Integer alarmId);

    public Integer repairAlarm(Integer alarmId);
}
