package com.example.light.controller;

import com.example.light.annotation.LogAnnotation;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.entity.Idea;
import com.example.light.entity.User;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.IdeaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.util.ArrayList;
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
    public Object removeAlarm(@PathVariable(name = "alarmId",required = true)Integer alarmId){

        //手动消除警报和自动消除警报，两种
//        alarmService.removeAlarm(alarmId);

        try{
            int i = alarmService.removeAlarm(alarmId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 报修页面
     */
    @RequestMapping("/alarmRepairPage/{alarmId}")
    public Object alarmRepairPage(@PathVariable(name = "alarmId",required = true)Integer alarmId, Model model){


        Alarm alarm = alarmService.queryAlarmById(alarmId);
        model.addAttribute("obj",alarm);


        return "/alarm/alarmRepairPage";
    }

    /**
     *
     * 报修处理过程，新建一张表，报修情况
     * @param
     * @return
     */
    @GetMapping("/alarmRepair")
    @ResponseBody
    public Object alarmRepair(Alarm alarm){
        //存在问题：页面转换数据时，替换原来的int型为string导致数据库存储出现问题


        System.out.println(alarm.toString());

        //将状态改为  “待维修”



        try{
            int i = alarmService.repairAlarm(alarm.getAlarmId());
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    @LogAnnotation
    @ApiOperation(value = "查看某区域的所有报警信息")
    @RequestMapping("/alarmListByArea/{deviceCoord}")
    @ResponseBody
    public Object alarmListByArea(@PathVariable(name = "deviceCoord",required = true)Integer deviceCoord){

        System.out.println("deviceCoord: " + deviceCoord);

        List<Alarm> alarmList = alarmService.alarmListByArea(deviceCoord);

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码

        return ResultMapUtil.getHashMapList(alarmList);

    }

    @LogAnnotation
    @ApiOperation(value = "查看某区域的所有报警信息按状态查看")
    @RequestMapping("/alarmListByalarmArea/{deviceCoord}/{alarmStatus}")
    @ResponseBody
    public Object alarmListByalarmArea(@PathVariable(name = "deviceCoord",required = true)Integer deviceCoord, @PathVariable(name = "alarmStatus",required = true)Integer alarmStatus){

        System.out.println("deviceCoord: " + deviceCoord);

        List<Alarm> alarmList = alarmService.alarmListByDeviceCoord(deviceCoord,alarmStatus);

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码

        return ResultMapUtil.getHashMapList(alarmList);

    }

    @RequestMapping("/alarmListByDeviceCoordList/{deviceCoordList}/{alarmStatus}")
    @ResponseBody
    public Object alarmListByDeviceCoordList(@PathVariable(name = "deviceCoordList",required = true)List<Integer> deviceCoordList, @PathVariable(name = "alarmStatus",required = true)Integer alarmStatus){

        List<Alarm> alarmList = new ArrayList<>();

        for (Integer integer : deviceCoordList) {
            alarmList.addAll(alarmService.alarmListByDeviceCoord(integer,alarmStatus));

        }

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码

        return ResultMapUtil.getHashMapList(alarmList);

    }


}
