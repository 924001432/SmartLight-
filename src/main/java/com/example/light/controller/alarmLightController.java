package com.example.light.controller;

import com.example.light.annotation.LogAnnotation;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.entity.Idea;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.IdeaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.util.List;

@Controller
public class alarmLightController {

    @Autowired
    private AlarmService alarmService;

    @RequestMapping("/alarmLight")
    public Object alarmLight(){
        return "/alarm/alarmLight";
    }

    @RequestMapping("/alarmLightTail")
    public Object alarmLightTail(){
        return "/alarm/alarmLightTail";
    }

//    @LogAnnotation
//    @ApiOperation(value = "获取所有报警数据")
    @RequestMapping("/alarmList")
    @ResponseBody
    public Object alarmList(){

        List<Alarm> alarmList = alarmService.queryAlarmList();
        return ResultMapUtil.getHashMapList(alarmList);

    }

//    @LogAnnotation
//    @ApiOperation(value = "根据报警状态获取报警数据")
    @RequestMapping("/alarmListByalarmStatus/{alarmStatus}")
    @ResponseBody
    public Object alarmListByalarmStatus(@PathVariable(name = "alarmStatus",required = true)Integer alarmStatus){

        List<Alarm> alarmList = alarmService.alarmListByalarmStatus(alarmStatus);
        return ResultMapUtil.getHashMapList(alarmList);

    }

//    @LogAnnotation
//    @ApiOperation(value = "查看某设备的报警数据信息")
    @RequestMapping("/alarmListBydeviceSerial/{deviceSerial}")
    @ResponseBody
    public Object alarmListBydeviceSerial(@PathVariable(name = "deviceSerial",required = true)Integer deviceSerial){

        List<Alarm> alarmList = alarmService.alarmListBydeviceSerial(deviceSerial);
        return ResultMapUtil.getHashMapList(alarmList);

    }

    @LogAnnotation
    @ApiOperation(value = "消除报警")
    @RequestMapping("/removeAlarm/{alarmId}")
    @ResponseBody
    public void removeAlarm(@PathVariable(name = "alarmId",required = true)Integer alarmId){

        //手动消除警报和自动消除警报，两种
        alarmService.removeAlarm(alarmId);

    }


}
