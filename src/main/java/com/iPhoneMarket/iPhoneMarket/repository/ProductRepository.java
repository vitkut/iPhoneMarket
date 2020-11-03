package com.iPhoneMarket.iPhoneMarket.repository;

import com.iPhoneMarket.iPhoneMarket.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends CrudRepository<Product, Integer> {
}
