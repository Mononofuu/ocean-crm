package com.becomejavasenior.validation;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class ContactFormHandler implements Serializable{
    private static final Logger LOGGER = LogManager.getLogger(ContactFormHandler.class);
    private static final long serialVersionUID = 8010769367123200304L;
    private Contact contact;
    private Task task;
    private Deal deal;
    private String tags;
    private String contactComment;
    private int userContactId;
    private String period;
    private String dueDate;
    private String dueTime;
    private int userTaskId;
    private int contactCompanyId;

    public ContactFormHandler() {
        contact = new Contact();
        task = new Task();
        deal = new Deal();
    }

    public int getContactCompanyId() {
        return contactCompanyId;
    }

    public ContactFormHandler setContactCompanyId(int contactCompanyId) {
        this.contactCompanyId = contactCompanyId;
        return this;
    }

    public int getUserTaskId() {
        return userTaskId;
    }

    public ContactFormHandler setUserTaskId(int userTaskId) {
        this.userTaskId = userTaskId;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public ContactFormHandler setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getDueTime() {
        return dueTime;
    }

    public ContactFormHandler setDueTime(String dueTime) {
        this.dueTime = dueTime;
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public ContactFormHandler setPeriod(String period) {
        this.period = period;
        return this;
    }

    public int getUserContactId() {
        return userContactId;
    }

    public ContactFormHandler setUserContactId(int userContactId) {
        this.userContactId = userContactId;
        return this;
    }

    public String getContactComment() {
        return contactComment;
    }

    public ContactFormHandler setContactComment(String contactComment) {
        this.contactComment = contactComment;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public ContactFormHandler setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Task getTask() {
        return task;
    }

    public ContactFormHandler setTask(Task task) {
        this.task = task;
        return this;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
