package com.iPhoneMarket.iPhoneMarket.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "Main page");
        logger.error("Enter od Main page (debug)");
        return "main";
    }

    @GetMapping("/about")

    public String about(Model model){
        model.addAttribute("title", "About");
        logger.error("Enter od About page (debug)");
        return "about";
    }
}