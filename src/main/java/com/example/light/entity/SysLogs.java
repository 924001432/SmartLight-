package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "logs")
public class SysLogs implements Serializable {

	@TableField(value = "logs_id")
	@TableId(value = "logs_id",type = IdType.AUTO)
	private Integer logsId;

	@TableField(value = "user_id")
	private String userId;

	@TableField(value = "user_name")
	private String userName;

	@TableField(value = "logs_module")
	private String logsModule;

	@TableField(value = "logs_flag")
	private Integer logsFlag;

	@TableField(value = "logs_remark")
	private String logsRemark;

	@TableField(value = "logs_createtime")
	private String logsCreatetime;

}
