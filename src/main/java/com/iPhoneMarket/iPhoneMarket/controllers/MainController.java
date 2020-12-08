package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.SecurityService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityServiceImpl;
import com.iPhoneMarket.iPhoneMarket.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/about")
    public String about(Model model){
        if(((SecurityServiceImpl) securityService).hasRole("ADMIN")){
            model.addAttribute("admin", true);
        }
        return "about";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model){
        return "accessDenied";
    }
}