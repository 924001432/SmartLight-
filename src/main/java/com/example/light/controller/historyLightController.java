package com.example.light.controller;

import com.example.light.entity.Device;
import com.example.light.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;

import java.util.List;

@Controller
public class historyLightController {



    @RequestMapping("/historyLight")
    public Object historyLight(){
        return "/historyLight";
    }

    @RequestMapping("/historyLightTail")
    public Object historyLightTail(){
        return "/historyLightTail";
    }




}
