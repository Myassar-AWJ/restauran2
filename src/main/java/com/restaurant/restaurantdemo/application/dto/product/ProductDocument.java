package com.restaurant.restaurantdemo.application.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductDocument(Long id,
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotBlank(message = "Price is required")
        Double price,
        @NotBlank(message = "PLU is required")// This makes the name field unique
        Integer plu,
        @NotBlank(message = "Image is required")
        String image) {

}
