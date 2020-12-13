package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HeaderService headerService;

    @Autowired
    private ProductService productService;


    @GetMapping("/login")
    public String loginGet(Model model){
        return "login";
    }

    @PostMapping("/login_processing")
    public String loginPost(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, Model model){

        User user = userService.findByUsername(username);
        logger.debug("(loginPost) user: "+ user);
        if(user == null){
            return "login";
        }
        if(!user.getPassword().equals(password)){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String registrationGet(Model model){
        return "reg";
    }

    @PostMapping("/reg")
    public String registrationPost(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "confirmPassword") String confirmPassword, Model model){

        try {
            User user = userService.findByUsername(username);
            if(user != null){
                throw new Exception("Username is already exist");
            }
            if(username.length() < 4 || username.length() > 255){
                throw new Exception("Username is very short or large");
            }
            if(!password.equals(confirmPassword)){
                throw new Exception("Password and confirm password if not the same");
            }
            if(name.length() < 1 || name.length() > 255){
                throw new Exception("Username is very short or large");
            }
            user = new User(username, password, name);
            userService.save(user);
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "reg";
        }

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profileGet(Model model){
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        ArrayList<User> resultUser = new ArrayList<>();
        resultUser.add(user);
        model = headerService.getHeader(model);
        model.addAttribute("titleName", user.getName());
        model.addAttribute("profile", resultUser);
        return "profile";
    }

    @GetMapping("/profile/input-money/card-check")
    public String cardCheckGet(Model model){
        model = headerService.getHeader(model);
        return "card-check";
    }

    @PostMapping("/profile/input-money/card-check")
    public String cardCheckPost(@RequestParam(name = "cardNumber1") String cardNumber1Str, @RequestParam(name = "cardNumber2") String cardNumber2Str,
                                @RequestParam(name = "cardNumber3") String cardNumber3Str, @RequestParam(name = "cardNumber4") String cardNumber4Str,
                                @RequestParam(name = "validityMonth") String validityMonthStr, @RequestParam(name = "amount") String amountStr,
                                @RequestParam(name = "validityYear") String validityYearStr, @RequestParam(name = "cvv") String cvvStr,
                                Model model){
        try{
            if(cardNumber1Str.length() != 4 || cardNumber2Str.length() != 4 || cardNumber3Str.length() != 4 || cardNumber4Str.length() != 4){
                throw new Exception("Card number is wrong");
            }
            if(cvvStr.length() != 3){
                throw new Exception("CVV is wrong");
            }
            int cardNumber = Integer.parseInt(cardNumber1Str);
            if(cardNumber < 0){
                throw new Exception("Card number is wrong");
            }
            cardNumber = Integer.parseInt(cardNumber2Str);
            if(cardNumber < 0){
                throw new Exception("Card number is wrong");
            }
            cardNumber = Integer.parseInt(cardNumber3Str);
            if(cardNumber < 0){
                throw new Exception("Card number is wrong");
            }
            cardNumber = Integer.parseInt(cardNumber4Str);
            if(cardNumber < 0){
                throw new Exception("Card number is wrong");
            }

            int validityMonth = Integer.parseInt(validityMonthStr);
            int validityYear = Integer.parseInt(validityYearStr);
            int cvv = Integer.parseInt(cvvStr);
            if(validityMonth <= 0 || validityMonth > 12){
                throw new Exception("Validity month is wrong");
            }
            if(validityYear < 0 || validityYear > 99){
                throw new Exception("Validity year is wrong");
            }
            if(cvv < 0){
                throw new Exception("CVV is wrong");
            }
            Float amount = Float.parseFloat(amountStr);
            if(amount <= 0 || amount > 100000){
                throw new Exception("Amount is wrong");
            }
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            user.setBalance(user.getBalance()+amount);
            userService.save(user);
            return "redirect:/profile";
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
        }
        return "card-check";
    }

    @GetMapping("/profile/basket")
    public String basketGet(Model model){
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        model = headerService.getHeader(model);
        model.addAttribute("name", user.getName());
        model.addAttribute("products", user.getProducts());
        if(user.getProducts().isEmpty()){
            model.addAttribute("message", "Basket is empty");
        }
        return "basket";
    }

    @PostMapping("/profile/to-basket/{id}")
    public String toBasketPost(@PathVariable(name = "id") Integer id, Model model){
        try{
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            Product product = productService.findById(id);
            Set<Product> userProducts = user.getProducts();
            if(userProducts.contains(product)){
                throw new Exception("Has been added");
            }
            userProducts.add(product);
            user.setProducts(userProducts);
            userService.save(user);
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "redirect:/products/"+id;
        }
        return "redirect:/profile/basket";
    }

    @GetMapping("/profile/basket/buy/{id}")
    public String buyProductInBasketGet(Model model){
        model = headerService.getHeader(model);
        return "accessDenied";
    }

    @PostMapping("/profile/basket/buy/{id}")
    public String buyProductInBasketPost(@PathVariable(name = "id") Integer id, Model model){
        try{
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            Product product = productService.findById(id);
            if(product.getPrice() > user.getBalance()){
                throw new Exception("Not enough money");
            }
            user.getProducts().remove(product);
            user.setBalance(user.getBalance()-product.getPrice());
            userService.save(user);
            model.addAttribute("message", "You have successfully bought "+product.getName());
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "redirect:/profile/basket";
        }
        return "redirect:/";
    }

    @GetMapping("/profile/basket/delete/{id}")
    public String deleteProductInBasketGet(Model model){
        model = headerService.getHeader(model);
        return "accessDenied";
    }

    @PostMapping("/profile/basket/delete/{id}")
    public String deleteProductInBasketPost(@PathVariable(name = "id") Integer id, Model model){
        try{
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            Product product = productService.findById(id);
            if(!user.getProducts().contains(product)){
                throw new Exception("There is no such product in your basket");
            }
            user.getProducts().remove(product);
            userService.save(user);
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/profile/basket";
    }

    @GetMapping("/profile/edit")
    public String editProfileGet(Model model){
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        List<User> resUser = new ArrayList<>();
        resUser.add(user);
        model = headerService.getHeader(model);
        model.addAttribute("titleName", "Edit");
        model.addAttribute("user", resUser);
        return "edit-user";
    }

    @PostMapping("/profile/edit")
    public String editProfilePost(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password,
                                  @RequestParam(name = "confirm_password") String confirmPassword, Model model){
        try{
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            if(user.getName().equals(name) && user.getPassword().equals(password)){
                model.addAttribute("message", "Successfully edited");
                return "redirect:/profile";
            }
            if(!user.getPassword().equals(password) && !password.equals(confirmPassword)){
                throw new Exception("Password and confirm password if not the same");
            }
            if(name.length() < 1 || name.length() > 255){
                throw new Exception("Name is very short or large");
            }
            user.setName(name);
            user.setPassword(password);
            userService.save(user);
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "redirect:/profile/edit";
        }
        return "redirect:/profile";
    }
}
