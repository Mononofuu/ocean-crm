package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "company")
@PrimaryKeyJoinColumn(name="id")
public class Company extends Subject {

    private static final long serialVersionUID = 6412485489253693564L;
    private final static Logger LOGGER = LogManager.getLogger(Contact.class);
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private URL web;
    private String address;
    @Column(name = "date_created", insertable=false)
    private Date createdDate;
    @Column(name = "date_updated")
    private Date updatedDate;
    @Transient
    private List<Comment> comments;
    @Transient
    private List<File> files;
    @Transient
    private List<Task> tasks;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Contact> contacts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dealCompany")
    private List<Deal> deals;

    public Company() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Company setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public Company setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        try {
            if (web != null ? !web.toURI().equals(company.web.toURI()) : company.web != null) return false;
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }
        if (address != null ? !address.equals(company.address) : company.address != null) return false;
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
        try {
            result = 31 * result + (web != null ? web.toURI().hashCode() : 0);
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (deals != null ? deals.hashCode() : 0);
        return result;
    }
}
