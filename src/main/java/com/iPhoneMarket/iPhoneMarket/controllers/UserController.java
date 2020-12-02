package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginGet(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, String error, Model model){
        if(error != null){
            model.addAttribute("error", "Username or password is incorrect");
        }

        User user = userService.findByUsername(username);
        if(user == null){
            error = "Err";
            return "login";
        }
        if(!user.getPassword().equals(password)){
            error = "Err";
            return "login";
        }
        return "redirect:/";
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
