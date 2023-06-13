package com.example.light.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class testLitsen {

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

