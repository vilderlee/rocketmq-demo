package com.vilderlee.rocketmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RocketmqDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RocketmqDemoApplication.class, args);
        RocketMQ bean = context.getBean(RocketMQ.class);
        try {
            bean.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
