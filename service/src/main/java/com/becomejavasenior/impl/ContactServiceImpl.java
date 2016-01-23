package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
public class ContactServiceImpl extends AbstractContactService<Contact> implements ContactService{
    private static Logger logger = LogManager.getLogger(ContactServiceImpl.class);
    private DaoFactory dao;
    private ContactDAOImpl contactDAO;

    public ContactServiceImpl(){
        try {
            dao = new PostgreSqlDaoFactory();
            contactDAO = (ContactDAOImpl) dao.getDao(Contact.class);
        } catch (DataBaseException e) {
            logger.error(e);
        }
    }

    @Override
    public Contact saveContact(Contact contact) throws DataBaseException {
        if (contact.getId() == 0) {
            contact = contactDAO.create(contact);
            if(contact.getTasks()!=null&&contact.getTasks().size()>0){
                saveTasks(contact.getTasks());
            }
            if(contact.getDeals()!=null&&contact.getDeals().size()>0){
                saveDeals(contact.getDeals());
            }
            return contact;
        } else {
            contactDAO.update(contact);
            if(contact.getTasks().size()>0){
                saveTasks(contact.getTasks());
            }
            if(contact.getDeals().size()>0){
                saveDeals(contact.getDeals());
            }
            return contactDAO.read(contact.getId());
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
    protected AbstractContactDAO<Contact> getDao() {
        return contactDAO;
    }

    @Override
    public List<Tag> getAllContactTags() throws DataBaseException {
        TagDAO tagDAO = (TagDAO)dao.getDao(Tag.class);
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }
}
