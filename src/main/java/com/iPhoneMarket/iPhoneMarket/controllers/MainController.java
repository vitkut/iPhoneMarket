package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HeaderService headerService;

    @GetMapping("/")
    public String main(Model model) {
        List<Product> products =((ProductServiceImpl) productService).findAll();
        model = headerService.getHeader(model);
        model.addAttribute("products", products);
        return "main";
    }

    @GetMapping("/products/{id}")
    public String productsIdGet(@PathVariable(name = "id") Integer id, Model model){
        Product product = productService.findById(id);
        ArrayList<Product> resultProduct = new ArrayList<>();
        resultProduct.add(product);
        model = headerService.getHeader(model);
        model.addAttribute("titleName", product.getName());
        model.addAttribute("product", resultProduct);
        return "product";
    }

    @GetMapping("/about")
    public String about(Model model){
        model = headerService.getHeader(model);
        if(((SecurityServiceImpl) securityService).hasRole("ADMIN")){
            model.addAttribute("admin", true);
        } else {
            model.addAttribute("admin", false);
        }
        return "about";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model){
        return "accessDenied";
    }
}