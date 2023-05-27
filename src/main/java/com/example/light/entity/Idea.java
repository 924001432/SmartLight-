package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "idea")
public class Idea implements Serializable{

    @TableField(value = "idea_id")
    @TableId(value = "idea_id",type = IdType.AUTO)
    private Integer ideaId;

    @TableField(value = "idea_name")
    private String ideaName;

    @TableField(value = "idea_status")
    private String ideaStatus;

    @TableField(value = "idea_opentime")
    private String ideaOpentime;

    @TableField(value = "idea_freetime")
    private String ideaFreetime;

    @TableField(value = "idea_day")
    private String ideaDay;



//    private String

}
