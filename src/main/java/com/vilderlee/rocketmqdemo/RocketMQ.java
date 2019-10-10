package com.vilderlee.rocketmqdemo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类说明:
 *
 * <pre>
 * Modify Information:
 * Author        Date          Description
 * ============ ============= ============================
 * VilderLee    2019/10/10      Create this file
 * </pre>
 */
@Component
public class RocketMQ {


    @Value("${rocketmq.namesrv.address}")
    private String rocketMQ_NameSrv_Address;

    private Logger LOGGER = LoggerFactory.getLogger(RocketMQ.class);

    public void test() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("PID_TEST");

        //设置Nameserver地址
        defaultMQProducer.setNamesrvAddr("39.104.159.18:9876");


        //启动生产者，建立到broker的链接
        defaultMQProducer.start();


        for (int i = 0; i < 10; i++) {
            try {
                Message sendMessage = new Message("TopicTest", "TagA",
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                defaultMQProducer.send(sendMessage, new SendCallback() {
                    @Override public void onSuccess(SendResult sendResult) {
                        LOGGER.info("消息id={}, 发送结果={}", sendResult.getMsgId(), sendResult.getSendStatus());
                    }

                    @Override public void onException(Throwable e) {
                        LOGGER.info("消息主题={}, 消息体={}", sendMessage.getTopic(), new String(sendMessage.getBody()));
                        e.printStackTrace();
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new RocketMQ().test();
    }

}
