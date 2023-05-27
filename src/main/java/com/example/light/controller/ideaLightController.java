package com.example.light.controller;


import com.example.light.entity.Idea;

import com.example.light.mqtt.MyMqttClient;
import com.example.light.service.IdeaService;
import com.example.light.service.NewsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.util.List;

@Controller
public class ideaLightController {

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private NewsProducerService newsProducerService;

    private boolean connect_tag = false;

    MyMqttClient mqttClient;

    @RequestMapping("/ideaLight")
    public Object ideaLight(){
        return "/ideaLight";
    }

    @RequestMapping("/ideaLightTail")
    public Object ideaLightTail(){
        return "/ideaLightTail";
    }

    @RequestMapping("/ideaList")
    @ResponseBody
    public Object ideaList(){

        List<Idea> ideaList = ideaService.queryIdeaList();
        return ResultMapUtil.getHashMapList(ideaList);

    }


    @RequestMapping("/performIdea/{ideaOpentime}")
    @ResponseBody
    public void performIdea(@PathVariable(name = "ideaOpentime",required = true)String ideaOpentime){

        byte[] period = {0x06,0x00,0x00,0x0F,0x3F,0x00,0x00,0x3B,0x3F,0x00,0x00,0x1E,0x23};


//        System.out.println(ideaOpentime);
//
//        byte startHour = (byte)Integer.parseInt(ideaOpentime.substring(0, 2));
//        byte startMinutes = (byte)Integer.parseInt(ideaOpentime.substring(3, 5));
//
//        period[1] = startHour;
//        period[2] = startMinutes;
//
//        byte finishHour = (byte)Integer.parseInt(ideaOpentime.substring(6, 8));
//        byte finishMinutes = (byte)Integer.parseInt(ideaOpentime.substring(9));
//
//        period[5] = finishHour;
//        period[6] = finishMinutes;

        //发布消息
        newsProducerService.publishBytes(period);

    }






}
