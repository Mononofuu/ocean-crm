package com.becomejavasenior;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealContact {
    private Contact contact;
    private Subject subject;

    public DealContact() {
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
