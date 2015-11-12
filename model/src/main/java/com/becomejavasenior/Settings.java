package com.becomejavasenior;


import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = -7443218529958802240L;

    private int id;
    private String setting;
    private String value;

    public Settings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
