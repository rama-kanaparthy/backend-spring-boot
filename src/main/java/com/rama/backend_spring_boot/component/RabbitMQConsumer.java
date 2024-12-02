package com.rama.backend_spring_boot.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "test-queue")
    public void receiveMessage(String message) {
        log.info("Message received from RabbitMQ: " + message);
    }
}

