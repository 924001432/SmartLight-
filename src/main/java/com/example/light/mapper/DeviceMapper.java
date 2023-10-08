package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Device;
import org.springframework.stereotype.Repository;

/**
 * 设备Mapper
 */
@Repository
public interface DeviceMapper extends BaseMapper<Device> {
    void updateOnlineStatus(Integer deviceId, int status);
}
