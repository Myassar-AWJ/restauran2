package com.restaurant.restaurantdemo.boundary.output.jpa.product;

import com.restaurant.restaurantdemo.domain.prodact.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String Name);
    Optional<Product> findByName(String name);
}
