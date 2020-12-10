package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.Category;
import com.iPhoneMarket.iPhoneMarket.models.Product;
import com.iPhoneMarket.iPhoneMarket.repository.CategoryRepository;
import com.iPhoneMarket.iPhoneMarket.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void save(Product product, Integer categoryId) {
        logger.debug("(save) product: "+product.toString());
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.getOne(categoryId));
        product.setCategories(categories);
        productRepository.save(product);
    }

    @Override
    public Product findById(Integer id) {
        logger.debug("(findById) id: "+id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }
}
