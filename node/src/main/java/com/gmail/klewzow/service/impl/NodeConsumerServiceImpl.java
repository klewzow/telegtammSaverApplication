package com.gmail.klewzow.service.impl;

import com.gmail.klewzow.dao.ApplicationUserRepository;
import com.gmail.klewzow.dao.MessageRowDataDAO;
import com.gmail.klewzow.entity.ApplicationUser;
import com.gmail.klewzow.entity.MessageRowData;
import com.gmail.klewzow.service.NodeConsumerService;
import com.gmail.klewzow.service.NodeProducerService;
import com.gmail.klewzow.service.enums.ServiceCommand;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.gmail.klewzow.StatusMessageQueue.*;
import static com.gmail.klewzow.enums.UserStatus.BASE_STATE;
import static com.gmail.klewzow.enums.UserStatus.WAIT_FOR_EMAIL_STATE;
import static com.gmail.klewzow.service.enums.ServiceCommand.*;

@Log4j
@Service
public class NodeConsumerServiceImpl implements NodeConsumerService {
    private final NodeProducerService nodeProducerService;
    private final ApplicationUserRepository applicationUserRepository;
    private final MessageRowDataDAO messageRowDataDAO;

    public NodeConsumerServiceImpl(NodeProducerService nodeProducerService, ApplicationUserRepository applicationUserRepository, MessageRowDataDAO messageRowDataDAO) {
        this.nodeProducerService = nodeProducerService;
        this.applicationUserRepository = applicationUserRepository;
        this.messageRowDataDAO = messageRowDataDAO;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE)
    public void commonTextMessageUpdate(Update update) {

        /*
         * Сохранить пользователя если такого не существует
         */
        ApplicationUser applicationUser = findOrSaveApplicationUser(update);
        int userStatus = applicationUser.showStatus();
        String telegramText = update.getMessage().getText();
        String output = "NODE: Сообщение доставлленно.";


        if (ServiceCommand.serviceCommandIsPresent(telegramText)) {
            if (CANSEL.equals(telegramText)) {
                output = canselProcess(applicationUser);
            } else if (BASE_STATE.state(userStatus)) {
                output = processServiceCommand(applicationUser, telegramText);
            } else if (WAIT_FOR_EMAIL_STATE.state(userStatus)) {
                //TODO Обработка Email
                output = "Подтверждение E-mail временно недоступно.";
            } else {
                log.error("Неизвестная комманда. -> " + userStatus);
            }
            //TODO сохранение сообщения в бд
            saveAllUpdateMessage(update);
        }


        /*
         * Сообщение  ответ(через брокер)
         */
        sendReturnMessage(update, output);


    }

    private String processServiceCommand(ApplicationUser applicationUser, String telegramText) {
        if (START.equals(telegramText)) {
            return "Добро пожаловать на сервис хранения файлов. \n" + help();
        }
        if (REGISTRATION.equals(telegramText)) {
            return " Регистрация временно недоступна.";
        }
        if (SHOW_MY_DATA.equals(telegramText)) {
            return " Данные временно недоступны.";
        }

        return "NODE: SERVICE COMMAND";
    }

    private String help() {
        return "\nСписок доступных комманд: \n" +
                "/help \n" +
                "/registration \n" +
                "/show_data \n" +
                "/cansel \n";

    }

    private String canselProcess(ApplicationUser applicationUser) {
        return "Операция отменена.";
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

    private ApplicationUser findOrSaveApplicationUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        ApplicationUser persistentApplicationUser = applicationUserRepository.findApplicationUserByTelegramUserId(telegramUser.getId());
        return Objects.requireNonNullElseGet(persistentApplicationUser, () -> addNewApplicationUserToDB(telegramUser));
    }

    private ApplicationUser addNewApplicationUserToDB(User telegramUser) {
        ApplicationUser transientApplicationUser = ApplicationUser.builder()
                .telegramUserId(telegramUser.getId())
                .username(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                //TODO изменить значение по умолчанию после добавления регистрации
                .isActive(true)
                .status(BASE_STATE)
                .build();
        return applicationUserRepository.save(transientApplicationUser);
    }


    private void saveAllUpdateMessage(Update update) {
        MessageRowData messageRowData = MessageRowData.builder()
                .chatID(update.getMessage().getChatId())
                .update(update)
                .build();
        messageRowDataDAO.save(messageRowData);
    }

    private void sendReturnMessage(Update update, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(text);
        nodeProducerService.produce(sendMessage);
    }


}
