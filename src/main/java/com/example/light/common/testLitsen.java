package com.example.light.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *  测试mqtt监听类
 */
@Slf4j
@Component
public class testLitsen {

    @JmsListener(destination = "inTopic")
    public void receive(char[] payload) {
        log.info("{} 接收到消息: {}", "1号", payload);
        System.out.println((byte)payload[2]);
    }

//    @JmsListener(destination = "newsTopic")
//    public void receive2(byte[] message) {
//
//        System.out.println(new String(message));
//
//
//    }

    @JmsListener(destination = "newsTopic")
    public void receive1(String msg) {
        log.info("{} 接收到消息: {}", "1号", msg);
    }

//    @JmsListener(destination = "newsTopic")
//    public void receive2(byte[] message) {
//
//        System.out.println(new String(message));
//
//
//    }

    private String arrayToStr(int[] arr) {
        String res = "";
        for (int i = 0; i < arr.length; i++) {
            res += Character.toString((char)arr[i]);
        }
        return res;
    }

    private int[] ascToArray(String str) {
        String[] arr = str.split(",");
        int[] resArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            resArr[i] = Integer.parseInt(arr[i]);
        }
        return resArr;
    }



}

