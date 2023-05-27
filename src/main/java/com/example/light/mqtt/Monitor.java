package com.example.light.mqtt;

import com.example.light.common.SpringUtils;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class Monitor {


    @JmsListener(destination = "newsTopic")
    public void subscribe(byte[] message) {
/*
    测试接收到的故障消息，格式正确
 */

        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        AlarmService alarmService = applicationContext.getBean(AlarmService.class);

        Alarm alarm = new Alarm();
        alarm.setDeviceSerial("0001");
        alarm.setAlarmTime("2023.05.23");
        alarm.setAlarmStatus(1);//数据库修改为默认值1


        for (int i = 4; i <= message[3]+4; i++) {

            if(message[i] == 1){//故障
                switch (i-4){
                    case 0://湿度
                        System.out.println("湿度");

                        alarm.setAlarmType(0);
//                        alarmService.insertAlarm(alarm);
                        break;
                    case 1://温度
                        System.out.println("温度");
                        alarm.setAlarmType(1);
                        break;
                    case 2://路灯电压
                        System.out.println("路灯电压");
                        alarm.setAlarmType(2);
                        break;
                    case 3://主板电压
                        System.out.println("主板电压");
                        alarm.setAlarmType(3);
                        break;
                    case 4://GPS
                        System.out.println("GPS");
                        alarm.setAlarmType(4);
                        break;
                    case 5://路灯
                        System.out.println("路灯");
                        alarm.setAlarmType(5);
                        break;
                }
                alarmService.insertAlarm(alarm);
            }
        }

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
            //在线状态更改，收到消息，1表示在线，0表示离线
            device.setDeviceStatus(1);
            //做一个计时器功能，十秒钟之内未收到消息，重新清零，写数据库


            //开灯状态,int型
            device.setDeviceLight(Integer.parseInt(cmdMessages.substring(34)));

            deviceService.insertDevice(device);

        }else {//其他协议类型
            System.out.println("其他协议类型");
        }


    }





}
