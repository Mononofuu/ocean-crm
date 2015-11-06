package com.becomejavasenior;


import java.io.Serializable;
import java.net.URL;
import java.util.Date;

public class File implements Serializable {

    private Subject subject;
    private String name;
    private User user;
    private byte[] contentFromDB;
    private java.io.File contentObject;
    private URL contentURL;
    private Date period;
    private int size;

    public File() {
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

    public byte[] getContentFromDB() {
        return contentFromDB;
    }

    public void setContentFromDB(byte[] contentFromDB) {
        this.contentFromDB = contentFromDB;
    }

    public java.io.File getContentObject() {
        return contentObject;
    }

    public void setContentObject(java.io.File contentObject) {
        this.contentObject = contentObject;
    }

    public URL getContentURL() {
        return contentURL;
    }

    public void setContentURL(URL contentURL) {
        this.contentURL = contentURL;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
