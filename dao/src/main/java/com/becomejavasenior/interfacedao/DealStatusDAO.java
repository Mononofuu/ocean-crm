package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealStatus;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealStatusDAO {
    int checkIfExists(DealStatus status) throws DataBaseException;
}
