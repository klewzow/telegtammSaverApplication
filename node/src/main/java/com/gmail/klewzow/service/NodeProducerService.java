package com.gmail.klewzow.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface NodeProducerService {

    void produce(SendMessage sendMessage);
}
