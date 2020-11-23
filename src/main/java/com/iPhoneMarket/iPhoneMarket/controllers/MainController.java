package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import com.iPhoneMarket.iPhoneMarket.service.LoginedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "Main page");
        if(LoginedUser.getUser() == null){
            model.addAttribute("param.isSigned", "Sing in");
        } else {
            model.addAttribute("param.isSigned", LoginedUser.getUser().getUsername());
        }
        return "main";
    }

    @GetMapping("/about")

    public String about(Model model){
        model.addAttribute("title", "About");
        if(LoginedUser.getUser() == null){
            model.addAttribute("param.isSigned", "Sing in");
        } else {
            model.addAttribute("param.isSigned", LoginedUser.getUser().getUsername());
        }
        return "about";
    }
}