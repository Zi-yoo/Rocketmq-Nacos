package com.ziyoo.producer;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@NacosPropertySource(dataId = "rocketmqproducer",autoRefreshed = true)
public class ProducerApplication {

    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class, args);
    }

}
