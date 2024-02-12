package com.restaurant.restaurantdemo.application.service.menu;

import com.restaurant.restaurantdemo.domain.prodact.Product;
import java.util.Set;

public record CreateMenuCommand(Long id,
                                String name,
                                Set<Product> products) {

}
