package com.example.light.controller;


import com.example.light.common.ResultMapUtil;
import com.example.light.entity.Device;
import com.example.light.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

@Controller
public class deviceInfoController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping("/deviceInfoLight")
    public Object deviceInfoLight(){
        return "/info/deviceInfoLight";
    }

    @RequestMapping("/deviceInfoList")
    @ResponseBody
    public Object deviceInfoList() throws ParseException {

        List<Device> deviceList = deviceService.queryDeviceList();



        return ResultMapUtil.getHashMapList(deviceList);
    }
}
