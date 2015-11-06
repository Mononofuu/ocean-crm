package com.becomejavasenior;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private Subject subject;
    private User user;
    private String text;
    private Date period;

    public Comment() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
