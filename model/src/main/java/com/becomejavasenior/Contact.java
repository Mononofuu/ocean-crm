package com.becomejavasenior;

import java.util.List;
import java.util.Set;

public class Contact extends Subject implements Tagable{

    private static final long serialVersionUID = -5553010181244222836L;

    private Company company;
    private String post;
    private PhoneType phoneType;
    private String phone;
    private String email;
    private String skype;

    private List<Comment> comments;
    private List<File> files;
    private List<Task> tasks;

    private List<Deal> deals;

    public Contact() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }
<<<<<<< a299759788ba94f5a461c385af562019ac491830
=======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (company != null ? !company.equals(contact.company) : contact.company != null) return false;
        if (post != null ? !post.equals(contact.post) : contact.post != null) return false;
        if (phoneType != contact.phoneType) return false;
        if (phone != null ? !phone.equals(contact.phone) : contact.phone != null) return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        if (skype != null ? !skype.equals(contact.skype) : contact.skype != null) return false;
        if (comments != null ? !comments.equals(contact.comments) : contact.comments != null) return false;
        if (files != null ? !files.equals(contact.files) : contact.files != null) return false;
        if (tasks != null ? !tasks.equals(contact.tasks) : contact.tasks != null) return false;
        return !(deals != null ? !deals.equals(contact.deals) : contact.deals != null);

    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (phoneType != null ? phoneType.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (deals != null ? deals.hashCode() : 0);
        return result;
    }
>>>>>>> added dao for Contact with simple test
}
