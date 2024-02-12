package com.restaurant.restaurantdemo.application.dto.menu;


import com.restaurant.restaurantdemo.application.dto.product.ProductDocument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDocument { // in Kitopi we usually use Document suffix for payloads and returned values of requests
    // in this case this class should be named MenuDocument
    private  Long id;
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private  String name;
    private Set<ProductDocument> products;

}
