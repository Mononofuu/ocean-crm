package com.becomejavasenior;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 388198834697983732L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    @Column(name = "operation_type")
    @Enumerated(EnumType.ORDINAL)
    private OperationType operationType;
    @Column(name = "content")
    private String eventContent;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date", insertable = false)
    private Date eventDate;

    public Event() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", operationType=" + operationType +
                ", eventContent='" + eventContent + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
