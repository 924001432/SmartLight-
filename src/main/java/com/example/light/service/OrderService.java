package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.HandleImage;
import com.example.light.entity.HandleResult;
import com.example.light.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    List<Order> orderListByReporterId(Integer reporterId);

    Integer orderAdd(Order order);

    List<Order> orderListByReporterIdAndStatus(Integer reporterId, Integer status);

    HandleResult handleResultById(Integer handleResultId);

    List<HandleImage> imagePathById(Integer handleResultId);

    Order queryByAlarmId(Integer alarmId);

    Integer updateOrderStatusByOrderId(Integer orderId, Integer status);

    List<Order> orderListByAreaAndStatus(String area, Integer status);

    List<Order> orderListByHandlerIdAndStatus(Integer handlerId, Integer status);

    Integer receiveOrder(Integer orderId, Integer handlerId);

    List<Order> getAllReceivedOrder();

    Integer cancelOrder(Integer orderId, String cancelReason);

    List<Order> orderListByHandlerId(Integer handlerId);

    Integer resultAdd(String handleResultText);

    Integer imgAdd(String img, Integer resultId);

    Integer setOrderResult(Integer orderId, Integer resultId);
}
