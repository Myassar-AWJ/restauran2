package com.restaurant.restaurantdemo.application.service.product;


import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.boundary.output.jpa.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;


    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }

    public List<Product> getProductsContainingName(String name) {

        return productRepository.findByNameContaining(name);

    }

    public Product getProductByName(String name) {

        return productRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Product not found"));

    }

    public Product getProductById(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

    }

    public Long handle(CreateProductCommand command) {
        var product = new Product(command.id(), command.name(), command.description(), command.price(), command.plu(), command.image(), new HashSet<>());
        productRepository.save(product);
        return command.id();

    }

    public Product updateProduct(Long id, Product product) {

        var oldProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPlu(product.getPlu());
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setImage(product.getImage());
        return productRepository.save(oldProduct);

    }

    public void deleteProductById(Long id) {

        productRepository.deleteById(id);

    }
}
