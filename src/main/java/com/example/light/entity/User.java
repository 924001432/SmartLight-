package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "user")
public class User implements Serializable {

    @TableField(value = "user_id")
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;


    @TableField(value = "user_name")
    private String userName;

    /*电话*/
    @TableField(value = "user_password")
    private String userPassword;
}
