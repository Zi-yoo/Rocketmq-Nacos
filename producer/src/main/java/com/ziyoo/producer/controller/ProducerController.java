package com.ziyoo.producer.controller;

import com.ziyoo.producer.config.ProducerProperties;
import com.ziyoo.producer.service.RocketMQService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {


    @Autowired
    private RocketMQService service;

    @Autowired
    private ProducerProperties properties;


    @GetMapping("/send")
    public void sendMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        service.sendMsg();
    }

    @GetMapping("/info")
    public void getInfo(){
        System.out.println(properties.getNamesrcAddr() + "\n" + properties.getTopic());
    }

}
