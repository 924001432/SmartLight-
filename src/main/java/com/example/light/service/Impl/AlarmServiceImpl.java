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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {

    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public Alarm queryAlarmById(Integer alarmId) {
        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
        wrapper.eq("alarm_id",alarmId);
        return alarmMapper.selectOne(wrapper);
    }

    @Override
    public List<Alarm> queryAlarmList(){
        //return this.list();

        return alarmMapper.selectList(null);
    }

    @Override
    public List<Alarm> alarmListByalarmStatus(Integer alarmStatus){

        if(alarmStatus != 0){
            QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
            wrapper.eq("alarm_status",alarmStatus).orderBy(true,false,"alarm_handletime");

            return alarmMapper.selectList(wrapper);
        }else {
            QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
            wrapper.eq("alarm_status",alarmStatus).orderBy(true,false,"alarm_time");

            return alarmMapper.selectList(wrapper);

        }


    }

    @Override
    public List<Alarm> alarmListBydeviceSerial(Integer deviceSerial){

        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
        wrapper.eq("device_serial",deviceSerial)
                .eq("alarm_status",0);

        return alarmMapper.selectList(wrapper);
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

    @Override
    public Integer removeAlarm(Integer alarmId){

        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
        wrapper.eq("alarm_id",alarmId);

        Alarm alarm = new Alarm();
        alarm.setAlarmStatus(1);



        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String handle_time = sf.format(date);

        alarm.setAlarmHandletime(handle_time);
        alarm.setAlarmHandlecomment("手动消除");

        return alarmMapper.update(alarm,wrapper);
    }

    @Override
    public Integer repairAlarm(Integer alarmId){

        QueryWrapper<Alarm> wrapper = new QueryWrapper<>();
        wrapper.eq("alarm_id",alarmId);

        Alarm alarm = new Alarm();
        alarm.setAlarmStatus(1);

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String handle_time = sf.format(date);

        alarm.setAlarmHandletime(handle_time);
        alarm.setAlarmHandlecomment("报修中");

        return alarmMapper.update(alarm,wrapper);
    }

    @Override
    public List<Alarm> alarmListByDeviceCoord(Integer deviceCoord , Integer alarmStatus) {

        return alarmMapper.alarmByAreaAndStatus(deviceCoord,alarmStatus);
    }

    @Override
    public List<Alarm> alarmListByArea(Integer deviceCoord) {
        return alarmMapper.alarmByArea(deviceCoord);
    }
}
