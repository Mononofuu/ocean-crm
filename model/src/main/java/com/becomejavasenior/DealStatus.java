package com.becomejavasenior;


import java.io.Serializable;

public class DealStatus implements Serializable {

    private static final long serialVersionUID = -7849041242260513488L;

    private int id;
    private String name;

    public DealStatus() {
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
