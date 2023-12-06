package com.restaurant.restaurantdemo.repository;

import com.restaurant.restaurantdemo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.awt.*;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findByNameContaining(String name);
    List<Restaurant> findByAddressContaining(String Address);
    Restaurant findByName(String name);
}
