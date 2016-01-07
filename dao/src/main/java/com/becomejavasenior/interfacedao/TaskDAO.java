package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Task;
import com.becomejavasenior.TaskType;
import com.becomejavasenior.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface TaskDAO {
    List<Task> getAllTasksBySubjectId(int id) throws DataBaseException;
    List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException;
}
