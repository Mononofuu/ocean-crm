package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Deal;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface DealDAO {
    public List<Deal> readStatusFilter(int statusId) throws DataBaseException;
    public List<Deal> readUserFilter(int userId) throws DataBaseException;
    public List<Deal> readTagFilter(String tag) throws DataBaseException;
}
