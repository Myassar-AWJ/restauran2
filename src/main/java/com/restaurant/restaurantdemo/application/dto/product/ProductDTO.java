package com.restaurant.restaurantdemo.application.dto.product;

import com.restaurant.restaurantdemo.domain.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer plu;
    private String image;
    private Set<String> menusName;
}
