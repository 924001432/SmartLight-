package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "report_order")
public class Order {
    @TableField(value = "order_id")
    @TableId(value = "order_id",type = IdType.AUTO)
    private Integer orderId;

    @TableField(value = "alarm_id")
    private Integer alarmId;

    @TableField(value = "reporter_id")
    private Integer reporterId;

    @TableField(value = "report_time")
    private String reportTime;

    @TableField(value = "order_status")
    private Integer orderStatus;

    @TableField(value = "handler_id")
    private Integer handlerId;

    @TableField(value = "handle_time")
    private String handleTime;

    @TableField(value = "handle_result_id")
    private Integer handleResultId;

    @TableField(value = "order_area")
    private String orderArea;

    @TableField(value = "device_serial")
    private String deviceSerial;

    @TableField(value = "receive_time")
    private String receiveTime;

    @TableField(value = "cancel_reason")
    private String cancelReason;
}
