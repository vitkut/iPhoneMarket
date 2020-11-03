package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/shop")
    public String shopMain(Model model) {
        model.addAttribute("title", "Shop");
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "shop-main";
    }
}