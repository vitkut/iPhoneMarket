package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HeaderServiceImpl implements HeaderService{

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Override
    public Model getHeader(Model model) {
        if(((SecurityServiceImpl) securityService).isAuthenticated()){
            model.addAttribute("isAuth", true);
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            model.addAttribute("balance", user.getBalance());
        } else {
            model.addAttribute("isAuth", false);
        }

        return model;
    }
}
