package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 18.12.2015.
 */
public class ContactServiceImpl implements ContactService{
    private static Logger logger = LogManager.getLogger(ContactServiceImpl.class);
    private DaoFactory dao;
    private ContactDAO contactDAO;

    public ContactServiceImpl(){
        try {
            dao = new PostgreSqlDaoFactory();
            contactDAO = (ContactDAO) dao.getDao(Contact.class);
        } catch (DataBaseException e) {
            logger.error(e);
        }
    }

    @Override
    public void saveContact(Contact contact) throws DataBaseException {
        if (contact.getId() == 0) {
            contactDAO.create(contact);
            if(contact.getTasks()!=null&&contact.getTasks().size()>0){
                saveTasks(contact.getTasks());
            }
            if(contact.getDeals()!=null&&contact.getDeals().size()>0){
                saveDeals(contact.getDeals());
            }
        } else {
            contactDAO.update(contact);
            if(contact.getTasks().size()>0){
                saveTasks(contact.getTasks());
            }
            if(contact.getDeals().size()>0){
                saveDeals(contact.getDeals());
            }
        }
    }

    @Override
    public void deleteContact(int id) throws DataBaseException {
        contactDAO.delete(id);
    }

    @Override
    public Contact findContactById(int id) throws DataBaseException {
        Contact contact = contactDAO.read(id);
        return contact;
    }

    @Override
    public List<Contact> findContacts() throws DataBaseException{
        List<Contact> contactList = contactDAO.readAll();
        return contactList;
    }

    @Override
    public List<Contact> findContactsLite() throws DataBaseException{
        List<Contact> contactList = contactDAO.readAllLite();
        return contactList;
    }

    @Override
    public Contact findContactByName(String name) throws DataBaseException {
        return contactDAO.readContactByName(name);
    }

    @Override
    public List<PhoneType> getAllPhoneTypes() throws DataBaseException {
        GenericDao<PhoneType> phoneTypeDAO = dao.getDao(PhoneType.class);
        return phoneTypeDAO.readAll();
    }

    private void saveTasks(List<Task> tasks) throws DataBaseException{
        TaskService taskService = new TaskServiceImpl();
        for(Task task: tasks){
            taskService.saveTask(task);
        }
    }

    private void saveDeals(List<Deal> deals) throws DataBaseException{
        DealService dealService = new DealServiceImpl();
        for(Deal deal: deals){
            dealService.saveDeal(deal);
        }
    }

    @Override
    public List<Contact> getAllContactsByParameters(Map<String, String[]> parameters) throws DataBaseException {
        List<ContactFilters> parametersList = new ArrayList<>();
        String userId = null;
        List<Integer> tagIdList = new ArrayList<>();
        Date taskStartDate = null;
        Date taskDueDate = null;
        String filter = parameters.get("filtername")[0];
        switch (filter){
            case "overduetaskcontacts":
                parametersList.add(ContactFilters.WITH_OVERDUE_TASKS);
                break;
            case "tasklesscontacts":
                parametersList.add(ContactFilters.WITHOUT_TASKS);
                break;
            default:
                break;
        }
        String user = parameters.get("user")[0];
        if(!"".equals(user)){
            userId=user;
        }

        String[] dealfilters = parameters.get("dealfilters");
        if(dealfilters.length>0){
            for(String dealFilter: dealfilters){
            }
        }

        return contactDAO.getAllContactsByParameters(parametersList, userId, tagIdList, taskStartDate, taskDueDate);
    }
}
