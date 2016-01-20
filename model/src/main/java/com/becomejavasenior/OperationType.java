package com.becomejavasenior;


import java.io.Serializable;

public enum OperationType implements Serializable {
    NEW_DEAL, NEW_CONTACT, DEAL_STATUS_CHANGED, NEW_COMMENT, NEW_EMAIL, NEW_COMPANY;

    @Override
    public String toString() {
        switch (this){
            case NEW_DEAL:
                return "Новая сделка";
            case NEW_CONTACT:
                return "Новый контакт";
            case DEAL_STATUS_CHANGED:
                return "Изменен статус сделки";
            case NEW_COMMENT:
                return "Новый комментарий";
            case NEW_EMAIL:
                return "Новое письмо";
            case NEW_COMPANY:
                return "Новая компания";
            default:
                return "Прочая операция";
        }
    }

}
