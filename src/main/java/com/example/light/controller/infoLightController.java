package com.example.light.controller;


import com.example.light.entity.Info;

import com.example.light.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return "/infoLight";
    }

    @RequestMapping("/infoLightTail")
    public Object infoLightTail(){
        return "/infoLightTail";
    }

    @RequestMapping("/infoList")
    @ResponseBody
    public Object infoList(){

        List<Info> infoList = infoService.queryInfoList();



        return ResultMapUtil.getHashMapList(infoList);

    }




}

