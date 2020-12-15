package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.geom.FlatteningPathIterator;
import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/admin/products")
    public String adminProductsGet(Model model){
        List<Product> products =((ProductServiceImpl) productService).findAll();
        model = headerService.getHeader(model);
        model.addAttribute("products", products);
        return "admin-products";
    }

    @GetMapping("/admin/products/{id}/edit")
    public String adminProductsEditGet(@PathVariable(name = "id") Integer id, Model model){
        model = headerService.getHeader(model);
        Product product = productService.findById(id);
        ArrayList<Product> resultProduct = new ArrayList<>();
        resultProduct.add(product);
        model.addAttribute("titleName", product.getName());
        model.addAttribute("product", resultProduct);
        return "product-edit";
    }

    @PostMapping("/admin/products/{id}/edit")
    public String adminProductsEditPost(@PathVariable(name = "id") Integer id, @RequestParam(name = "name") String name,
                                        @RequestParam(name = "price") String priceStr, @RequestParam(name = "charact") String charact, Model model){
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
            Product product = productService.findById(id);
            product.setName(name);
            product.setPrice(price);
            product.setCharact(charact);
            productService.save(product);
        } catch (Exception ex){
            model.addAttribute("error", true);
            return "product-edit";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/{id}/delete")
    public String adminProductsDeleteGet(@PathVariable(name = "id") Integer id, Model model){
        model = headerService.getHeader(model);
        Product product = productService.findById(id);
        ArrayList<Product> resultProduct = new ArrayList<>();
        resultProduct.add(product);
        model.addAttribute("titleName", product.getName());
        model.addAttribute("product", resultProduct);
        return "product-delete";
    }

    @PostMapping("/admin/products/{id}/delete/true")
    public String adminProductsDeleteTruePost(@PathVariable(name = "id") Integer id, Model model){
        model = headerService.getHeader(model);
        try{
            ((ProductServiceImpl) productService).delete(id);
        } catch (Exception ex){
            return "accessDenied";
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/{id}/delete/false")
    public String adminProductsDeleteFalsePost(@PathVariable(name = "id") Integer id, Model model){
        model = headerService.getHeader(model);
        return "redirect:/admin/products/"+id;
    }

    @GetMapping("/admin/users")
    public String usersGet(Model model){
        model = headerService.getHeader(model);
        List<User> users = ((UserServiceImpl) userService).findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/admin/users/{username}")
    public String userProfileGet(@PathVariable(name = "username") String username, Model model){
        model = headerService.getHeader(model);
        try{
            User user = userService.findByUsername(username);
            List<User> resUser = new ArrayList<>();
            resUser.add(user);
            model.addAttribute("profile", resUser);
            model.addAttribute("roles", user.getRoles());
            model.addAttribute("products", user.getProducts());
            model.addAttribute("username", username);
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
        }
        return "user-profile";
    }

    @PostMapping("/admin/users/{username}/delete")
    public String deleteUserProfilePost(@PathVariable(name = "username") String username, Model model){
        try{
            User user = userService.findByUsername(username);
            ((UserServiceImpl) userService).remove(user);
            model.addAttribute("message", "User "+username+" is successfully deleted");
        } catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
        }
        return "redirect:/admin/users";
    }
}
