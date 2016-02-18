package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kramar on 12.02.16.
 */

@RestController
//@RequestMapping("/rest/deals")
public class DealRESTController {
    @Autowired
    DealService dealService;

    private final static Logger LOGGER = LogManager.getLogger(DealController.class);

    //-------------------Retrieve All Deals----------------------------------------

    @RequestMapping(value = "/rest/deal", method = RequestMethod.GET)
    public ResponseEntity<List<Deal>> listAllDeals() {
        List<Deal> deals = new ArrayList<>();

        try {
            deals = dealService.findDeals();
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        if(deals.isEmpty()){
            return new ResponseEntity<List<Deal>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Deal>>(deals, HttpStatus.OK);
    }

    //-------------------Retrieve Single Deal--------------------------------------

    @RequestMapping(value = "/rest/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Deal> getUser(@PathVariable("id") int id) {
        Deal deal = null;
        try {
            deal = dealService.findDealById(id);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        if (deal == null) {
            return new ResponseEntity<Deal>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Deal>(deal, HttpStatus.OK);
    }


    //-------------------Create a Deal--------------------------------------------------------

    @RequestMapping(value = "/deal/", method = RequestMethod.POST)
    public ResponseEntity<Void> createDeal(@RequestBody Deal deal, UriComponentsBuilder ucBuilder) {
        try {
            dealService.saveDeal(deal);
        } catch (DataBaseException e) {
            LOGGER.error(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/deal/{id}").buildAndExpand(deal.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /*

    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {

        User currentUser = userService.findById(id);

        if (currentUser==null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {

        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
    */
}