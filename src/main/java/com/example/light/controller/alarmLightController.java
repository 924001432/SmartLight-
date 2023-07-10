package com.example.light.controller;

import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.entity.Idea;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return "/alarmLight";
    }

    @RequestMapping("/alarmLightTail")
    public Object alarmLightTail(){
        return "/alarmLightTail";
    }

    @RequestMapping("/alarmList")
    @ResponseBody
    public Object alarmList(){

        List<Alarm> alarmList = alarmService.queryAlarmList();
        return ResultMapUtil.getHashMapList(alarmList);

    }

    @RequestMapping("/alarmListByalarmStatus")
    @ResponseBody
    public Object alarmListByalarmStatus(){

        List<Alarm> alarmList = alarmService.alarmListByalarmStatus(0);
        return ResultMapUtil.getHashMapList(alarmList);

    }


}
