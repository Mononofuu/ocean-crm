package com.becomejavasenior;

import java.io.Serializable;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public enum FilterTaskType implements Serializable {
    IGNORE, TODAY, TOMORROW, THIS_WEEK, THIS_MONTHS, THIS_QUARTER, WO_TASKS, EXPIRED;

    @Override
    public String toString() {
        switch (this) {
            case TODAY:
                return "На сегодня";
            case TOMORROW:
                return "На завтра";
            case THIS_WEEK:
                return "На этой неделе";
            case THIS_MONTHS:
                return "В этом месяце";
            case THIS_QUARTER:
                return "В этом квартале";
            case WO_TASKS:
                return "Нет задач";
            case EXPIRED:
                return "Просрочены";
            default:
                return "Не учитывать задачи";
        }
    }
}
