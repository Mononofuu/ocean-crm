package com.becomejavasenior.interfacedao;

import com.becomejavasenior.GenericDao;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Task;
import com.becomejavasenior.*;
import java.util.Date;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
public interface TaskDAO extends GenericDao<Task>{
    List<Task> getAllTasksBySubjectId(int id) throws DataBaseException;
    List<Task> getAllTasksByParameters(String userId, Date date, String taskTypeId) throws DataBaseException;
    List<Task> getAllTasksBySubject(Subject subject) throws DataBaseException;
    Subject getSubject(int id)throws DataBaseException;
}
