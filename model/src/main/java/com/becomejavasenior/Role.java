package com.becomejavasenior;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 8681711728307488038L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Transient
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            return this.getName().equals(((Role) obj).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
