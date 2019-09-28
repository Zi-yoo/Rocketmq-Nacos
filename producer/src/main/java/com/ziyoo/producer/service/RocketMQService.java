package com.ziyoo.producer.service;

import com.ziyoo.producer.config.ProducerProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketMQService {

    @Autowired
    private ProducerProperties properties;

    public void sendMsg() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = properties.getProducer();
        Message msg = new Message(properties.getTopic(),"test".getBytes());
        producer.send(msg);
        System.out.println("Send Success!");
        producer.shutdown();
    }

}
