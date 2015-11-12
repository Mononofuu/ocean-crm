package com.becomejavasenior;


import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    private static final long serialVersionUID = 388198834697983732L;

    private int id;
    private User user;
    private OperationType operationType;
    private String eventContent;
    private Date eventDate;

    public Event() {
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
