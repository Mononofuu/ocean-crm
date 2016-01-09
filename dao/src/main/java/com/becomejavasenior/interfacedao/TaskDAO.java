package com.becomejavasenior.interfacedao;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.GenericDao;
import com.becomejavasenior.Task;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface TaskDAO{
    List<Task> getAllTasksBySubjectId(int id) throws DataBaseException;
}
