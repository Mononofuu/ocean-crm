package com.becomejavasenior;

import java.util.Date;
import java.util.List;

public class Deal extends Subject {

    private static final long serialVersionUID = -5045712962652077587L;

    private User mainContact;
    private DealStatus status;
    private int budget;
    private Currency currency;
    private Date dateWhenDealClose;

    private List<Contact> contacts;
    private Company dealCompany;

    private List<Comment> comments;
    private List<File> files;
    private List<Task> tasks;
    private Date dateCreated;

    public Deal() {
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getMainContact() {
        return mainContact;
    }

    public void setMainContact(User mainContact) {
        this.mainContact = mainContact;
    }

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Deal deal = (Deal) o;

        return budget == deal.budget;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + budget;
        return result;
    }
}
