package com.becomejavasenior;


import java.io.Serializable;
import java.util.TimeZone;

public class Settings implements Serializable {

    private Currency currency;
    private TimeZone timeZone;

    public Settings() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
