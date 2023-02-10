package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.service.NodeProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.gmail.klewzow.StatusMessageQueue.ANSWER_MESSAGE;


@Service
public class NodeProducerServiceIml implements NodeProducerService {
    private final RabbitTemplate rabbitTemplate;

    public NodeProducerServiceIml(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }
}
