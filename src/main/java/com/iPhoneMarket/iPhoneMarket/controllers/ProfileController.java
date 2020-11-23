package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import com.iPhoneMarket.iPhoneMarket.service.LoginedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Model model){
        if(LoginedUser.getUser() == null){
            return "redirect:/login";
        } else {
            return "redirect:/profile/"+LoginedUser.getUser().getUsername();
        }
    }

    @GetMapping("/profile/{username}")
    public String profileOfUser(@PathVariable(value = "username") String username, Model model){
        User user = userRepository.findByUsername(username);
        if(user == null){
            return "redirect:/login";
        }
        model.addAttribute("title", username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("isActive", user.isActive());
        return "profile";
    }

    @GetMapping("/profile/{username}/delete")
    public String profileOfUserPost(@PathVariable(value = "username") String username, Model model){
        User user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
        }
        return "redirect:/login";
    }

    @GetMapping("/profile/{username}/signout")
    public String signOutUser(@PathVariable(value = "username") String username, Model model){
        User user = userRepository.findByUsername(username);
        if(user != null){
            user.setActive(false);
            LoginedUser.setUser(null);
        }
        return "redirect:/login";
    }
}
