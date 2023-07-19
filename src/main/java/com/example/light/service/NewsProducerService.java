package com.example.light.service;


import javax.jms.Destination;

public interface NewsProducerService {


    void publish(String msg);

    public void testPublish(byte[] payload);

    void publishBytes(byte[] payload);

    //发布字符数组
    void publishChars(char[] payload);

    //添加主题，发布消息
    void newPublishChars(Destination destination, char[] payload);

}
