package com.gmail.klewzow.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface DispatcherAnswerService {


    void answer(SendMessage sendMessage);
}
