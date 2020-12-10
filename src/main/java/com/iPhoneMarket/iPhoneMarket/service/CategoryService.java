package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category, Integer productId);

    Category findById(Integer id);

    Category findByName(String name);

    Category findByUrlName(String urlName);

    List<Category> findAll();
}
