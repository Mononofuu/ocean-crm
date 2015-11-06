package com.becomejavasenior;


import java.io.Serializable;

public class Currency implements Serializable {

    private int id;
    private String name;

    public Currency() {
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
}
