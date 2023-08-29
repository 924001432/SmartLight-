package com.example.light.controller;


import com.example.light.annotation.LogAnnotation;
import com.example.light.entity.Info;

import com.example.light.service.InfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.util.List;

@Controller
public class infoLightController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/infoLight")
    public Object infoLight(){
        return "/info/infoLight";
    }

    @RequestMapping("/infoLightTail")
    public Object infoLightTail(){
        return "/info/infoLightTail";
    }

//    @LogAnnotation
//    @ApiOperation(value = "获取所有环境数据信息")
    @RequestMapping("/infoList")
    @ResponseBody
    public Object infoList(){

        List<Info> infoList = infoService.queryInfoList();



        return ResultMapUtil.getHashMapList(infoList);

    }

//    @LogAnnotation
//    @ApiOperation(value = "查看某设备的环境数据信息")
    @RequestMapping("/infoListByDeviceSerial/{deviceSerial}")
    @ResponseBody
    public Object infoListByDeviceSerial(@PathVariable(name = "deviceSerial",required = true)String deviceSerial){

        List<Info> infoList = infoService.queryInfoListByDeviceSerial(deviceSerial);

        return ResultMapUtil.getHashMapList(infoList);

    }

    @RequestMapping("/infoListByDate/{uptime}")
    @ResponseBody
    public Object infoListByDate(@PathVariable(name = "uptime",required = true)String uptime){

        List<Info> infoList = infoService.queryInfoListByDate(uptime);


        return ResultMapUtil.getHashMapList(infoList);

    }

    @RequestMapping("/testInfo")
    @ResponseBody
    public Object testInfo(){

        List<Info> infoList = infoService.queryInfoListByDate("2023-07-18");


        for (Info info : infoList) {
            System.out.println(info);
        }

        return ResultMapUtil.getHashMapList(infoList);

    }





}

