package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.User;

public class LoginedUser {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginedUser.user = user;
    }
}
