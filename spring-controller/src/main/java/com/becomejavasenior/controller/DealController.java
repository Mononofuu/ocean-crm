package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@Controller
public class DealController {
    private static final Logger LOGGER = LogManager.getLogger(DealController.class);

    @Autowired
    private DealService dealService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/dealslist")
    public String getAllDealsForList(Model model) throws DataBaseException, ServiceException {
        List<Deal> dealsList = dealService.findDeals();
        List<DealStatus> dealStatusList = dealService.getAllDealStatuses();
        List<User> userList = userService.getAllUsersLite();
        List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
        List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));

        model.addAttribute("deals", dealsList);
        model.addAttribute("deals_map", dealStatusList);
        model.addAttribute("managers", userList);
        model.addAttribute("filterperiod", filterPeriods);
        model.addAttribute("filtertask", filterTaskTypes);

        return "dealslist";
    }

    @RequestMapping("/dealspyramid")
    public String getAllDealsForPyramid(Model model) throws DataBaseException {
        List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
        List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));
        List<Contact> contacts = contactService.findContactsLite();
        List<Filter> filters = dealService.findDealFilters();
        model.addAttribute("filterperiod", filterPeriods);
        model.addAttribute("filtertask", filterTaskTypes);
        model.addAttribute("managers", contacts);
        model.addAttribute("filters", filters);

        return "dealspyramid";
    }

    @RequestMapping("/deal")
    public String addNewDeal(Model model) {
        return "newdeal";
    }

}
