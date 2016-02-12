package com.becomejavasenior;

import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.EventDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */

public class EventHibernateDAOImpl extends AbstractHibernateDAO<Event> implements EventDAO{

    @Override
    public Class getObjectСlass() {
        return Event.class;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Event event = new Event();
        event.setId(id);
        delete(event);
    }
}
