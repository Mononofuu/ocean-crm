package com.becomejavasenior;


import java.io.Serializable;

public class Currency implements Serializable {

    private static final long serialVersionUID = 8065618450514247270L;

    private int id;
    private String name;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
