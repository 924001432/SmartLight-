package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "stock")
public class Stock implements Serializable {

    //编号和名称不能重复
    @TableField(value = "stock_id")
    @TableId(value = "stock_id",type = IdType.AUTO)
    private Integer stockId;

    @TableField(value = "device_serial")
    private String deviceSerial;

    @TableField(value = "device_type")
    private Integer deviceType;

    @TableField(value = "device_name")
    private String deviceName;

    @TableField(value = "device_model")
    private String deviceModel;

    @TableField(value = "device_brand")
    private String deviceBrand;

    @TableField(value = "stock_batch")
    private String stockBatch;

    @TableField(value = "stock_user")
    private String stockUser;

    @TableField(value = "device_producetime")
    private String deviceProducetime;

    @TableField(value = "stock_intime")
    private String stockIntime;

    @TableField(value = "stock_outtime")
    private String stockOuttime;

    @TableField(value = "stock_updatetime")
    private String stockUpdatetime;

    @TableField(value = "stock_status")
    private Integer stockStatus;

    @TableField(value = "device_repairnum")
    private Integer deviceRepairnum;



}
