package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.entity.Idea;
import com.example.light.mapper.AlarmMapper;
import com.example.light.mapper.DeviceMapper;
import com.example.light.mapper.IdeaMapper;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {

    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public Alarm queryAlarmById(Alarm alarm) {
        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
        wrapper.eq("alarm_id",alarm.getAlarmId());
        return alarmMapper.selectOne(wrapper);
    }

    @Override
    public List<Alarm> queryAlarmList(){
        //return this.list();

        return alarmMapper.selectList(null);
    }

    @Override
    public void insertAlarm(Alarm alarm){

//        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
//        wrapper.eq("device_serial",device.getDeviceSerial());
//
//        if(deviceMapper.selectOne(wrapper)!=null){
//            System.out.println("update over");
//            deviceMapper.update(device,wrapper);
//        }else {
//            System.out.println("insert over");
//
//        }
        /*
            不为空则更新，空则插入
         */
        alarmMapper.insert(alarm);
    }
}
