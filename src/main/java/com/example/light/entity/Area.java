package com.example.light.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "area")
public class Area {

    @TableField(value = "area_id")
    @TableId(value = "area_id",type = IdType.AUTO)
    Integer areaId;

    @TableField(value = "area_name")
    String areaName;

    @TableField(value = "parent_id")
    Integer parentId;

    @TableField(value = "area_level")
    Integer areaLevel;

    @TableField(value = "area_rank")
    Integer areaRank;

    @TableField(value = "area_serial")
    String areaSerial;

    @TableField(value = "area_net")
    String areaNet;

    @TableField(value = "area_lon")
    String areaLon;

    @TableField(value = "area_lat")
    String areaLat;


}
