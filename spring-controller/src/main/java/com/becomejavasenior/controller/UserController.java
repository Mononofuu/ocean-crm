package com.becomejavasenior.controller;

import com.becomejavasenior.*;
import com.becomejavasenior.validation.UserFormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Controller
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private UserFormValidator userFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String userRegister(Model model){
        model.addAttribute("userForm", new User());
        return "userform";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result, Model model,
                                   final RedirectAttributes redirectAttributes) throws ServiceException {
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
            return "userform";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            authService.registration(user);
            return "sign_in";
        }
    }
}
