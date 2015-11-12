package com.becomejavasenior;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Deal extends Subject {

    private static final long serialVersionUID = -5045712962652077587L;

    private Contact mainContact;
    private DealStatus status;
    private int budget;
    private Currency currency;
    private Date dateWhenDealClose;

    private List<Contact> contacts;
    private Company dealCompany;

    private List<String> comments;
    private List<File> files;
    private List<Task> tasks;

    public Deal() {
    }

    public Contact getMainContact() {
        return mainContact;
    }

    public void setMainContact(Contact mainContact) {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
