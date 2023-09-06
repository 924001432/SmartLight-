package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Device;
import com.example.light.entity.Info;
import com.example.light.entity.SysLogs;
import com.example.light.entity.User;
import com.example.light.mapper.SyslogsMapper;
import com.example.light.mapper.UserMapper;
import com.example.light.service.SyslogsService;
import com.example.light.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyslogsServiceImpl extends ServiceImpl<SyslogsMapper, SysLogs> implements SyslogsService {

    @Autowired
    private SyslogsMapper syslogsMapper;

    @Override
    public List<SysLogs> queryLogList(){
        QueryWrapper<SysLogs> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,false,"logs_createtime");

        return syslogsMapper.selectList(wrapper);

    }

    @Override
    public List<SysLogs> queryLogByUserName(String userName) {
        QueryWrapper<SysLogs> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName).orderBy(true,false,"logs_createtime");

        return syslogsMapper.selectList(wrapper);
    }

    @Override
    public List<SysLogs> queryLogByModule(String module) {
        QueryWrapper<SysLogs> wrapper = new QueryWrapper<>();
        wrapper.eq("logs_module",module).orderBy(true,false,"logs_createtime");

        return syslogsMapper.selectList(wrapper);
    }

    @Override
    public List<SysLogs> queryLogByUpTime(String upTime) {
        QueryWrapper<SysLogs> wrapper = new QueryWrapper<>();
        wrapper.like("logs_createtime",upTime).orderBy(true,false,"logs_createtime");

        return syslogsMapper.selectList(wrapper);
    }


}