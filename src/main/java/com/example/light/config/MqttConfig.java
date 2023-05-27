package com.example.light.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

/**
    MQTT主题定义
 **/
@Configuration
public class MqttConfig {

    //MQTT主题定义
    @Bean
    Topic topic() {

        return new ActiveMQTopic("inTopic");

    }


}
