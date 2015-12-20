package com.becomejavasenior;

import java.io.Serializable;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public enum FilterPeriod implements Serializable {
    ALL_TIME, TODAY, FOR_THREE_DAYS, WEEK, MONTH, QUARTER, PERIOD;

    @Override
    public String toString() {
        switch (this) {
            case ALL_TIME:
                return "За все время";
            case TODAY:
                return "За сегодня";
            case FOR_THREE_DAYS:
                return "За 3 дня";
            case WEEK:
                return "За неделю";
            case MONTH:
                return "За месяц";
            case QUARTER:
                return "За квартал";
            default:
                return "За период(выбрать даты ниже)";
        }
    }
}
