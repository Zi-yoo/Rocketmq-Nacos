package com.ziyoo.consumer.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ConsumerProperties {


    @NacosValue("${rmq.consumer.name-server}")
    private String namesrvAddr;

    @NacosValue("${rmq.consumer.group}")
    private String consumerGroup;

    @NacosValue("${rmq.consumer.topic}")
    private String topic;


    public void runMQPushConsumer() throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic,"*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                System.out.println(new String(msg.getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Listening ...");
//        return consumer;
    }



}
