package com.gmail.klewzow;

import com.gmail.klewzow.enums.Notifications;
import com.gmail.klewzow.service.DispatcherProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gmail.klewzow.StatusMessageQueue.*;
import static com.gmail.klewzow.enums.Notifications.MESSAGE_INCORRECT;
import static com.gmail.klewzow.enums.Notifications.MESSAGE_OK;

@Log4j
@Component
public class DispatcherMessageHandler {

    private TelegramBot telegramBot;
    private final DeterminantMessage determinantMessage;
    private final DispatcherProducerService dispatcherProducerService;

    public DispatcherMessageHandler(DeterminantMessage determinantMessage, DispatcherProducerService dispatcherProducerService) {
        this.determinantMessage = determinantMessage;
        this.dispatcherProducerService = dispatcherProducerService;
    }

    public void initTelegramBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }
    public void studyOutUpdate(Update update) {
        if (update == null) {
            log.error(Notifications.MESSAGE_NULL.getMsg());
            return;
        } else {
            defineMessageType(update);
        }
    }

    private void defineMessageType(Update update) {

        Message message = update.getMessage();
        if (message.hasText()) {
            setTextMessageType(update);
        } else if (message.hasPhoto()) {
            setPhotoMessageType(update);
        } else if (message.hasDocument()) {
            setDocumentMessageType(update);
        } else {
            setUnsupportedTypeMessage(update);
        }
    }
    private void setDocumentMessageType(Update update) {
        dispatcherProducerService.produce(DOCUMENT_MESSAGE, update);
    }

    private void setPhotoMessageType(Update update) {
        dispatcherProducerService.produce(PHOTO_MESSAGE, update);
    }

    private void setTextMessageType(Update update) {
        dispatcherProducerService.produce(TEXT_MESSAGE, update);
    }

    private void setUnsupportedTypeMessage(Update update) {
        feedback(update, MESSAGE_INCORRECT);
    }

    public void sendMessageToBot(SendMessage sendMessage) {
        this.telegramBot.sendMessage(sendMessage);
    }

    private void feedback(Update update, Notifications notifications) {
        SendMessage sendMessage = determinantMessage.generatedReturnMessage(update, notifications.getMsg());
        sendMessageToBot(sendMessage);
    }
}
