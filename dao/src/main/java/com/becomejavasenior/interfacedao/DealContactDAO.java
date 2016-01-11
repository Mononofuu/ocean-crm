package com.becomejavasenior.interfacedao;

import com.becomejavasenior.Contact;
import com.becomejavasenior.DataBaseException;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealContactDAO {
    List<Contact> getAllContactsByDealId(int id) throws DataBaseException;
}
