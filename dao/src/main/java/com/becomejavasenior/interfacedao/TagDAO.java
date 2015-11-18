package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Tag;

public interface TagDAO {
    int checkIfExists(Tag tag) throws DataBaseException;
}
