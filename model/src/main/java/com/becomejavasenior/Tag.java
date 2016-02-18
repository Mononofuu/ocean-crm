package com.becomejavasenior;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 2760079646947188984L;

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Transient
    private SubjectType subjectType;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

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

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }
}
