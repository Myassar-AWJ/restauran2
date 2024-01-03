package com.restaurant.restaurantdemo.application.dto.menu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private  Long id;
    private  String name;
    private Set<String> productNames; // Include product names in the DTO

}
