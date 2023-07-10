package com.example.light.controller;

import com.example.light.entity.Device;
import com.example.light.mqtt.MyMqttClient;
import com.example.light.service.DeviceService;
import com.example.light.service.NewsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return "/controlLight";
    }

    @RequestMapping("/controlLightTail")
    public Object controlLightTail(){
        return "/controlLightTail";
    }


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

    @RequestMapping("/deviceListByDeviceCoord/{deviceCoord}")
    @ResponseBody
    public Object deviceListByDeviceCoord(@PathVariable(name = "deviceCoord",required = true)Integer deviceCoord){

//        System.out.println("deviceCoord: " + deviceCoord);

        List<Device> deviceList = deviceService.deviceListByDeviceCoord(deviceCoord);
        return ResultMapUtil.getHashMapList(deviceList);

    }

    @RequestMapping("/SetTime")
    @ResponseBody
    public void SetTime(){

        /*
            采用方法取当前时间转字节
         */
        //当前时间
//        byte[] payload={0x05,0x00,0x00,0x00,0x23};
        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x03,0x00,0x00,0x00,0x23};

        //发布消息
        newsProducerService.publishBytes(payload);

    }

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

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffSeconds = diff / 1000 % 60;

            //小于20s，在线
            return ((diffMinutes * 60) + diffSeconds) <= 20;


    }
}
