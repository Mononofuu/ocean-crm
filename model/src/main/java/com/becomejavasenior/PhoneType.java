package com.becomejavasenior;


import java.io.Serializable;

public enum PhoneType implements Serializable {
    WORK_PHONE_NUMBER, WORK_DIRECT_PHONE_NUMBER, MOBILE_PHONE_NUMBER, FAX_NUMBER, HOME_PHONE_NUMBER, OTHER_PHONE_NUMBER;

    @Override
    public String toString() {
        switch (this){
            case WORK_PHONE_NUMBER:System.out.println("<spring:message code=\"label.phonework\"/>");
                return "label.phonework";
            case WORK_DIRECT_PHONE_NUMBER:
                return "label.phoneworkdirect";
            case MOBILE_PHONE_NUMBER:
                return "label.phonemobile";
            case FAX_NUMBER:
                return "label.phonefax";
            case HOME_PHONE_NUMBER:
                return "label.phonehome";
            default:
                return "label.phoneother";
        }
    }
}
