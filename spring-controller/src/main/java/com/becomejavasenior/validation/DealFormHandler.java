package com.becomejavasenior.validation;

import com.becomejavasenior.Company;
import com.becomejavasenior.Contact;
import com.becomejavasenior.Deal;
import com.becomejavasenior.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealFormHandler {
    private static final Logger LOGGER = LogManager.getLogger(DealFormHandler.class);
    private static final long serialVersionUID = 8010769367123200304L;

    private String tags;
    private String dealComment;
    private int userContactId;
    private int contactCompanyId;
    private int dealCompanyId;
    private int dealContactId;
    private int taskUserId;
    private String dueDate;
    private String dueTime;
    private Deal deal;
    private Contact contact;
    private Company company;
    private Task task;
    private String taskPeriod;
    public DealFormHandler() {
        deal = new Deal();
        contact = new Contact();
        company = new Company();
        task = new Task();
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getTaskPeriod() {
        return taskPeriod;
    }

    public void setTaskPeriod(String taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(int taskUserId) {
        this.taskUserId = taskUserId;
    }

    public int getDealContactId() {
        return dealContactId;
    }

    public void setDealContactId(int dealContactId) {
        this.dealContactId = dealContactId;
    }

    public int getDealCompanyId() {
        return dealCompanyId;
    }

    public void setDealCompanyId(int dealCompanyId) {
        this.dealCompanyId = dealCompanyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getContactCompanyId() {
        return contactCompanyId;
    }

    public void setContactCompanyId(int contactCompanyId) {
        this.contactCompanyId = contactCompanyId;
    }

    public String getDealComment() {
        return dealComment;
    }

    public void setDealComment(String dealComment) {
        this.dealComment = dealComment;
    }

    public int getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(int userContactId) {
        this.userContactId = userContactId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}