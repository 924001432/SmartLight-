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
    public Object deviceList(){
        List<Device> deviceList = deviceService.queryDeviceList();
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
        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x01,0x03,0x00,0x00,0x00,0x23};

        //发布消息
        newsProducerService.publishBytes(payload);

    }

    @RequestMapping("/AllLightControl/{type}")
    @ResponseBody
    public void AllLightControl(@PathVariable(name = "type",required = true)Integer type){

        //协议数据包
        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x04,0x02,0x00,0x00,0x23};
//        byte[] payload={0x02,0x44,0x01,0x04,(byte)0xFF,0x00,0x00,0x00,0x00,0x23};
        //控制命令类型
        if(type == 1){//全开
            payload[8]=0x01;
        }else if(type == 2){//全关
            payload[8]=0x02;
        }else{//雷达控制
            payload[8]=0x03;
        }

        //发布消息
        newsProducerService.publishBytes(payload);

    }


    @RequestMapping("/SingleLightControl/{id}&{type}")
    @ResponseBody
    public void SingleLightControl(@PathVariable(name = "id",required = true)Integer id, @PathVariable(name = "type",required = true)Integer type){

        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x04,0x02,0x00,0x00,0x23};

        /*
            需要修改为按照返回数据来定义编号，测试int对应的byte型数据
         */
        //路灯编号
        if(id == 1){//全开
            payload[4]=0x01;
        }else if(id == 2){//全关
            payload[4]=0x02;
        }else{//雷达控制
            payload[4]=0x03;
        }

        //控制命令类型
        if(type == 1){//全开
            payload[8]=0x01;
        }else if(type == 2){//全关
            payload[8]=0x02;
        }else{//雷达控制
            payload[8]=0x03;
        }

        //发布消息
        newsProducerService.publishBytes(payload);

        System.out.println("id:" + id + "type:" + type);
    }
}
