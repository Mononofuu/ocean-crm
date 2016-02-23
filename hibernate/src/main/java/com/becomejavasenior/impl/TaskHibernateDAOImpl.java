package com.becomejavasenior.impl;

import com.becomejavasenior.AbstractHibernateDAO;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;
import com.becomejavasenior.interfacedao.DealDAO;
import com.becomejavasenior.interfacedao.TaskDAO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by kramar on 10.2.16.
 */

public class TaskHibernateDAOImpl extends AbstractHibernateDAO<Task> implements TaskDAO{

    @Override
    public Class getObjectСlass() {
        return Task.class;
    }


    @Override
    public List<Task> getAllTasksBySubjectId(int id) throws DataBaseException {
        return null;
    }

    @Override
    public List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException {
        return null;
    }

    @Override
    public List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException {
        return null;
    }

    @Override
    public Subject getSubject(int id) throws DataBaseException {
        return null;
    }

    @Override
    public void delete(int id) throws DataBaseException {
        Task task = new Task();
        task.setId(id);
        delete(task);
    }
}
