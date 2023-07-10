package com.example.light.service;


public interface NewsProducerService {


    void publish(String msg);

    public void testPublish(byte[] payload);

    void publishBytes(byte[] payload);

    //发布字符数组
    void publishChars(char[] payload);

}
