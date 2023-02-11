package com.gmail.klewzow.enums;

public enum UserStatus {
    BASE_STATE,
    WAIT_FOR_EMAIL_STATE;

    public boolean state(int val){
        return this.ordinal() == val;
    }
}
