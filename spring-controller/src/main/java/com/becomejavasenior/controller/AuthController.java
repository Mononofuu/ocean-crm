package com.becomejavasenior.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */

@Controller
public class AuthController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(){
        return "sign_in";
    }

}
