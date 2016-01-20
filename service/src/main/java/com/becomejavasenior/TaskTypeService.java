package com.becomejavasenior;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.TaskType;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public interface TaskTypeService {
    TaskType saveTaskType(TaskType taskType) throws DataBaseException;
    void deleteTaskType(int id) throws DataBaseException;
    TaskType findTaskTypeById(int id) throws DataBaseException;
    List<TaskType> getAllTaskTypes() throws DataBaseException;
}
