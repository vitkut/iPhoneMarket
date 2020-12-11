package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.geom.FlatteningPathIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ProductService productService;

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

    @GetMapping("/admin/add-product")
    public String addProductGet(Model model){
        model = headerService.getHeader(model);
        return "add-product";
    }

    @PostMapping("/admin/add-product")
    public String addProductPost(@RequestParam(name = "name") String name, @RequestParam(name = "price") String priceStr,
                                 @RequestParam(name = "charact") String charact, Model model){
        try{
            if(name.equals("") || name.length()>255){
                throw new Exception("Wrong name");
            }
            Float price = Float.parseFloat(priceStr);
            if(price <= 0){
                throw new Exception("Wrong price");
            }
            if(charact.equals("") || charact.length() == 0){
                throw new Exception("Wrong charact");
            }
            productService.save(new Product(name, price, charact));
        } catch (Exception ex){
            model.addAttribute("error", true);
            return "add-product";
        }
        return "redirect:/";
    }

}
