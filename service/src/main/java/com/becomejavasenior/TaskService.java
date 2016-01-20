package com.becomejavasenior;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;
import com.becomejavasenior.TaskType;

import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */
public interface TaskService {

    Task saveTask(Task task) throws DataBaseException;

    void deleteTask(int id) throws DataBaseException;

    Task findTaskById(int id) throws DataBaseException;

    List<Task> getAllTask() throws DataBaseException;

    List<Task> getTasksBySubject(Subject subject) throws DataBaseException;

    List<Task> getTasksByParameters(Map<String, String[]> parameters) throws DataBaseException;

    List<TaskType> getAllTaskTypes() throws DataBaseException;

    Subject getSubject(int id) throws DataBaseException;
}
