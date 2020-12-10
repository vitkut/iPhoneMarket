package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.Category;
import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.repository.CategoryRepository;
import com.iPhoneMarket.iPhoneMarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Category category, Integer productId) {
        Set<Product> products = new HashSet<>();
        products.add(productRepository.getOne(productId));
        category.setProducts(products);
        categoryRepository.save(category);
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    @Override
    public Category findByName(String name) {
        Category needfulCategory;
        for(Category category:findAll()){
            if(category.getName().equals(name)){
                return category;
            }
        }
        return null;
    }

    @Override
    public Category findByUrlName(String urlName) {
        Category needfulCategory;
        for(Category category:findAll()){
            if(category.getName().equals(urlName)){
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }


}
