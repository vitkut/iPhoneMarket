package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Role;
import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUserPost(@RequestParam String username, @RequestParam String password, Model model){

        User user = new User(username, password);

        System.out.println("User: "+user.getUsername());

        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            model.addAttribute("param.error", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
