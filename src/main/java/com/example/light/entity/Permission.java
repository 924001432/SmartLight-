package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "permission")
public class Permission implements Serializable {

    @TableField(value = "id")
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    @TableField(value = "parentId")
    private Integer parentId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "href")
    private String href;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "permission")
    private String permission;

    @TableField(value = "sort")
    private Integer sort;

//    private List<Permission> child;


}
