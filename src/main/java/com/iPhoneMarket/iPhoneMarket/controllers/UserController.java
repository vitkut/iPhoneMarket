package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.SecurityService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityServiceImpl;
import com.iPhoneMarket.iPhoneMarket.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    public String loginGet(Model model){
        return "login";
    }

    @PostMapping("/login_processing")
    public String loginPost(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, Model model){

        User user = userService.findByUsername(username);
        logger.debug("(loginPost) user: "+ user);
        if(user == null){
            return "login";
        }
        if(!user.getPassword().equals(password)){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/sign-out")
    public String signOutGet(Model model){
        ((SecurityServiceImpl) securityService).signOut();
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String registrationGet(Model model){
        return "reg";
    }

    @PostMapping("/reg")
    public String registrationPost(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                                   @RequestParam(name = "confirmPassword") String confirmPassword, Model model){
        User user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("error", "Username is incorrect");
            return "reg";
        }
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "Confirm password is incorrect");
            return "reg";
        }
        user = new User(username, password);
        userService.save(user);
        return "redirect:/login";
    }
}
