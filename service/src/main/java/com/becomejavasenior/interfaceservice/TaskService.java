package com.becomejavasenior.interfaceservice;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Subject;
import com.becomejavasenior.Task;

import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
public interface TaskService {

    void saveTask(Task task) throws DataBaseException;

    void deleteTask(int id) throws DataBaseException;

    Task findTaskById(int id) throws DataBaseException;

    List<Task> getAllTask() throws DataBaseException;

    List<Task> getTasksBySubject(Subject subject) throws DataBaseException;

}
