package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.service.DispatcherAnswerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.gmail.klewzow.StatusMessageQueue.ANSWER_MESSAGE;
@Service
public class DispatcherAnswerServiceImpl implements DispatcherAnswerService {


    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void answer(SendMessage sendMessage) {

    }
}
