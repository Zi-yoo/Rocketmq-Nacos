package com.ziyoo.consumer.listener;

import com.ziyoo.consumer.config.ConsumerProperties;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {


    @Autowired
    private ConsumerProperties properties;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        if (CollectionUtils.isEmpty(msgs)){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);
        String msg = new String(messageExt.getBody());
        if(messageExt.getTopic().equals(properties.getTopic())){
            int reconsumeTimes = messageExt.getReconsumeTimes();
            if(reconsumeTimes == 3){
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            //业务逻辑代码
            System.out.println(msg);

        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

}
