package com.becomejavasenior;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Deal implements Serializable {

    private String dealName;
    private DealStatus status;
    private Set<String> tags;
    private User user;
    private int budget;
    private Date dateWhenDealClose;

    private List<Contact> contacts;
    private Company dealCompany;

    private List<String> comments;
    private List<File> files;

    public Deal() {
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }
}
