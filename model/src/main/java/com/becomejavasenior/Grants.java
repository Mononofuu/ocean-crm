package com.becomejavasenior;

import java.io.Serializable;

public class Grants implements Serializable {

    private static final long serialVersionUID = -4422398493853647082L;

    private int level;
    private User user;
    private Role role;

    public Grants() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
