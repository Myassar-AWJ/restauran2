package com.restaurant.restaurantdemo.application;

import com.restaurant.restaurantdemo.application.service.menu.CreateMenuCommand;
import com.restaurant.restaurantdemo.application.service.menu.MenuService;
import com.restaurant.restaurantdemo.application.service.product.CreateProductCommand;
import com.restaurant.restaurantdemo.application.service.product.ProductService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantFacade {

    private final MenuService menuService;

    private final ProductService productService;

    public Long handle(CreateMenuCommand createMenuCommand) {
        return menuService.handle(createMenuCommand);
    }

    public Long handle(CreateProductCommand createProductCommand) {
        return productService.handle(createProductCommand);
    }

}
