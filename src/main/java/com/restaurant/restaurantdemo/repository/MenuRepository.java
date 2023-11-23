package com.restaurant.restaurantdemo.repository;

import com.restaurant.restaurantdemo.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
//    @EntityGraph(attributePaths = "products")
//    Optional<Menu> findById(Long id);

//    @EntityGraph(attributePaths = "products")
//    Menu findByIdWithProducts(Long id);
}
