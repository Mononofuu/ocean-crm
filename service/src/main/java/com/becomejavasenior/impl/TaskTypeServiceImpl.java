package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.TaskType;
import com.becomejavasenior.TaskTypeService;
import com.becomejavasenior.interfacedao.TaskTypeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class TaskTypeServiceImpl implements TaskTypeService {
    private static final Logger LOGGER = LogManager.getLogger(TaskTypeServiceImpl.class);
    @Autowired
    private TaskTypeDAO taskTypeDAO;

    @Override
    public TaskType saveTaskType(TaskType taskType) throws DataBaseException {
        return taskTypeDAO.create(taskType);
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
