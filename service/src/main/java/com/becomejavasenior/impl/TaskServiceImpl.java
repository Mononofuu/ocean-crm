package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;
import com.becomejavasenior.interfaceservice.TaskService;

import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
public class TaskServiceImpl implements TaskService {

    private final TaskDAOImpl myDao = new TaskDAOImpl();

    @Override
    public void saveTask(Task task) throws DataBaseException {
        if (task.getId() == 0) {
            myDao.create(task);
        } else {
            myDao.update(task);
        }
    }

    @Override
    public void deleteTask(int id) throws DataBaseException {
        myDao.delete(id);
    }

    @Override
    public Task findTaskById(int id) throws DataBaseException {
        return myDao.read(id);
    }

    @Override
    public List<Task> getAllTask() throws DataBaseException {
        return myDao.readAll();
    }

    @Override
    public List<Task> getTasksBySubject(Subject subject) throws DataBaseException {
        return myDao.getAllTasksBySubjectId(subject.getId());
    }

}
