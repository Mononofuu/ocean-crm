package com.becomejavasenior.controller;

import com.becomejavasenior.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model){
        model.addAllAttributes(dashboardService.getDashboardInformation());
        return "dashboard";
    }
}
