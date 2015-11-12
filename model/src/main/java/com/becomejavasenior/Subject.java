package com.becomejavasenior;

import java.io.Serializable;
import java.util.Set;

//Class that represent owner for comments, files and tasks.

public abstract class Subject implements Serializable {

    private static final long serialVersionUID = 8010763598123200304L;

    private int id;
    private String name;
    private User user;
    private Set<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
