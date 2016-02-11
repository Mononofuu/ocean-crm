package com.becomejavasenior;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//@Entity
//@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 2976681857111953842L;

    private int id;
    private String name;
    private String login;
    private String password;

//    @Transient
    private byte[] photo;
    private String email;
    private String phoneWork;
    private String phoneHome;
//    @Transient
    private Set<Grants> grantsSet;
//    @Transient
    private Language language;
//    @Transient
    private List<Event> events;

//    @Transient
//    @Column(name = "comment")
    private List<Comment> comments;
//    @Transient
    private List<File> files;
//    @Transient
    private List<Task> tasks;

    public User() {
    }

//    @Id
//    @GeneratedValue
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public Set<Grants> getGrantsSet() {
        return grantsSet;
    }

    public void setGrantsSet(Set<Grants> GrantsSet) {
        this.grantsSet = GrantsSet;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (!Arrays.equals(photo, user.photo)) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phoneWork != null ? !phoneWork.equals(user.phoneWork) : user.phoneWork != null) return false;
        if (phoneHome != null ? !phoneHome.equals(user.phoneHome) : user.phoneHome != null) return false;
        if (grantsSet != null ? !grantsSet.equals(user.grantsSet) : user.grantsSet != null) return false;
        if (language != user.language) return false;
        if (events != null ? !events.equals(user.events) : user.events != null) return false;
        if (comments != null ? !comments.equals(user.comments) : user.comments != null) return false;
        if (files != null ? !files.equals(user.files) : user.files != null) return false;
        return !(tasks != null ? !tasks.equals(user.tasks) : user.tasks != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (photo != null ? Arrays.hashCode(photo) : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneWork != null ? phoneWork.hashCode() : 0);
        result = 31 * result + (phoneHome != null ? phoneHome.hashCode() : 0);
        result = 31 * result + (grantsSet != null ? grantsSet.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
