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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        return !(user != null ? !user.equals(subject.user) : subject.user != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Subject subject = (Subject) o;
//
//        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
//        if (user != null ? !user.equals(subject.user) : subject.user != null) return false;
//        return !(tags != null ? !tags.equals(subject.tags) : subject.tags != null);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = name != null ? name.hashCode() : 0;
//        result = 31 * result + (user != null ? user.hashCode() : 0);
//        result = 31 * result + (tags != null ? tags.hashCode() : 0);
//        return result;
//    }
}
