package com.becomejavasenior;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class Filter implements Serializable {
    private static final long serialVersionUID = -5045712962652077587L;
    private int id;
    private String name;
    private User user;
    private FilterPeriod type;
    private Timestamp date_from;
    private Timestamp date_to;
    private DealStatus status;
    private Contact manager;
    private FilterTaskType taskType;
    private String tags;

    public Filter() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilterPeriod getType() {
        return type;
    }

    public void setType(FilterPeriod type) {
        this.type = type;
    }

    public Timestamp getDate_from() {
        return date_from;
    }

    public void setDate_from(Timestamp date_from) {
        this.date_from = date_from;
    }

    public Timestamp getDate_to() {
        return date_to;
    }

    public void setDate_to(Timestamp date_to) {
        this.date_to = date_to;
    }

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
    }

    public Contact getManager() {
        return manager;
    }

    public void setManager(Contact manager) {
        this.manager = manager;
    }

    public FilterTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(FilterTaskType taskType) {
        this.taskType = taskType;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter filter = (Filter) o;

        if (type != filter.type) return false;
        if (date_from != null ? !date_from.equals(filter.date_from) : filter.date_from != null) return false;
        if (date_to != null ? !date_to.equals(filter.date_to) : filter.date_to != null) return false;
        if (!status.equals(filter.status)) return false;
        if (manager != null ? !manager.equals(filter.manager) : filter.manager != null) return false;
        if (taskType != filter.taskType) return false;
        return tags != null ? tags.equals(filter.tags) : filter.tags == null;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (date_from != null ? date_from.hashCode() : 0);
        result = 31 * result + (date_to != null ? date_to.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        result = 31 * result + taskType.hashCode();
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
