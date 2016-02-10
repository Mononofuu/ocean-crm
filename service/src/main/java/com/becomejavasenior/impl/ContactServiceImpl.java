package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.interfacedao.ContactDAO;
import com.becomejavasenior.interfacedao.DealContactDAO;
import com.becomejavasenior.interfacedao.PhoneTypeDAO;
import com.becomejavasenior.interfacedao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Peter on 18.12.2015.
 */
@Service
public class ContactServiceImpl extends AbstractContactService<Contact> implements ContactService{
    @Autowired
//    private ContactDAOImpl contactDAO;
    private ContactDAO contactDAO;
    @Autowired
    private PhoneTypeDAO phoneTypeDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private DealContactDAO dealContactDAO;

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
        return phoneTypeDAO.readAll();
    }

    @Override
    public List<Contact> getAllContactsBySubjectId(int id) throws DataBaseException {
        return dealContactDAO.getAllContactsBySubjectId(id);
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
    public List<Tag> getAllContactTags() throws DataBaseException {
        return tagDAO.readAll(SubjectType.CONTACT_TAG);
    }
}
