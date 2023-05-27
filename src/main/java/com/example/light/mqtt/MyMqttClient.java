package com.example.light.mqtt;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mqtt客户端类
 * @author Mr.Liu
 * @date 2021/3/31 17:58
 */
public class MyMqttClient {
    /** mqtt客户端对象 */
    private MqttConnectOptions mqttConnectOptions;
    /** MQTT连接对象 对连接进行设置 */
    private MqttClient mqttClient;
    private MemoryPersistence memoryPersistence;
    /** 定义需要的属性变量 */
    private String serviceUrl;
    private String userName;
    private String password;
    private String clientId;
    private boolean connect_tag;


    @Autowired
    private MqttReceiveCallback mqttReceiveCallback;

    /**
     * 定义构造函数
     * @param serviceUrl
     * @param userName
     * @param password
     */
    public MyMqttClient(String serviceUrl, String userName, String password, String clientId) {
        this.serviceUrl = serviceUrl;
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
    }

    /**
     * 类初始化，建立mqttClient连接
     */
    public void init(){
        //初始化连接对象
        mqttConnectOptions = new MqttConnectOptions();
        //初始化mqttClient
        if (mqttConnectOptions != null){
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            mqttConnectOptions.setCleanSession(true);
            //设置超时时间 单位为秒
            mqttConnectOptions.setConnectionTimeout(30);
            mqttConnectOptions.setUserName(userName);
            mqttConnectOptions.setPassword(password.toCharArray());
            //设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            mqttConnectOptions.setKeepAliveInterval(45);
            memoryPersistence = new MemoryPersistence();
            if (mqttConnectOptions != null && clientId != null){
                try {
                    mqttClient = new MqttClient(serviceUrl, clientId, memoryPersistence);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("mqttConnectOptions == null || clientId == null");
            }
        }else {
            System.out.println("mqttConnectOptions == null");
        }

        System.out.println("MqttClient是否连接？"+mqttClient.isConnected());
        if (mqttClient != null){
            //创建回调函数对象,用来接收已经订阅的消息
            mqttClient.setCallback(new MqttReceiveCallback());

            System.out.println("创建连接......");
            try {
                mqttClient.connect(mqttConnectOptions);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("mqttClient对象为空，连接失败...");
        }
        System.out.println("mqttClient是否连接？"+mqttClient.isConnected());

    }

    /**
     * 订阅主题
     * @param topic 主题
     * @param qos 消息质量
     */
    public void subscribeTopic(String topic, int qos){
        if (mqttClient != null && mqttClient.isConnected() && topic != null){
            try {
                mqttClient.subscribe(topic, qos);
                System.out.println("订阅成功");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("订阅主题失败！mqttClient == null || mqttClient.isConnected() == false");
        }
    }

    /**
     * 取消订阅主题
     * @param topic 主题名称
     */
    public void cleanTopic(String topic){
        if (mqttClient != null && mqttClient.isConnected()){
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("取消订阅失败！");
        }
    }

    /**
     * 发布消息
     * @param publishTopic 发布消息的主题名称
     * @param message 消息内容
     * @param qos 消息质量
     */
    public void publishMessage(String publishTopic, byte[] message, int qos){
        // 判断是否连接
        if (mqttClient != null && mqttClient.isConnected()){
            System.out.println("发布消息.......");
            System.out.println("发布消息人的clientId："+mqttClient.getClientId());
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message);
            MqttTopic mqttTopic = mqttClient.getTopic(publishTopic);
            if (mqttTopic != null){
                try {
                    MqttDeliveryToken publish = mqttTopic.publish(mqttMessage);
                    if (!publish.isComplete()){
                        System.out.println("消息发布成功！");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("mqttTopic == null");
            }
        }else {
            System.out.println("mqttClient == null || mqttClient.isConnected() == false");
        }
    }
}

