package com.becomejavasenior;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Deal extends Subject implements Serializable {

    private int id;
    private String dealName;
    private DealStatus status;
    private Set<Tag> tags;
    private User user;
    private int budget;
    private Date dateWhenDealClose;

    private List<Contact> contacts;
    private Company dealCompany;

    private List<String> comments;
    private List<File> files;

    public Deal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getDateWhenDealClose() {
        return dateWhenDealClose;
    }

    public void setDateWhenDealClose(Date dateWhenDealClose) {
        this.dateWhenDealClose = dateWhenDealClose;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Company getDealCompany() {
        return dealCompany;
    }

    public void setDealCompany(Company dealCompany) {
        this.dealCompany = dealCompany;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
