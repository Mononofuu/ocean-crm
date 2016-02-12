package com.becomejavasenior;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subject_tag")
public class SubjectTag implements Serializable{
    @Id
    @Column(name = "tag_id")
    private Tag tag;
    @Id
    @Column(name = "subject_id")
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
