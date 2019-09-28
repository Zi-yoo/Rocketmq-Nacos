package com.ziyoo.consumer.controller;

import com.ziyoo.consumer.config.ConsumerProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerProperties properties;


    @GetMapping("/run")
    public void listen() throws MQClientException {
        properties.runMQPushConsumer();
    }

    @GetMapping("/info")
    public void getInfo(){
        System.out.println(properties.getNamesrvAddr() + "\n" + properties.getTopic());
    }


}
