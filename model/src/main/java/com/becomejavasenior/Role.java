package com.becomejavasenior;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {

    private static final long serialVersionUID = 8681711728307488038L;

    private int id;
    private String name;
    private String description;
    private Set<Grants> grantsSet;

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Grants> getGrantsSet() {
        return grantsSet;
    }

    public void setGrantsSet(Set<Grants> grantsSet) {
        this.grantsSet = grantsSet;
    }
}
