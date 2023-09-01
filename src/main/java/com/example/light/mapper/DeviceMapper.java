package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Device;

/**
 * 设备Mapper
 */
public interface DeviceMapper extends BaseMapper<Device> {
    void updateOnlineStatus(Integer deviceId, int status);
}
