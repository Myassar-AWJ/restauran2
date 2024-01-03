package com.restaurant.restaurantdemo.boundary.output.jpa.menu;

import com.restaurant.restaurantdemo.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    Menu findByName(String name);
    Menu findByNameAndIdNot(String name, Long id);
}
