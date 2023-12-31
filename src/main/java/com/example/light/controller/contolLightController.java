package com.example.light.controller;

import com.example.light.annotation.LogAnnotation;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.mqtt.MyMqttClient;
import com.example.light.service.DeviceService;
import com.example.light.service.NewsProducerService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.light.common.ResultMapUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class contolLightController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private NewsProducerService newsProducerService;

    private boolean connect_tag = false;

    MyMqttClient mqttClient;

    @RequestMapping("/controlLight")
    public Object controlLight(){
        return "/control/controlLight";
    }

    @RequestMapping("/controlLightTail")
    public Object controlLightTail(){
        return "/control/controlLightTail";
    }

//    @LogAnnotation
//    @ApiOperation(value = "查看所有设备信息")
    @RequestMapping("/deviceList")
    @ResponseBody
    public Object deviceList() throws ParseException {

        List<Device> deviceList = deviceService.queryDeviceList();

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        for (Device device : deviceList) {
            if (cal_time(device.getDeviceHearttime())){
                device.setDeviceStatus(1);
            }else {
                device.setDeviceStatus(0);
            }
        }

        return ResultMapUtil.getHashMapList(deviceList);
    }





    /**
     * 查看某最低区域的所有设备信息
     * @param deviceCoord
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByDeviceCoord/{deviceCoord}")
    @ResponseBody
    public Object deviceListByDeviceCoord(@PathVariable(name = "deviceCoord",required = true)String deviceCoord) throws ParseException {

//        System.out.println("deviceCoord: " + deviceCoord);

        List<Device> deviceList = deviceService.deviceListByDeviceCoord(deviceCoord);

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码
        for (Device device : deviceList) {
            if (cal_time(device.getDeviceHearttime())){
                device.setDeviceStatus(1);
                deviceService.updateOnlineStatus(device.getDeviceId(),1);
//                System.out.println("更新为在线");
            }else {
                device.setDeviceStatus(0);
                deviceService.updateOnlineStatus(device.getDeviceId(),0);
//                System.out.println("更新为离线");
            }
        }

        return ResultMapUtil.getHashMapList(deviceList);

    }

    /**
     * 查看某最低区域的在线设备信息
     * @param deviceCoord
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByIsOnline/{deviceCoord}")
    @ResponseBody
    public Object deviceListByIsOnline(@PathVariable(name = "deviceCoord",required = true)String deviceCoord) throws ParseException {

//        System.out.println("deviceCoord: " + deviceCoord);

        List<Device> deviceList = deviceService.deviceListByIsOnline(deviceCoord);

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码

        return ResultMapUtil.getHashMapList(deviceList);

    }

    /**
     * 查看某最低区域的离线设备信息
     * @param deviceCoord
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByIsNotOnline/{deviceCoord}")
    @ResponseBody
    public Object deviceListByIsNotOnline(@PathVariable(name = "deviceCoord",required = true)String deviceCoord) throws ParseException {

//        System.out.println("deviceCoord: " + deviceCoord);

        List<Device> deviceList = deviceService.deviceListByIsNotOnline(deviceCoord);

        //遍历列表，获得当前时间，二者做差，如果大于比如20s，更新数据库为离线
        //插入代码

        return ResultMapUtil.getHashMapList(deviceList);

    }


    /**
     * 查看某非最低区域的所有设备信息
     * @param deviceCoordList
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByDeviceCoordList/{deviceCoordList}")
    @ResponseBody
    public Object deviceListByDeviceCoordList(@PathVariable(name = "deviceCoordList",required = true)List<String> deviceCoordList) throws ParseException {


        List<Device> deviceList = new ArrayList<>();

        for (String s : deviceCoordList) {
            deviceList.addAll(deviceService.deviceListByDeviceCoord(s));

        }

        for (Device device : deviceList) {
            if (cal_time(device.getDeviceHearttime())){
                device.setDeviceStatus(1);//在线
            }else {
                device.setDeviceStatus(0);
            }
        }
        return ResultMapUtil.getHashMapList(deviceList);
    }

    /**
     * 查看某非最低区域的在线设备信息
     * @param deviceCoordList
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByIsOnlineList/{deviceCoordList}")
    @ResponseBody
    public Object deviceListByIsOnlineList(@PathVariable(name = "deviceCoordList",required = true)List<String> deviceCoordList) throws ParseException {

        List<Device> deviceList = new ArrayList<>();

        for (String s : deviceCoordList) {
            deviceList.addAll(deviceService.deviceListByIsOnline(s));

        }
        return ResultMapUtil.getHashMapList(deviceList);

    }

    /**
     * 查看某非最低区域的离线设备信息
     * @param deviceCoordList
     * @return
     * @throws ParseException
     */
    @RequestMapping("/deviceListByIsNotOnlineList/{deviceCoordList}")
    @ResponseBody
    public Object deviceListByIsNotOnlineList(@PathVariable(name = "deviceCoordList",required = true)List<String> deviceCoordList) throws ParseException {


        List<Device> deviceList = new ArrayList<>();

        for (String s : deviceCoordList) {
            deviceList.addAll(deviceService.deviceListByIsNotOnline(s));

        }
        return ResultMapUtil.getHashMapList(deviceList);

    }

    @LogAnnotation
    @ApiOperation(value = "校准时间")
    @RequiresPermissions("sys:user:qqq")
    @RequestMapping("/SetTime")
    @ResponseBody
    public Object SetTime(){

        /*
            采用方法取当前时间转字节
         */

        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x03,0x00,0x00,0x00,0x23};

        //发布消息
        newsProducerService.publishBytes(payload);
        return ResultMapUtil.getHashMapSave(1);

    }

    @LogAnnotation
    @ApiOperation(value = "开启全部路灯")
    @RequestMapping("/AllLightControl/{PanId}&{type}")
    @ResponseBody
    public void AllLightControl(@PathVariable(name = "PanId",required = true)String PanId, @PathVariable(name = "type",required = true)Integer type){

        System.out.println(PanId);
        System.out.println(type);
        //协议数据包
        byte[] payload={0x58,0x44,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x04,0x02,0x00,0x00,0x23};
//        byte[] payload={0x02,0x44,0x01,0x04,(byte)0xFF,0x00,0x00,0x00,0x00,0x23};

        //PanId
        payload[2] = (byte) Integer.parseInt(PanId.substring(2));
        payload[3] = (byte) Integer.parseInt(PanId.substring(0,2));


        //控制命令类型
        if(type == 1){//全开
            payload[18]=0x01;
        }else if(type == 2){//全关
            payload[18]=0x02;
        }else{//雷达控制
            payload[18]=0x03;
        }

        System.out.println(Arrays.toString(payload));
        //发布消息
        newsProducerService.publishBytes(payload);

    }


    @RequestMapping("/SingleLightControl/{id}&{type}")
    @ResponseBody
    public void SingleLightControl(@PathVariable(name = "id",required = true)Integer id, @PathVariable(name = "type",required = true)Integer type){

        byte[] payload={0x58,0x44,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x04,0x02,0x00,0x00,0x23};

        /*
            需要修改为按照返回数据来定义编号，测试int对应的byte型数据
         */
        //路灯编号
        if(id == 1){//全开
            payload[14]=0x01;
        }else if(id == 2){//全关
            payload[14]=0x02;
        }else{//雷达控制
            payload[14]=0x03;
        }

        //控制命令类型
        if(type == 1){//全开
            payload[18]=0x01;
        }else if(type == 2){//全关
            payload[18]=0x02;
        }else{//雷达控制
            payload[18]=0x03;
        }

        //发布消息
        newsProducerService.publishBytes(payload);

        System.out.println("id:" + id + "type:" + type);
    }

    /**
     *
     * @param heart_time 心跳包获取时间
     * @return
     */

    public boolean cal_time(String heart_time) throws ParseException {

        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 当前时间
        Date now = new Date();

        Date d1=df.parse(heart_time);
        // 时间差：天、时、分、秒
        long diff = now.getTime() - d1.getTime();

//        System.out.println(df.format(now.getTime()));
//        System.out.println(df.format(d1.getTime()));

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffSeconds = diff / 1000 % 60;

        //小于20s，在线
        return ((diffMinutes * 60) + diffSeconds) <= 20;
    }

    /**
     * 更新设备数据
     * @param updatedDevice
     */
    @PostMapping("/updateDevice")
    @ResponseBody
    public Object updateDevice(@RequestBody Device updatedDevice) {
        deviceService.insertDevice(updatedDevice);
        System.out.println("设备数据已成功更新");
        return ResultMapUtil.getHashMapSave(1);
    }

    @RequestMapping("/queryDeviceBySerial/{deviceSerial}")
    @ResponseBody
    public Device queryDeviceBySerial(@PathVariable(name = "deviceSerial",required = true)String deviceSerial){
        Device device = deviceService.queryDeviceBySerial(deviceSerial);
        return device;
    }


}
