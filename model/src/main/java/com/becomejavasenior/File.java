package com.becomejavasenior;


import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;

@Entity
@Table(name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = -2959672203378534217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name = "content")
    private byte[] fileFromDB;
    @Column(name = "link")
    private URL fileLink;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date dateCreated;
    private int size;

    public File() {
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

    public byte[] getFileFromDB() {
        return fileFromDB;
    }

    public void setFileFromDB(byte[] fileFromDB) {
        this.fileFromDB = fileFromDB;
    }

    public URL getFileLink() {
        return fileLink;
    }

    public void setFileLink(URL fileLink) {
        this.fileLink = fileLink;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", subject=" + subject.getName() +
                ", user=" + user.getName() +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", content=" + fileFromDB +
                '}';
    }
}
