package com.restaurant.restaurantdemo.application.service.product;

public record CreateProductCommand(Long id,
                                   String name,
                                   String description,
                                   Double price,
                                   Integer plu,
                                   String image) {

}
