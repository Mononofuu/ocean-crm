package com.becomejavasenior;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "contact")
@PrimaryKeyJoinColumn(name="id")
public class Contact extends Subject {

    private static final long serialVersionUID = -5553010181244222836L;
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    private String post;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "phone_type_id")
    private PhoneType phoneType;
    private String phone;
    private String email;
    private String skype;
    @Transient
    private List<Comment> comments;
    @Transient
    private List<File> files;
    @Transient
    private List<Task> tasks;
    @ManyToMany
    @JoinTable(name="deal_contact")
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
        if (getName() != null ? !getName().equals(contact.getName()) : contact.getName() != null) return false;
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


}
