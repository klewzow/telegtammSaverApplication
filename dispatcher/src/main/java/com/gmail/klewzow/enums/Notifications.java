package com.gmail.klewzow.enums;

public enum Notifications {

    MESSAGE_NULL("Message is null."),
    MESSAGE_INCORRECT("Incorrect type data."),
    MESSAGE_OK("Received. File processing..."),
    NULL_MESSAGE("Your message is null ");
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    Notifications(String s) {
        this.msg = s;
    }
}
