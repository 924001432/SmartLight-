package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.HandleImage;
import com.example.light.entity.HandleResult;
import com.example.light.entity.Order;
import com.example.light.mapper.HandleImageMapper;
import com.example.light.mapper.HandleResultMapper;
import com.example.light.mapper.OrderMapper;
import com.example.light.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private HandleResultMapper handleResultMapper;
    @Autowired
    private HandleImageMapper handleImageMapper;

    @Override
    public List<Order> orderListByReporterId(Integer reporterId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("reporter_id",reporterId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Integer orderAdd(Order order) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String report_time = sf.format(date);
        if(order.getReportTime() == null) {//如果已经有，就不设置
            order.setReportTime(report_time);
        }
        order.setOrderStatus(0);//待接单
        return orderMapper.insert(order);
    }

    @Override
    public List<Order> orderListByReporterIdAndStatus(Integer reporterId, Integer status) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("reporter_id",reporterId)
                .eq("order_status",status);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public HandleResult handleResultById(Integer handleResultId) {
        QueryWrapper<HandleResult> wrapper = new QueryWrapper<>();
        wrapper.eq("handle_result_id",handleResultId);
        return handleResultMapper.selectOne(wrapper);
    }

    @Override
    public List<HandleImage> imagePathById(Integer handleResultId) {
        QueryWrapper<HandleImage> wrapper = new QueryWrapper<>();
        wrapper.eq("handle_result_id",handleResultId);
        return handleImageMapper.selectList(wrapper);
    }

    /**
     * 通过故障编号查询订单
     * @param alarmId
     * @return
     */
    @Override
    public Order queryByAlarmId(Integer alarmId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("alarm_id",alarmId);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public Integer updateOrderStatusByOrderId(Integer orderId, Integer status) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId) // 设置更新条件，例如订单ID等于指定值
                .set("order_status", status); // 设置要更新的字段和新值
        return orderMapper.update(null, wrapper); // 使用wrapper作为参数执行更新操作
    }

    @Override
    public List<Order> orderListByAreaAndStatus(String area, Integer status) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.like("order_area", area + "%")
                .eq("order_status",status);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public List<Order> orderListByHandlerIdAndStatus(Integer handlerId, Integer status) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("handler_id",handlerId)
                .eq("order_status",status);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Integer receiveOrder(Integer orderId, Integer handlerId) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String receiveTime = sf.format(date);

        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId) // 设置更新条件，例如订单ID等于指定值
                .set("handler_id", handlerId)
                .set("order_status", 1) // 设置要更新的字段和新值
                .set("receive_time", receiveTime);
        return orderMapper.update(null, wrapper); // 使用wrapper作为参数执行更新操作
    }

    @Override
    public List<Order> getAllReceivedOrder() {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_status",1);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Integer cancelOrder(Integer orderId, String cancelReason) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId) // 设置更新条件，例如订单ID等于指定值
                .set("cancel_reason", cancelReason) // 设置要更新的字段和新值
                .set("order_status", 4);
        return orderMapper.update(null, wrapper); // 使用wrapper作为参数执行更新操作
    }

    @Override
    public List<Order> orderListByHandlerId(Integer handlerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("handler_id",handlerId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Integer resultAdd(String handleResultText) {
        HandleResult handleResult = new HandleResult();
        handleResult.setHandleResultText(handleResultText);
        handleResultMapper.insert(handleResult);
        return handleResult.getHandleResultId();
    }

    @Override
    public Integer imgAdd(String img, Integer resultId) {
        HandleImage handleImage = new HandleImage();
        handleImage.setImagePath(img);
        handleImage.setHandleResultId(resultId);
        return handleImageMapper.insert(handleImage);
    }

    @Override
    public Integer setOrderResult(Integer orderId, Integer resultId) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String handle_time = sf.format(date);

        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId) // 设置更新条件，例如订单ID等于指定值
                .set("handle_result_id", resultId) // 设置要更新的字段和新值
                .set("order_status", 2)//上传结果后状态更新为待确认
                .set("handle_time",handle_time);
        return orderMapper.update(null, wrapper); // 使用wrapper作为参数执行更新操作
    }


}
