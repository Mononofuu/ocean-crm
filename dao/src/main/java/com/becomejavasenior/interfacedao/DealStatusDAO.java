package com.becomejavasenior.interfacedao;

<<<<<<< HEAD
public interface DealStatusDAO {
=======
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealStatus;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealStatusDAO {
    int checkIfExists(DealStatus status) throws DataBaseException;
>>>>>>> master
}
