package com.gmail.klewzow.service.enums;

import java.util.Arrays;

public enum ServiceCommand {
    START("/start"),
    CANSEL("/cansel"),
    HELP("/help"),
    REGISTRATION("/registration"),
    SHOW_MY_DATA("/show_data");

    String command;

    ServiceCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }


    public boolean equals(String text){
        return this.getCommand().equals(text);
    }

    public static boolean serviceCommandIsPresent(String telegramCommand){

        return Arrays.stream(ServiceCommand.values()).filter(el -> el.equals(telegramCommand)).findAny().isPresent();

    }

}
