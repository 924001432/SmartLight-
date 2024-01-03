package com.example.light.dto;

import com.example.light.entity.Order;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends Order {
    public OrderDto(Order order) {
        this.setOrderId(order.getOrderId());
        this.setAlarmId(order.getAlarmId());
        this.setOrderStatus(order.getOrderStatus());
        this.setReportTime(order.getReportTime());
        this.setOrderArea(order.getOrderArea());
        this.setDeviceSerial(order.getDeviceSerial());
        this.setReporterId(order.getReporterId());
        this.setCancelReason(order.getCancelReason());
        this.setHandlerId(order.getHandlerId());
        this.setHandleResultId(order.getHandleResultId());
        this.setReceiveTime(order.getReceiveTime());
        this.setHandleTime(order.getHandleTime());
    }

    private Integer alarmType;

    private String alarmTime;

    private String deviceLon;

    private String deviceLat;

    private String resultText;

    private List<String> resultImages;

    private String reporterPhone;

    private String handlerPhone;
}
