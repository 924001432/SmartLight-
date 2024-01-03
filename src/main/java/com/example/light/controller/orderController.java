package com.example.light.controller;


import com.example.light.common.ResultMapUtil;
import com.example.light.dto.OrderDto;
import com.example.light.dto.handleResultDto;
import com.example.light.entity.HandleImage;
import com.example.light.entity.HandleResult;
import com.example.light.entity.Order;
import com.example.light.entity.User;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.OrderService;
import com.example.light.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class orderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    private static final int TIME_MAX =  7 * 24 * 60 * 60;

    @Scheduled(cron = "0 0 0 * * ?") // 每天0点触发任务
    public void checkOrderTimeout() {

        // 获取当前时间
        Date currentDate = new Date();

        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<Order> orderList = orderService.getAllReceivedOrder();
            for(Order o : orderList){
                String dateString = o.getReceiveTime();
                Date parsedDate = dateFormat.parse(dateString);
                // 计算时间差（毫秒）
                long timeDifference = currentDate.getTime() - parsedDate.getTime();
                // 将时间差转换为天数
                long daysDifference = timeDifference / 1000;
                // 判断是否超过7天
                if (daysDifference > TIME_MAX) {
                        System.out.println("订单："+ o +"因超时未处理被取消");
                        orderService.cancelOrder(o.getOrderId(),"接单时间超过七天未处理");
                        Order order = new Order();
                        order.setOrderArea(o.getOrderArea());
                        order.setReportTime(o.getReportTime());//?为啥这个没有设置
                        order.setAlarmId(o.getAlarmId());
                        order.setDeviceSerial(o.getDeviceSerial());
                        order.setReporterId(o.getReporterId());
                        orderService.orderAdd(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据修理员id查询订单
     * @param
     * @return
     */
    @RequestMapping("/orderListByHandlerId/{userId}")
    @ResponseBody
    public Object orderListByHandlerId(@PathVariable(name = "userId",required = true)Integer userId){

        List<Order> orderList = orderService.orderListByHandlerId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order o : orderList){
            OrderDto orderDto = new OrderDto(o);
            orderDto.setAlarmType(alarmService.queryAlarmById(o.getAlarmId()).getAlarmType());
            orderDto.setAlarmTime(alarmService.queryAlarmById(o.getAlarmId()).getAlarmTime());
            orderDto.setDeviceLon(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLon());
            orderDto.setDeviceLat(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLat());
            orderDto.setReporterPhone(userService.queryUserById(o.getReporterId()).getUserPhone());
            orderDto.setHandlerPhone(userService.queryUserById(userId).getUserPhone());


            if(o.getHandleResultId() != null) {
                orderDto.setResultText(orderService.handleResultById(o.getHandleResultId()).getHandleResultText());
                List<HandleImage> handleImages = orderService.imagePathById(o.getHandleResultId());
                List<String> imagePathList = new ArrayList<>();
                for (HandleImage image : handleImages) {
                    imagePathList.add(image.getImagePath());
                }
                orderDto.setResultImages(imagePathList);
            }
            orderDtoList.add(orderDto);
        }
        return ResultMapUtil.getHashMapList(orderDtoList);
    }

    /**
     * 根据报修人id查询订单
     * @param
     * @return
     */
    @RequestMapping("/orderListByReporterId/{userId}")
    @ResponseBody
    public Object orderListByReporterId(@PathVariable(name = "userId",required = true)Integer userId){

        List<Order> orderList = orderService.orderListByReporterId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order o : orderList){
            OrderDto orderDto = new OrderDto(o);
            orderDto.setAlarmType(alarmService.queryAlarmById(o.getAlarmId()).getAlarmType());
            orderDto.setAlarmTime(alarmService.queryAlarmById(o.getAlarmId()).getAlarmTime());
            orderDto.setDeviceLon(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLon());
            orderDto.setDeviceLat(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLat());
            orderDto.setReporterPhone(userService.queryUserById(userId).getUserPhone());
            if(o.getHandlerId() != null) {
                orderDto.setHandlerPhone(userService.queryUserById(o.getHandlerId()).getUserPhone());
            }
            if(o.getHandleResultId() != null) {
                orderDto.setResultText(orderService.handleResultById(o.getHandleResultId()).getHandleResultText());
                List<HandleImage> handleImages = orderService.imagePathById(o.getHandleResultId());
                List<String> imagePathList = new ArrayList<>();
                for (HandleImage image : handleImages) {
                    imagePathList.add(image.getImagePath());
                }
                orderDto.setResultImages(imagePathList);
            }
            orderDtoList.add(orderDto);
        }
        return ResultMapUtil.getHashMapList(orderDtoList);
    }


//    /**
//     * 根据报修人查询报修订单
//     * @param reporterId
//     * @return
//     */
//    @RequestMapping("/orderListByReporterId/{reporterId}")
//    @ResponseBody
//    public Object orderListByReporterId(@PathVariable(name = "reporterId",required = true)Integer reporterId){
//
//        List<Order> orderList = orderService.orderListByReporterId(reporterId);
//        List<OrderDto> orderDtoList = new ArrayList<>();
//        for(Order o : orderList){
//            OrderDto orderDto = new OrderDto(o);
//
//            orderDto.setAlarmType(alarmService.queryAlarmById(o.getAlarmId()).getAlarmType());
//            orderDto.setAlarmTime(alarmService.queryAlarmById(o.getAlarmId()).getAlarmTime());
//            orderDto.setDeviceLon(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLon());
//            orderDto.setDeviceLat(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLat());
//            orderDtoList.add(orderDto);
//        }
//
//        return ResultMapUtil.getHashMapList(orderDtoList);
//    }

    /**
     * 根据报修人和订单状态查询报修订单
     * @param
     * @return
     */
    @RequestMapping("/orderListByReporterIdAndStatus/{reporterId}/{status}")
    @ResponseBody
    public Object orderListByReporterIdAndStatus(@PathVariable(name = "reporterId",required = true)Integer reporterId,@PathVariable(name = "status",required = true)Integer status){

        List<Order> orderList = orderService.orderListByReporterIdAndStatus(reporterId,status);
        return ResultMapUtil.getHashMapList(orderList);
    }

    /**
     * 根据处理人和订单状态查询报修订单
     * @param
     * @return
     */
    @RequestMapping("/orderListByHandlerIdAndStatus/{handlerId}/{status}")
    @ResponseBody
    public Object orderListByHandlerIdAndStatus(@PathVariable(name = "handlerId",required = true)Integer handlerId,@PathVariable(name = "status",required = true)Integer status){

        List<Order> orderList = orderService.orderListByHandlerIdAndStatus(handlerId,status);
        return ResultMapUtil.getHashMapList(orderList);
    }


    /**
     * 根据订单地区和订单状态查询报修订单
     * @param
     * @return
     */
    @RequestMapping("/orderListByAreaAndStatus/{area}/{status}")
    @ResponseBody
    public Object orderListByAreaAndStatus(@PathVariable(name = "area",required = true)String area,@PathVariable(name = "status",required = true)Integer status){

        List<Order> orderList = orderService.orderListByAreaAndStatus(area,status);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order o : orderList){
            OrderDto orderDto = new OrderDto(o);

            orderDto.setAlarmType(alarmService.queryAlarmById(o.getAlarmId()).getAlarmType());
            orderDto.setAlarmTime(alarmService.queryAlarmById(o.getAlarmId()).getAlarmTime());
            orderDto.setDeviceLon(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLon());
            orderDto.setDeviceLat(deviceService.queryDeviceBySerial(o.getDeviceSerial()).getDeviceLat());
            orderDtoList.add(orderDto);
        }
        return ResultMapUtil.getHashMapList(orderDtoList);
    }

    /**
     * 报修
     * @param order
     * @return
     */
    @RequestMapping("/orderAdd")
    @ResponseBody
    public Object orderAdd(@RequestBody Order order) {
        System.out.println("添加报修订单:"+order);
        if(orderService.queryByAlarmId(order.getAlarmId()) == null){//该订单不存在
            try{
                alarmService.repairAlarm(order.getAlarmId());
                int i = orderService.orderAdd(order);
                return ResultMapUtil.getHashMapSave(i);
            } catch (Exception e){
                return ResultMapUtil.getHashMapException(e);
            }
        }else{
            return null;
        }
    }

    /**
     * 上传修理结果
     * @param handle_ResultDto
     * @return
     */
    @RequestMapping("/uploadResult")
    @ResponseBody
    public Object uploadResult(@RequestBody handleResultDto handle_ResultDto) {
        System.out.println("添加结果:"+handle_ResultDto);

        try{

                Integer resultId = orderService.resultAdd(handle_ResultDto.getHandleResultText());
                List<String> imageList = handle_ResultDto.getImageList();
                for(String img : imageList){
                    orderService.imgAdd(img, resultId);
                }
                int i = orderService.setOrderResult(handle_ResultDto.getOrderId(),resultId);
                return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
                return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 查询报修结果
     * @param handleResultId
     * @return
     */
    @RequestMapping("/handleResultById/{handleResultId}")
    @ResponseBody
    public handleResultDto handleResultById(@PathVariable(name = "handleResultId",required = true)Integer handleResultId){
        HandleResult handleResult = orderService.handleResultById(handleResultId);
        handleResultDto handleResultdto = new handleResultDto(handleResult);
        List<HandleImage> handleImages = orderService.imagePathById(handleResultId);
        List<String> imagePathList = new ArrayList<>();
        for(HandleImage image : handleImages){
            imagePathList.add(image.getImagePath());
        }
        handleResultdto.setImageList(imagePathList);
        System.out.println(handleResultdto);
        return handleResultdto;
    }

    /**
     * 更新订单状态
     * @param orderId
     * @param status
     * @return
     */
    @RequestMapping("/updateOrderStatusByOrderId/{orderId}/{status}")
    @ResponseBody
    public Object updateOrderStatusByOrderId(@PathVariable(name = "orderId",required = true)Integer orderId, @PathVariable(name = "status",required = true)Integer status){

        try{
            int i = orderService.updateOrderStatusByOrderId(orderId, status);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


    /**
     * 接单
     * @param orderId
     * @param handlerId
     * @return
     */
    @RequestMapping("/receiveOrder/{orderId}/{handlerId}")
    @ResponseBody
    public Object receiveOrder(@PathVariable(name = "orderId",required = true)Integer orderId, @PathVariable(name = "handlerId",required = true)Integer handlerId){

        try{
            int i = orderService.receiveOrder(orderId, handlerId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    /**
     * 取消订单
     * @param orderId
     * @param cancelReason
     * @return
     */
    @RequestMapping("/cancelOrder/{orderId}/{cancelReason}")
    @ResponseBody
    public Object cancelOrder(@PathVariable(name = "orderId",required = true)Integer orderId, @PathVariable(name = "cancelReason",required = true)String cancelReason){

        try{
            int i = orderService.cancelOrder(orderId, cancelReason);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }


}
