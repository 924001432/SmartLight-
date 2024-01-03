package com.example.light.mqtt;

import com.example.light.common.MessageUtil;
import com.example.light.common.SpringUtils;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.service.AlarmService;
import com.example.light.service.DeviceService;
import com.example.light.service.NewsProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Component
public class Monitor {


    //获取上下文，构造服务类
    ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    DeviceService deviceService = applicationContext.getBean(DeviceService.class);

    NewsProducerService newsProducerService = applicationContext.getBean(NewsProducerService.class);

    Device device = new Device();

    @JmsListener(destination = "DHT11")
    public void subscribe(byte[] payload)  {
    /*
        测试接收到的故障消息，格式正确
        1.修改字节格式上传
        2.修改解析方法
        3.
     */

        analyser(new String(payload));


    }

    public void analyser(String Messages)  {

        //转换为Int型后的数据，便于查找
        Integer[] integers = MessageUtil.handleStr2ArrInt(MessageUtil.handleStr2ArrStr(Messages));

        //帧头帧尾检测，消息类型识别
        if(integers[0]==0x58 && integers[1]==0x44 && integers[integers.length-1]==0x23){

            switch (integers[20]) {
                //路灯信息协议
                case 0x02 : {     //协议类型
                    //可以改进
                    System.out.println("路灯信息");

                    infoProcess(Messages);

                    break;
                }
                //故障信息协议
                case 0x03 :

                    System.out.println("故障信息");
//                     调用alarm方法，针对不同类型的故障信息，做出相应处理
                    alarmProcess(Messages , integers);

                    break;
                //注册认证协议
                case 0x0F :

                    System.out.println("注册认证消息");
                    //认证处理方法

                    break;
                //应答消息协议
                case 0xAA :

                    System.out.println("应答消息");

                    break;
                //心跳包协议
                case 0x55 : {

                    System.out.println("心跳包");

                    heartProcess(Messages);

                    break;
                }
                default:
                    break;
            }


        }else {//未检测到帧头帧尾
            System.out.println("未检测到帧头帧尾");
        }


    }

    //路灯信息处理方法
    public void infoProcess(String Messages){

        System.out.println("get info message");

//        //PanID
//        System.out.println(Messages.substring(6, 8) + Messages.substring(4, 6));
//
//        //区域编号
//        System.out.println(Messages.substring(4, 26));
//
//        //MAC地址
//        device.setDeviceMac(Messages.substring(26, 42));
//
//        //短地址
//        String temp = Messages.substring(44, 46) + Messages.substring(42, 44);
//        device.setDeviceShort(temp);
//
//        //序列号
//        temp = Messages.substring(48, 50) + Messages.substring(46, 48);
//        device.setDeviceSerial(temp);
//
//        //开灯状态,int型
//        device.setDeviceLight(Integer.parseInt(Messages.substring(36, 38)));
//
//        Date date = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String update_time = sf.format(date);
//
//        device.setDeviceUpdatetime(update_time);

//        deviceService.insertDevice(device);

        //Java端对Zigbee端的应答消息
//        byte[] payload = {0x58, 0x44, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xAA, 0x01, 0x02, 0x23};
//
//        newsProducerService.publishBytes(payload);
    }

    //报警处理方法
    public void alarmProcess(String alarmMessage, Integer[] integer){

        //获取上下文，构造服务类
        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        AlarmService alarmService = applicationContext.getBean(AlarmService.class);

        //故障实体类
        Alarm alarm = new Alarm();

        //获取故障设备编号
        String temp = alarmMessage.substring(30, 32) + alarmMessage.substring(28, 30);
        alarm.setDeviceSerial(temp);

        //获取故障上传时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String alarm_time = sf.format(date);

        //设置故障时间
        alarm.setAlarmTime(alarm_time);
        //设置故障状态
        alarm.setAlarmStatus(0);//数据库修改为默认值1

        //设置故障类型，插入数据库
        for (int i = 18; i <= integer[17]+18; i++) {

            if(integer[i] == 1){//故障
                switch (i-18){
                    case 0://湿度
                        System.out.println("湿度");
                        alarm.setAlarmType(0);
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
                    default:
                        break;
                }
                alarmService.insertAlarm(alarm);
            }
        }
    }

    public void heartProcess(String Messages){
        //PanID
        System.out.println("get heart message");
//        System.out.println("PanID:" + Messages.substring(6, 8) + Messages.substring(4, 6));
//
//        //序列号
//        String temp = Messages.substring(30, 32) + Messages.substring(28, 30);
//        device.setDeviceSerial(temp);
//
//
//        Date date = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String heart_time = sf.format(date);
//
//        device.setDeviceHearttime(heart_time);
//
//        //获取心跳包的时间，写入数据库
//        deviceService.insertDevice(device);
    }

}

