package com.example.light.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试实体类
 */
@Data
@TableName(value = "test")
public class testEntity implements Serializable {
    /*主键*/
    @TableField(value = "test_id")
    @TableId(value = "test_id",type = IdType.AUTO)
    private Integer testId;


    @TableField(value = "username")
    private String userName;

    /*电话*/
    @TableField(value = "password")
    private String password;

}
