package com.cnten.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    @Value("${queue.name}")
    private String queueName;
    @Bean
    public Queue Queue() {
        return new Queue(queueName);
    }
}