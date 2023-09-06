package com.example.light.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
	@ExcelProperty("日志编号")
	@ColumnWidth(10)
	private Integer logsId;

	@ExcelProperty("用户编号")
	@ColumnWidth(20)
	@TableField(value = "user_id")
	private String userId;

	@ExcelProperty("用户名")
	@ColumnWidth(20)
	@TableField(value = "user_name")
	private String userName;

	@ExcelProperty("日志模块")
	@ColumnWidth(30)
	@TableField(value = "logs_module")
	private String logsModule;

	@ExcelProperty("日志状态")
	@ColumnWidth(10)
	@TableField(value = "logs_flag")
	private Integer logsFlag;

	@ExcelProperty("日志备注")
	@ColumnWidth(20)
	@TableField(value = "logs_remark")
	private String logsRemark;

	@ExcelProperty("记录时间")
	@ColumnWidth(20)
	@TableField(value = "logs_createtime")
	private String logsCreatetime;

}
