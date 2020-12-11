package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.Product;

public interface ProductService {

    void save(Product product);

    Product findById(Integer id);
}
