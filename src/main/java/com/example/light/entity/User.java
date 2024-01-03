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


    @TableField(value = "user_password")
    private String userPassword;

    /*电话*/
    @TableField(value = "user_phone")
    private String userPhone;

    @TableField(value = "user_email")
    private String userEmail;

    @TableField(value = "user_role")
    private Integer userRole;

    @TableField(value = "user_area")
    private Integer userArea;

    @TableField(value = "user_builder")
    private Integer userBuilder;

    @TableField(value = "user_createtime")
    private String userCreatetime;

    @TableField(value = "user_updatetime")
    private String userUpdatetime;

    @TableField(value = "user_wx_id")
    private String userwxId;



}
