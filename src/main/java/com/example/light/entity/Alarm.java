package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "alarm")
public class Alarm implements Serializable{

    @TableField(value = "alarm_id")
    @TableId(value = "alarm_id",type = IdType.AUTO)
    private Integer alarmId;

    @TableField(value = "device_serial")
    private String deviceSerial;

    @TableField(value = "alarm_type")
    private Integer alarmType;

    @TableField(value = "alarm_time")
    private String alarmTime;

    @TableField(value = "alarm_status")
    private Integer alarmStatus;

    @TableField(value = "alarm_handletime")
    private String alarmHandletime;


}
