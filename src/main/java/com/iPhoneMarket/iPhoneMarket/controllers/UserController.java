package com.iPhoneMarket.iPhoneMarket.controllers;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.service.HeaderService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityService;
import com.iPhoneMarket.iPhoneMarket.service.SecurityServiceImpl;
import com.iPhoneMarket.iPhoneMarket.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HeaderService headerService;

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
                                   @RequestParam(name = "confirmPassword") String confirmPassword, Model model){
        User user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("error", "Username is incorrect");
            return "reg";
        }
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "Confirm password is incorrect");
            return "reg";
        }
        user = new User(username, password);
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profileGet(Model model){
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        ArrayList<User> resultUser = new ArrayList<>();
        resultUser.add(user);
        model = headerService.getHeader(model);
        model.addAttribute("titleName", user.getUsername());
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
}
