package com.gmail.klewzow;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DeterminantMessage {
    public SendMessage generatedReturnMessage(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(update.getMessage().getChatId());
        return sendMessage;
    }
}
