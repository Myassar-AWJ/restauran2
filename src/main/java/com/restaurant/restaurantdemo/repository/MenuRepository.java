package com.restaurant.restaurantdemo.repository;

import com.restaurant.restaurantdemo.model.Menu;
import com.restaurant.restaurantdemo.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    Menu findByName(String name);
    Menu findByNameAndIdNot(String name, Long id);
}
