package com.becomejavasenior;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 388198834697983732L;

    @Id
    @GeneratedValue
    private int id;
    @Transient
    private User user;
    @Transient
    private OperationType operationType;
    private String eventContent;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="event_date")
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
