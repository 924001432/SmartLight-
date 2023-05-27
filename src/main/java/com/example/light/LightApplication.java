package com.example.light;

import com.example.light.mqtt.MyMqttClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LightApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(LightApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {



//		if(true){
//
//			mqttClient.subscribeTopic("streetlight/server/coord01",2);
//
//		}
	}

}
