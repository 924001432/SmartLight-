package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "role")
public class Role {

    @TableField(value = "role_id")
    @TableId(value = "role_id",type = IdType.AUTO)
    Integer roleId;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "role_remark")
    private String roleRemark;

    @TableField(value = "role_createtime")
    private String roleCreatetime;

    @TableField(value = "role_updatetime")
    private String roleUpdatetime;

    @TableField(value = "role_level")
    private Integer roleLevel;

}
