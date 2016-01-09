package com.becomejavasenior.interfaceservice;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */
public interface TaskService {

    void saveTask(Task task) throws DataBaseException;

    void deleteTask(int id) throws DataBaseException;

    Task findTaskById(int id) throws DataBaseException;

    List<Task> getAllTask() throws DataBaseException;

    List<Task> getTasksBySubject(Subject subject) throws DataBaseException;

    List<Task> getTasksByParameters(Map<String, String[]> parameters) throws DataBaseException;
}
