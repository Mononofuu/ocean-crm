package com.becomejavasenior.controllers;

import com.becomejavasenior.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kramar on 12.02.16.
 */

@RestController
@RequestMapping("/rest/deals")
public class DealRESTController {
    @Autowired
    DealService dealService;
    @Autowired
    ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Deal> getDeals() throws DataBaseException {
        List<Deal> deals = dealService.findDeals();
        Deal deal;
        Contact contact;
        for (int i = 0; i < deals.size(); i++) {
            deal = deals.get(i);
            contact = contactService.findContactById(deal.getMainContact().getId());
            contact.setDeals(null);
            deal.setMainContact(contact);
            deal.setComments(null);
            deal.setContacts(null);
            deal.setFiles(null);
            deal.setTasks(null);
        }
        return deals;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Deal getDeal(@PathVariable int id) throws DataBaseException{
        Deal deal = dealService.findDealById(id);
        Contact contact = contactService.findContactById(deal.getMainContact().getId());
        contact.setDeals(null);
        deal.setMainContact(contact);
        deal.setComments(null);
        deal.setContacts(null);
        deal.setFiles(null);
        deal.setTasks(null);
        return deal;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public Deal addDeal(@RequestBody Deal deal) throws DataBaseException{
        return dealService.saveDeal(deal);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editDeal(@PathVariable int id,  @RequestBody Deal deal) throws DataBaseException{
        dealService.saveDeal(deal);
    }

    @RequestMapping(name = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeal(@PathVariable int id)throws DataBaseException{
        dealService.deleteDeal(id);
    }

    @RequestMapping(value = "/dealstatuses", method = RequestMethod.GET)
    @ResponseBody
    public List<DealStatus> getDealStatuses() throws DataBaseException {
        return dealService.getAllDealStatuses();
    }
}