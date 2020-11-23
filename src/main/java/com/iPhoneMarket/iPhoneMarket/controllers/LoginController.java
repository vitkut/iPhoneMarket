package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import com.iPhoneMarket.iPhoneMarket.service.LoginedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, Model model){
        User user = userRepository.findByUsername(username);
        if(user == null){
            model.addAttribute("param.error", true);
            return "login";
        }
        user.setActive(true);
        LoginedUser.setUser(user);
        return "redirect:/";
    }
}
