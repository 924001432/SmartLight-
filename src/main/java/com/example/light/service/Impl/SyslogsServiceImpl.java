package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Device;
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

        return syslogsMapper.selectList(null);

    }



}