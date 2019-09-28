package com.ziyoo.consumer.consumer;

import com.ziyoo.consumer.config.ConsumerProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PullConsumer {
    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();

    @Autowired
    private ConsumerProperties config;


    private String namesrvAddr = config.getNamesrvAddr();

    private String topic = config.getTopic();

    private String consumerGroup = config.getConsumerGroup();

    private DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(consumerGroup);

    public void runConsumer() throws MQClientException {

        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumerTimeoutMillisWhenSuspend(300000);


        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic);

        for (MessageQueue mq : mqs) {
            System.out.printf("Consume from the queue: " + mq + "%n");
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
//                            System.out.println(new String(pullResult.getMsgFoundList().get(0).getBody()));
                            List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                            for (MessageExt m : messageExtList) {
                                System.out.println(new String(m.getBody()));
                            }
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            System.out.println("no new msg");
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }





    }


    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null){
            return offset;
        }
        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }

}
