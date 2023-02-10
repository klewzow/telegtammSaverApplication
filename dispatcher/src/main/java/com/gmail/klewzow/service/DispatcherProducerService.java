package com.gmail.klewzow.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface DispatcherProducerService {
    void produce(String queueName,Update update);
}
