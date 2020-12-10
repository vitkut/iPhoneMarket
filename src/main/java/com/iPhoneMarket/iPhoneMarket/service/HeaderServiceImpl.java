package com.iPhoneMarket.iPhoneMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HeaderServiceImpl implements HeaderService{

    @Autowired
    private SecurityService securityService;

    @Override
    public Model getHeader(Model model) {
        if(((SecurityServiceImpl) securityService).isAuthenticated()){
            model.addAttribute("isAuth", true);
        } else {
            model.addAttribute("isAuth", false);
        }

        return model;
    }
}
