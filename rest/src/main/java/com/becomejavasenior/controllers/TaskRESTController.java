package com.becomejavasenior.controllers;

import com.becomejavasenior.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@RestController
@RequestMapping("rest/tasks")
public class TaskRESTController {
    @Autowired
    TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Task> getTasks() throws DataBaseException {
        return taskService.getAllTask();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable int id) throws DataBaseException {
        return taskService.findTaskById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void addTask(@RequestBody Task task) throws DataBaseException {
        taskService.saveTask(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void editTask(@PathVariable int id, @RequestBody Task task) throws DataBaseException {
        taskService.saveTask(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable int id) throws DataBaseException {
        taskService.deleteTask(id);
    }
}
