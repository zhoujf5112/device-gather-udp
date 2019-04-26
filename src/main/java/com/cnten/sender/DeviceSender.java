package com.cnten.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeviceSender {
    @Value("${queue.name}")
    private String queueName;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void send(String data) {
        this.rabbitTemplate.convertAndSend(queueName, data);
    }
}
