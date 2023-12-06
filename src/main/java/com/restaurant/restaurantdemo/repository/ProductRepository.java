package com.restaurant.restaurantdemo.repository;

import com.restaurant.restaurantdemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String Name);
    Product findByName(String Name);
}
