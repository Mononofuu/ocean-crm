package com.becomejavasenior;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "task")
@XmlRootElement
public class Task implements Serializable {

    private static final long serialVersionUID = 5383049602322141163L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private User user;
    @Column(name = "created_date")
    private Date dateCreated;
    @Column(name = "due_date")
    private Date dueTime;
    @Column(name = "task_type_id")
    @Enumerated(EnumType.ORDINAL)
    private TaskType type;
    private String comment;
    @Column(name = "is_closed")
    private byte isClosed;
    @Column(name = "is_deleted")
    private byte isDeleted;

    public Task() {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(byte isClosed) {
        this.isClosed = isClosed;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (subject != null ? !subject.equals(task.subject) : task.subject != null) return false;
        if (user != null ? !user.equals(task.user) : task.user != null) return false;
        if (dateCreated != null ? !dateCreated.equals(task.dateCreated) : task.dateCreated != null) return false;
        if (dueTime != null ? !dueTime.equals(task.dueTime) : task.dueTime != null) return false;
        if (type != task.type) return false;
        return !(comment != null ? !comment.equals(task.comment) : task.comment != null);

    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dueTime != null ? dueTime.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", subject=" + subject.getName() +
                ", user=" + user.getName() +
                ", dateCreated=" + dateCreated +
                ", dueTime=" + dueTime +
                ", comment='" + comment + '\'' +
                ", isClosed=" + isClosed +
                ", isDeleted=" + isDeleted +
                ", taskType=" + type +
                '}';
    }
}
