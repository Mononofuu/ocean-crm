package com.becomejavasenior;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private String name;
    private User user;
    private String text;
    private Date period;

    public Comment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}
