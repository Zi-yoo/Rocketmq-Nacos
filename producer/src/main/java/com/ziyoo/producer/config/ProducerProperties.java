package com.ziyoo.producer.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ProducerProperties {


    @NacosValue("${rmq.producer.name-server}")
    private String namesrcAddr;

    @NacosValue("${rmq.producer.group}")
    private String groupName;

    @NacosValue("${rmq.producer.topic}")
    private String topic;


    public DefaultMQProducer getProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrcAddr);
        producer.setInstanceName(groupName);
        producer.start();
        return producer;
    }


}
