package com.becomejavasenior;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 5409233407264547377L;

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @Transient
    private User user;
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private Date dateCreated;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
