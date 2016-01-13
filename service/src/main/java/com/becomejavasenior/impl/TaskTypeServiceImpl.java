package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.TaskTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class TaskTypeServiceImpl implements TaskTypeService {
    private static final Logger LOGGER = LogManager.getLogger(TaskTypeServiceImpl.class);
    private DaoFactory dao;
    private GenericDao<TaskType> taskTypeDAO;

    public TaskTypeServiceImpl() throws DataBaseException{
        dao = new PostgreSqlDaoFactory();
        taskTypeDAO = dao.getDao(TaskType.class);
    }

    @Override
    public void saveTaskType(TaskType taskType) throws DataBaseException {
        taskTypeDAO.create(taskType);
    }

    @Override
    public void deleteTaskType(int id) throws DataBaseException {
        taskTypeDAO.delete(id);
    }

    @Override
    public TaskType findTaskTypeById(int id) throws DataBaseException {
        return taskTypeDAO.read(id);
    }

    @Override
    public List<TaskType> getAllTaskTypes() throws DataBaseException {
        return taskTypeDAO.readAll();
    }
}
