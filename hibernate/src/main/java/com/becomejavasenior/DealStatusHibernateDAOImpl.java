package com.becomejavasenior;

import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.DealStatusDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */
@Repository
public class DealStatusHibernateDAOImpl extends AbstractHibernateDAO<DealStatus> implements DealStatusDAO{

    @Override
    public Class getObjectСlass() {
        return DealStatus.class;
    }

    @Override
    public int checkIfExists(DealStatus status) throws DataBaseException {
        return 0;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        DealStatus dealStatus = new DealStatus();
        dealStatus.setId(id);
        delete(dealStatus);
    }
}
