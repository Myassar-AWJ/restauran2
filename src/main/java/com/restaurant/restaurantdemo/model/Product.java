package com.restaurant.restaurantdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer plu;
    private String image;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Menu> menus = new HashSet<>();

    public Product(String name, String description, Double price, Integer plu, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.plu = plu;
        this.image = image;
    }

    public Product() {
        // Default constructor
    }
}
