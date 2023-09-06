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

    @TableField(value = "device_coord")
    private String deviceCoord;

    @TableField(value = "device_serial")
    private String deviceSerial;

    @TableField(value = "device_lon")
    private String deviceLon;

    @TableField(value = "device_lat")
    private String deviceLat;

    @TableField(value = "device_type")
    private Integer deviceType;



    @TableField(value = "device_light")
    private Integer deviceLight;

    @TableField(value = "device_status")
    private Integer deviceStatus;

    @TableField(value = "device_model")
    private Integer deviceModel;

    @TableField(value = "device_updatetime")
    private String deviceUpdatetime;

    @TableField(value = "device_hearttime")
    private String deviceHearttime;

//    @TableField(value = "device_longitude")
//    private Double deviceLongitude;
//
//    @TableField(value = "device_latitude")
//    private Double deviceLatitude;




}
