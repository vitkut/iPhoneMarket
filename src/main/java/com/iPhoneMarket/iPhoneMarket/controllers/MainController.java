package com.iPhoneMarket.iPhoneMarket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "Main page");
        return "main";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About");
        return "about";
    }
}