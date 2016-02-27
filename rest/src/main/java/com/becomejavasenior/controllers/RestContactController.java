package com.becomejavasenior.controllers;

import com.becomejavasenior.Contact;
import com.becomejavasenior.ContactService;
import com.becomejavasenior.DataBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
@RequestMapping("rest/contacts")
public class RestContactController {
    @Autowired
    private ContactService contactService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getContacts() throws DataBaseException {
        return contactService.findContacts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContact(@PathVariable int id) throws DataBaseException{
        return contactService.findContactById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public Contact addContact(@RequestBody Contact contact) throws DataBaseException{
        return contactService.saveContact(contact);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editContact(@PathVariable int id,  @RequestBody Contact contact) throws DataBaseException{
        contactService.saveContact(contact);
    }

    @RequestMapping(name = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable int id)throws DataBaseException{
        contactService.deleteContact(id);
    }


}
