package com.restaurant.restaurantdemo.domain.prodact;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.restaurantdemo.domain.menu.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Price is required")
    private Double price;
    @NotBlank(message = "PLU is required")
    @Column(unique = true)  // This makes the name field unique
    private Integer plu;
    @NotBlank(message = "Image is required")
    private String image;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Menu> menus = new HashSet<>();


}
