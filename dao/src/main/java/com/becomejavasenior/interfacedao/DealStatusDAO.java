package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealStatus;
import com.becomejavasenior.GenericDao;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealStatusDAO extends GenericDao<DealStatus>{
    int checkIfExists(DealStatus status) throws DataBaseException;
}
