package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.service.RabbitMQProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    private final RabbitMQProducer rabbitMQProducer;

    public RabbitMQController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @PostMapping("/send-rabbitmq")
    public String sendToRabbitMQ(@RequestParam String message) {
        rabbitMQProducer.sendMessage("test-queue", message);
        return "Message sent to RabbitMQ: " + message;
    }
}

