package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfaceservice.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Lybachevskiy.Vladislav
 */
public class TaskServiceImpl implements TaskService {
    private Logger logger = LogManager.getLogger(TaskServiceImpl.class);
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

    @Override
    public List<Task> getTasksByParameters(Map<String, String[]> parameters) throws DataBaseException {
        String userId = null;
        Date date = null;
        String taskTypeId = null;
        String filter = parameters.get("filtername")[0];
        switch (filter){
            case "mytasks":
                userId = parameters.get("currentuser")[0];
                break;
            case "overduetasks":
                date = new Date();
                userId = parameters.get("currentuser")[0];
                break;
        }
        // добавление фильтра по дате
        if(date==null){ //если дата уже определенна как "сейчас" пропускаем
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            try {
                String dateFromRequest = parameters.get("duedate")[0];
                if(!"".equals(dateFromRequest)){
                    String time = parameters.get("duetime")[0];
                    if("".equals(time)){
                        time = "23:59";
                    }
                    date = dateFormat.parse(dateFromRequest+" "+time);
                }
            } catch (ParseException e) {
                logger.error("Неверный формат даты", e);
            }
        }

        // добавление фильтра по типу задачи
        String taskType = parameters.get("tasktype")[0];
        if(!"".equals(taskType)){
            taskTypeId = taskType;
        }
        // добавление филтра по пользователю
        String user = parameters.get("user")[0];
        if (!"".equals(user)){
            userId = user;
        }
        return myDao.getAllTasksByParameters(userId, date, taskTypeId);
    }
}
