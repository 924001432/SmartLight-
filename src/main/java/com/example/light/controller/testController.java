package com.example.light.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.light.entity.Alarm;
import com.example.light.entity.Device;
import com.example.light.entity.User;
import com.example.light.entity.testEntity;
import com.example.light.mapper.UserMapper;
import com.example.light.mapper.testMapper;
import com.example.light.mqtt.MyMqttClient;
import com.example.light.service.AlarmService;
import com.example.light.service.NewsProducerService;
import com.example.light.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.light.common.ResultMapUtil;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class testController {

    @Autowired
    private testMapper testmapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsProducerService newsProducerService;

    @Autowired
    private AlarmService alarmService;


    private boolean connect_tag = false;

    MyMqttClient mqttClient;

    public void mqttconnect(){
        String serviceUrl = "tcp://127.0.0.1:1883";
        String userName = "java";
        String password = "java";
        String clientId = "javaSubscribeId";
        String msg = "";

        mqttClient = new MyMqttClient(serviceUrl, userName, password, clientId);
        mqttClient.init();
    }


    @RequestMapping("/testString")
    @ResponseBody
    public String testString(){
        return "hello";
    }

    @RequestMapping("/testButton")
    @ResponseBody
    public void testButton(){
        System.out.println("testButton succ!!");
    }

    @RequestMapping("/testLogin")
    public String testLogin(){
        return "/login2";
    }

    @RequestMapping("/test")
    public Object test(){
        return "/test";
    }

    @RequestMapping("/test1")
    public Object test1(){
        return "/test1";
    }

    @RequestMapping("/testValue")
    @ResponseBody
    public Object testValue(){

        List<Alarm> alarmList = alarmService.queryAlarmList();

        return ResultMapUtil.getHashMapList(alarmList);

    }


    @RequestMapping("/testLoginIn")
    @ResponseBody
    public Object testLoginIn(String username,String password){



        User QueryUser = userService.queryUserByNameAndPassword(username,password);

        if(QueryUser != null){
            return ResultMapUtil.getHashMapLogin("验证成功","1");
        }else {
            return ResultMapUtil.getHashMapLogin("验证失败","2");
        }

    }

    @RequestMapping("/testIndex")
    public Object testIndex(){
        return "/index";
    }

    @RequestMapping("/testBaiduMap")
    public Object testBaiduMap(){
        return "/baiduMap";
    }

    @RequestMapping("/setLight")
    public Object setLight(){
        return "/setLight";
    }

    @RequestMapping("/lightNum")
    public Object lightNum(){
        return "/lightNum";
    }

    @RequestMapping("/lightError")
    public Object lightError(){
        return "/lightError";
    }

    @RequestMapping("/lightOnOff")
    public Object lightOnOff(){
        return "/lightOnOff";
    }

    @RequestMapping("/localLight")
    public Object localLight(){
        return "/localLight";
    }

    @RequestMapping("/groupLight")
    public Object groupLight(){
        return "/groupLight";
    }

    @RequestMapping("/testpost")
    @ResponseBody
    public Object testpost(){

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        // 执行HTTP请求，将返回的结构使用String类格式化
        ResponseEntity<String> response = client.exchange("http://localhost:8080/postcmd", method, requestEntity, String.class);

        return response.getBody();
    }

    /**
        按键定义开关灯指令，前后端交互，发布消息，消息下发到zigbee，解析消息，开关灯，定义开关灯协议
     **/
//    @RequestMapping("/testLightAll")
//    @ResponseBody
//    public void testLightAll(){
//        String topic0 = "streetlight/zigbee/coord01";
//        String topic1 = "light/device/coord01";
//        String msg = "0444010DFF000102#";
//        //04开头为Java发布的消息，03开头为mqttfx端发布的消息
//        if(!connect_tag) {
//            String serviceUrl = "tcp://127.0.0.1:1883";
//            String userName = "java";
//            String password = "java";
//            String clientId = "javaSubscribeId";
//
//            mqttClient = new MyMqttClient(serviceUrl, userName, password, clientId);
//            mqttClient.init();
//            mqttClient.publishMessage(topic1, msg, 2);
//            System.out.println("主题: " + topic1 + "  内容: " + msg);
//            connect_tag = true;
//        }else {
//            mqttClient.publishMessage(topic1, msg, 2);
//            System.out.println("主题: " + topic1 + "  内容: " + msg);
//        }
//    }


    @RequestMapping("/testProduce")
    @ResponseBody
    public void produce() {

        //String msg = "org.apache.catalina.core.StandardContextValve";

        byte[] payload={0x58,0x44,0x00,0x00,0x00,0x00,0x01,0x03,0x00,0x00,0x00,0x23};

        //newsProducerService.publish(msg);

        newsProducerService.publishBytes(payload);

        //System.out.println("msg:" + msg);


    }


}
