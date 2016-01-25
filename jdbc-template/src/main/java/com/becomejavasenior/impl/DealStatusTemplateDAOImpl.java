package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.DealStatus;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class DealStatusTemplateDAOImpl extends JdbcDaoSupport implements DealStatusDAO{
    public int checkIfExists(DealStatus status) throws DataBaseException {
        return 0;
    }

    public DealStatus create(DealStatus object) throws DataBaseException {
        return null;
    }

    public DealStatus read(int key) throws DataBaseException {
        return null;
    }

    public DealStatus readLite(int key) throws DataBaseException {
        return null;
    }

    public void update(DealStatus object) throws DataBaseException {

    }

    public void delete(int id) throws DataBaseException {

    }

    public List<DealStatus> readAll() throws DataBaseException {
        return null;
    }

    public List<DealStatus> readAllLite() throws DataBaseException {
        return null;
    }
}
