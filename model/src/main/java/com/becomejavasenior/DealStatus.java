package com.becomejavasenior;


import java.io.Serializable;

public class DealStatus implements Serializable {

    private String name;

    public DealStatus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
