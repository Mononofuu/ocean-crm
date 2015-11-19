package com.becomejavasenior;

import java.net.URL;
import java.util.List;

public class Company extends Subject {

    private static final long serialVersionUID = 6412485489253693564L;

    private String phoneNumber;
    private String email;
    private URL web;
    private String adress;

    private List<Comment> comments;
    private List<File> files;
    private List<Task> tasks;

    private List<Contact> contacts;
    private List<Deal> deals;

    public Company() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getWeb() {
        return web;
    }

    public void setWeb(URL web) {
        this.web = web;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        if (phoneNumber != null ? !phoneNumber.equals(company.phoneNumber) : company.phoneNumber != null) return false;
        if (email != null ? !email.equals(company.email) : company.email != null) return false;
        if (web != null ? !web.equals(company.web) : company.web != null) return false;
        if (adress != null ? !adress.equals(company.adress) : company.adress != null) return false;
        if (comments != null ? !comments.equals(company.comments) : company.comments != null) return false;
        if (files != null ? !files.equals(company.files) : company.files != null) return false;
        if (tasks != null ? !tasks.equals(company.tasks) : company.tasks != null) return false;
        if (contacts != null ? !contacts.equals(company.contacts) : company.contacts != null) return false;
        return !(deals != null ? !deals.equals(company.deals) : company.deals != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (deals != null ? deals.hashCode() : 0);
        return result;
    }
}
