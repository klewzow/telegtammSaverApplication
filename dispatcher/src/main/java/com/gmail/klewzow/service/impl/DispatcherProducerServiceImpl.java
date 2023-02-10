package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.service.DispatcherProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
@Log4j
@Service
@EnableAutoConfiguration
public class DispatcherProducerServiceImpl implements DispatcherProducerService {

    private RabbitTemplate rabbitTemplate;

    public DispatcherProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String queueName, Update update) {
        log.debug("PRODUCER: Send message \"" + queueName +"\" to broker.");
        rabbitTemplate.convertAndSend(queueName, update);
    }
}
