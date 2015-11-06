package com.becomejavasenior;


import java.io.Serializable;

public class Event implements Serializable {

    private User user;
    private OperationType operationType;
    private String eventContent;

    public Event() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
