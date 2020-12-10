package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.service.HeaderService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityServiceImpl;
import com.iPhoneMarket.iPhoneMarket.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HeaderService headerService;

    @GetMapping("/admin")
    public String adminGet(Model model){
        logger.debug( "Adm page");

        model = headerService.getHeader(model);

        String username = securityService.findLoggedInUsername();
        model.addAttribute("admin_username", username);
        return "admin";
    }

}
