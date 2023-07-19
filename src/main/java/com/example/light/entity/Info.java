package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "info")
public class Info implements Serializable {

    @TableField(value = "info_id")
    @TableId(value = "info_id", type = IdType.AUTO)
    private Integer infoId;

    @TableField(value = "info_serial")
    private String infoSerial;

    @TableField(value = "info_temp")
    private Double infoTemp;

    @TableField(value = "info_humi")
    private Double infoHumi;

    @TableField(value = "info_lampV")
    private Double infoLampV;

    @TableField(value = "info_boardV")
    private Double infoBoardV;

    @TableField(value = "info_lux")
    private Double infoLux;

    @TableField(value = "info_air")
    private Double infoAir;

    @TableField(value = "info_updatetime")
    private String infoUpdatetime;


//    private String
}
