package com.becomejavasenior;


import java.io.Serializable;

public enum PhoneType implements Serializable {
    WORK_PHONE_NUMBER, WORK_DIRECT_PHONE_NUMBER, MOBILE_PHONE_NUMBER, FAX_NUMBER, HOME_PHONE_NUMBER, OTHER_PHONE_NUMBER;

    @Override
    public String toString() {
        switch (this){
            case WORK_PHONE_NUMBER:
                return "Рабочий";
            case WORK_DIRECT_PHONE_NUMBER:
                return "Рабочий прямой";
            case MOBILE_PHONE_NUMBER:
                return "Мобильный";
            case FAX_NUMBER:
                return "Факс";
            case HOME_PHONE_NUMBER:
                return "Домашний";
            case OTHER_PHONE_NUMBER:
                return "Другой";
            default:
                return "Другой";
        }
    }

}
