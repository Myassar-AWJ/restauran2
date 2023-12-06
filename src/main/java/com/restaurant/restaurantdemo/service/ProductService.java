package com.restaurant.restaurantdemo.service;


import com.restaurant.restaurantdemo.model.Product;
import com.restaurant.restaurantdemo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final LoggerService logger;

    @Autowired
    public ProductService(ProductRepository productRepository, LoggerService logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }


    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving all products", e.getMessage());
            throw new RuntimeException("Error while retrieving all products", e);
        }
    }

    public List<Product> getProductsContainingName(String name) {
        try {
            return productRepository.findByNameContaining(name);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving products containing name", e.getMessage());
            throw new RuntimeException("Error while retrieving products containing name", e);
        }
    }

    public Optional<Product> getProductByName(String name) {
        try {
            return Optional.ofNullable(productRepository.findByName(name));
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving product by name", e.getMessage());
            throw new RuntimeException("Error while retrieving product by id", e);
        }
    }

    public Optional<Product> getProductById(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving product by id", e.getMessage());
            throw new RuntimeException("Error while retrieving product by id", e);
        }
    }

    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while creating product", e.getMessage());
            throw new RuntimeException("Error while creating product", e);
        }
    }

    public Product updateProduct(Long id, Product product) {
        try {
            var oldProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
            oldProduct.setDescription(product.getDescription());
            oldProduct.setPlu(product.getPlu());
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setImage(product.getImage());
            return productRepository.save(oldProduct);
        } catch (Exception e) {
            logger.error("Error while updating product", e.getMessage());
            throw new RuntimeException("Error while updating product", e);
        }
    }

    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while deleting product by ID", e.getMessage());
            throw new RuntimeException("Error while deleting product by ID: " + id);
        }
    }
}
