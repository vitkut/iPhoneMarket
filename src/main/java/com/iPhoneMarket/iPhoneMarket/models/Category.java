package com.iPhoneMarket.iPhoneMarket.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    private String urlName;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.urlName = name.toLowerCase().replace(' ', '-');
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
