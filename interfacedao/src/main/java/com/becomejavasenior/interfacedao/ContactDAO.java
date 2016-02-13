package com.becomejavasenior.interfacedao;

import com.becomejavasenior.*;

import java.util.List;

public interface ContactDAO extends GeneralContactDAO<Contact>{
    List<Tag> readAllContactsTags() throws DataBaseException;
}
