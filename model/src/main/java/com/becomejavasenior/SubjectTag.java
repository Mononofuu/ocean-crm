package com.becomejavasenior;

import java.io.Serializable;

public class SubjectTag implements Serializable{
    private Tag tag;
    private Subject subject;

    public SubjectTag() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
