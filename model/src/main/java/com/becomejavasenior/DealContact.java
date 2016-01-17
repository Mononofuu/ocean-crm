package com.becomejavasenior;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public class DealContact {
    private Contact contact;
    private Deal deal;

    public DealContact() {
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Subject getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
