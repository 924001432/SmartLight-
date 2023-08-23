package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role_permission")
public class RolePermission {

    @TableField(value = "role_id")
    @TableId(value = "role_id")//type = IdType.AUTO 会导致报错，提示role_id没有默认值
    Integer roleId;

    @TableField(value = "permission_id")
    Integer permissionId;


}
