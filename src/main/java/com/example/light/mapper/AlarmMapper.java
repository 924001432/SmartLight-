package com.example.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Alarm;
import java.util.List;

/**
 * 设备Mapper
 */
public interface AlarmMapper extends BaseMapper<Alarm> {

    /**
     * 通过地区网关号查询警报设备
     * @return
     */
    List<Alarm> alarmByAreaAndStatus(Integer deviceCoord, Integer alarmStatus);

    List<Alarm> alarmByArea(Integer deviceCoord);
}
