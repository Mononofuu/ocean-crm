package com.becomejavasenior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateDBTestData {
    private static DaoFactory dao;
    private static Logger logger = LogManager.getLogger(CreateDBTestData.class);
    private static List<User> userList = new ArrayList<>();
    private static List<Company> companyList = new ArrayList<>();
    private static List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            dao = new PostgreSqlDaoFactory();
            createCompany();
            createUser();
            createTasks();
        } catch (DataBaseException e) {
            logger.error(e);
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    private static void createCompany() throws DataBaseException,MalformedURLException{
        GenericDao companyDao = dao.getDao(Company.class);
        for(int i=0;i<5;i++){
            Company company = new Company();
            company.setName("компания"+i);
            company.setEmail("company"+i+"@company.com");
            company.setWeb(new URL("http://www.google.com"));
            companyList.add((Company)companyDao.create(company));
        }
    }

    private static void createUser() throws DataBaseException{
        GenericDao userDao = dao.getDao(User.class);
        for(int i=0;i<5;i++){
            User user = new User();
            user.setName("пользователь"+i);
            user.setEmail("user"+i+"@company.com");
            userList.add((User) userDao.create(user));
        }
    }

    private static void createTasks() throws DataBaseException, ParseException{
        GenericDao taskDao = dao.getDao(Task.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(int i=0;i<5;i++){
            Task task = new Task();
            task.setComment("текст задачи №"+i);
            task.setUser(userList.get(i));
            task.setSubject(companyList.get(i));
            task.setDateCreated(new Date());
            task.setDueTime(dateFormat.parse("20/04/202"+i));
            task.setType(TaskType.MEETING);
            taskList.add((Task)taskDao.create(task));
        }
    }
}
