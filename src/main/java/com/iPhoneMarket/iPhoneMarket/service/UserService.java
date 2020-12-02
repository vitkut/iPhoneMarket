package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
