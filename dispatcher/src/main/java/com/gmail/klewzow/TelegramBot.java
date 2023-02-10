package com.gmail.klewzow;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String key;
    private final DispatcherMessageHandler dispatcherMessageHandler;

    public TelegramBot(DispatcherMessageHandler dispatcherMessageHandler) {
        this.dispatcherMessageHandler = dispatcherMessageHandler;
    }

    @PostConstruct
    private void init() {
        dispatcherMessageHandler.initTelegramBot(this);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return key;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("TELEGRAM: Input Message" + update.getMessage().getText());
        dispatcherMessageHandler.studyOutUpdate(update);
    }

    public void sendMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
                log.debug("TELEGRAM: Message send." + message.getText());
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}
