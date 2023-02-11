package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.dao.ApplicationUserRepository;
import com.gmail.klewzow.dao.MessageRowDataDAO;
import com.gmail.klewzow.entity.MessageRowData;
import com.gmail.klewzow.service.NodeConsumerService;
import com.gmail.klewzow.service.NodeProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.gmail.klewzow.StatusMessageQueue.*;

@Log4j
@Service
public class NodeConsumerServiceImpl implements NodeConsumerService {
    private final NodeProducerService nodeProducerService;
    private final ApplicationUserRepository applicationUserRepository;
    private final MessageRowDataDAO messageRowDataDAO;
    private  MessageRowData messageRowData;

    public NodeConsumerServiceImpl(NodeProducerService nodeProducerService, ApplicationUserRepository applicationUserRepository, MessageRowDataDAO messageRowDataDAO ) {
        this.nodeProducerService = nodeProducerService;
        this.applicationUserRepository = applicationUserRepository;
        this.messageRowDataDAO = messageRowDataDAO;

    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE)
    public void commonTextMessageUpdate(Update update) {
        log.debug("NODE: Text message isset ");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Text message");
        messageRowData = MessageRowData.builder()
                .chatID(update.getMessage().getChatId())
                .update(update)
                .build();

         messageRowDataDAO.save(messageRowData);
        nodeProducerService.produce(sendMessage);
    }
    @Override
    @RabbitListener(queues = PHOTO_MESSAGE)
    public void commonPhotoMessageUpdate(Update update) {
        log.debug("NODE: Photo message isset ");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Photo message");
        nodeProducerService.produce(sendMessage);
    }

    @Override
    @RabbitListener(queues = DOCUMENT_MESSAGE)
    public void commonDocumentMessageUpdate(Update update) {
        log.debug("NODE: Document message isset ");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Document message");
        nodeProducerService.produce(sendMessage);
    }
}
