package com.example.light.service.Impl;

import com.example.light.service.NewsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.Topic;

@Service
public class NewsProducerServiceImpl implements NewsProducerService {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    @Override
    public void publish(String msg) {
        jmsTemplate.convertAndSend(topic, msg);
    }

    @Override
    public void testPublish(byte[] payload) {
        jmsTemplate.convertAndSend("DHT11", payload);
    }

    @Override
    public void publishBytes(byte[] payload) {

        jmsTemplate.convertAndSend(topic, payload);

    }

    @Override
    public void publishChars(char[] payload) {

        jmsTemplate.convertAndSend(topic, payload);

//        System.out.println(payload);

    }

    @Override
    public void newPublishChars(Destination destination, char[] payload){

        jmsTemplate.convertAndSend(destination, payload);
        System.out.println(payload);

    }
}
