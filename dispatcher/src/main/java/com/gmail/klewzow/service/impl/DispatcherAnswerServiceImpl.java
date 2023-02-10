package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.DispatcherMessageHandler;
import com.gmail.klewzow.service.DispatcherAnswerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.gmail.klewzow.StatusMessageQueue.ANSWER_MESSAGE;

@Log4j
@Service
public class DispatcherAnswerServiceImpl implements DispatcherAnswerService {
    private final DispatcherMessageHandler dispatcherMessageHandler;

    public DispatcherAnswerServiceImpl(DispatcherMessageHandler dispatcherMessageHandler) {
        this.dispatcherMessageHandler = dispatcherMessageHandler;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void answer(SendMessage sendMessage) {
        log.debug("DISPATCHER: Answer message enter.");
        dispatcherMessageHandler.sendMessageToBot(sendMessage);
    }
}
