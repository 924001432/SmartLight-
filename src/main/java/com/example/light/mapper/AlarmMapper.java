package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Alarm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备Mapper
 */
@Repository
public interface AlarmMapper extends BaseMapper<Alarm> {

    /**
     * 通过地区网关号查询警报设备
     * @return
     */
    List<Alarm> alarmByAreaAndStatus(String deviceCoord, Integer alarmStatus);

    List<Alarm> alarmByArea(String deviceCoord);
}
