package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/shop/addProduct")
    public String shopAdd(Model model) {
        model.addAttribute("title", "Add product");
        return "shop-add";
    }

    @GetMapping("/shop/{id}")
    public String shopDetails(@PathVariable(value = "id") int id, Model model){
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> result = new ArrayList<>();
        product.ifPresent(result::add);
        model.addAttribute("product", result);
        return "shop-details";
    }

    @GetMapping("/shop/{id}/edit")
    public String shopEdit(@PathVariable(value = "id") int id, Model model){
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> result = new ArrayList<>();
        product.ifPresent(result::add);
        model.addAttribute("product", result);
        return "shop-edit";
    }

    @PostMapping("/shop/addProduct")
    public String shopPostAddProduct(@RequestParam String name, @RequestParam String price, @RequestParam String description, Model model){
        Product product = new Product(name, Float.parseFloat(price), description);
        productRepository.save(product);
        return "redirect:/shop";
    }

    @PostMapping("/shop/{id}/edit")
    public String shopPostEdit(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String price, @RequestParam String description, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setDescription(description);
        productRepository.save(product);
        return "redirect:/shop/{id}";
    }

    @PostMapping("/shop/{id}/delete")
    public String shopPostDelete(@PathVariable(value = "id") int id, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/shop";
    }
}