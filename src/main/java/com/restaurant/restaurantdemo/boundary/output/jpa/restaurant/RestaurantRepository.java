package com.restaurant.restaurantdemo.boundary.output.jpa.restaurant;

import com.restaurant.restaurantdemo.domain.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findByNameContaining(String name);
    List<Restaurant> findByAddressContaining(String Address);
    Restaurant findByName(String name);
    Restaurant findByNameAndIdNot(String name, Long id);


}
