package com.becomejavasenior.validation;

import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;
import com.becomejavasenior.TaskType;
import com.becomejavasenior.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Created by kramar on 29.02.16.
 */
public class TaskFormHandler implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ContactFormHandler.class);
    private static final long serialVersionUID = 3738864885157037021L;

    private Task task;
    private String period;
    private String dueDate;
    private String dueTime;

    public TaskFormHandler() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

}
