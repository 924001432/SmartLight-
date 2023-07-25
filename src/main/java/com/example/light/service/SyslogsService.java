package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Device;
import com.example.light.entity.SysLogs;
import com.example.light.entity.User;

import java.util.List;


/**
 * 用户表的service接口
 */
public interface SyslogsService extends IService<SysLogs> {
    /**
     * 根据用户名查询用户对象
     */

    public List<SysLogs> queryLogList();


}
