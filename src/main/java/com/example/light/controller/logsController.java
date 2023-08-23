package com.example.light.controller;

import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ResultMapUtil;
import com.example.light.entity.SysLogs;
import com.example.light.service.SyslogsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class logsController {

    @Autowired
    private SyslogsService syslogsService;

    @RequestMapping("/logsLight")
    public Object logsLight(){

        return "/logs/logsLight";

    }

//    @LogAnnotation
//    @ApiOperation(value = "查看所有日志")
    @RequestMapping("/logsList")
    @ResponseBody
    public Object logsList(){

        List<SysLogs> logList = syslogsService.queryLogList();

        return ResultMapUtil.getHashMapList(logList);
    }

}
