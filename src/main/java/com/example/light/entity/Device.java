package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "device")
public class Device implements Serializable {

    @TableField(value = "device_id")
    @TableId(value = "device_id",type = IdType.AUTO)
    private Integer deviceId;

    @TableField(value = "device_mac")
    private String deviceMac;

    @TableField(value = "device_short")
    private String deviceShort;

    @TableField(value = "device_serial")
    private String deviceSerial;

    @TableField(value = "device_type")
    private Integer deviceType;

    @TableField(value = "device_coord")
    private String deviceCoord;

    @TableField(value = "device_light")
    private Integer deviceLight;

    @TableField(value = "device_status")
    private Integer deviceStatus;

    @TableField(value = "device_updatetime")
    private String deviceUpdatetime;

    @TableField(value = "device_hearttime")
    private String deviceHearttime;


}
