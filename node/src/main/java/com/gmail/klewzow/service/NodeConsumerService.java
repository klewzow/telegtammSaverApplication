package com.gmail.klewzow.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface NodeConsumerService {
    void commonTextMessageUpdate(Update update);
    void commonPhotoMessageUpdate(Update update);
    void commonDocumentMessageUpdate(Update update);
}
