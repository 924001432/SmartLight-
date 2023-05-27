package com.example.light.mqtt;


import com.example.light.common.SpringUtils;
import com.example.light.entity.Device;
import com.example.light.mapper.DeviceMapper;
import com.example.light.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mqtt连接回调函数 ，用来接收已经订阅的消息
 * @author Mr.Liu
 * @date 2021/4/1 9:36
 */
@Slf4j
@Component
public class MqttReceiveCallback implements MqttCallback {

    //@Autowired
    //private DeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    public static String _topic;
    public static String _qos;
    public static String _msg;

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("连接断开，正在重连 ");
        String serviceUrl = "tcp://127.0.0.1:1883";
        String userName = "java";
        String password = "java";
        String clientId = "javaPublishId";
        String msg = "";

        MyMqttClient mqttClient = new MyMqttClient(serviceUrl, userName, password, clientId);
        mqttClient.init();

//        if(true){
//            mqttClient.subscribeTopic("streetlight/server/coord01",2);
//        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String qos = message.getQos()+"";
        String msg = new String(message.getPayload());

        //System.out.println("byte: "+toHexString(message.getPayload()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date date = new Date();  //获取当前时间
        simpleDateFormat.applyPattern("HH:mm:ss");  //其中的a时am和pm的标记，如果不加a则不会显示是上午还是下午

        System.out.println("时间: "+simpleDateFormat.format(date)+"  主题: " + topic + "  内容: " + msg);
        System.out.println("isRetained: " + message.isRetained());

        _topic = topic;
        _qos = qos;
        _msg = msg;
        //解析协议内容，数据库操作
        //analyser(msg);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public void analyser(String cmdMessages){

        /**
         * 先解析协议头
         * * 网关认证应答消息
         * * 网关测试消息
         * * 温度上传消息
         * * 定位消息
         * * 路由广播消息
         * * 路灯状态消息
         * * 定位状态消息
         * * 路灯故障消息
         *  协议头存到一个变量里
         *  这里也要添加一个帧头帧尾的判定
         */
        /**
         * 0244010DFF——前三位是帧头，数据类型，协议类型
         * D4ED
         * 0300
         * 505F0902004B1200
         * 02
         */
        //获取上下文，构造服务类
        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        DeviceService deviceService = applicationContext.getBean(DeviceService.class);
        //在考虑要不要转换成int型
        if(cmdMessages.substring(0,2).equals("02") & cmdMessages.substring(2,4).equals("44") & cmdMessages.substring(4,6).equals("01")){
            //可以改进
            Device device = new Device();

            //MAC地址
            device.setDeviceMac(cmdMessages.substring(18,34));

            //短地址
            String temp = cmdMessages.substring(12,14)+cmdMessages.substring(10,12);
            device.setDeviceShort(temp);

            //序列号
            temp = cmdMessages.substring(16,18)+cmdMessages.substring(14,16);
            device.setDeviceSerial(temp);
            //在线状态更改，收到消息


            //开灯状态,int型
            device.setDeviceLight(Integer.parseInt(cmdMessages.substring(34)));

            deviceService.insertDevice(device);

        }else {//其他协议类型
            System.out.println("其他协议类型");
        }


    }

    // 别的Controller层会调用这个方法获取接收到的数据
    public String receive(){
        return _topic+_qos+_msg;
    }

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
}

