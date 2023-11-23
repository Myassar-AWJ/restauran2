package com.restaurant.restaurantdemo.controller;

import com.restaurant.restaurantdemo.model.Product;
import com.restaurant.restaurantdemo.model.ResponseWithData;
import com.restaurant.restaurantdemo.service.LoggerService;
import com.restaurant.restaurantdemo.service.MenuService;
import com.restaurant.restaurantdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final LoggerService logger;


    @Autowired
    public ProductController(ProductService productService, LoggerService logger) {
        this.productService = productService;
        this.logger = logger;
    }


    @GetMapping
    public ResponseEntity<ResponseWithData<List<Product>>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            ResponseWithData<List<Product>> response = new ResponseWithData<>("Success", products);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the exception
            logger.error("Error while getting all products", e.getMessage());
            ResponseWithData<List<Product>> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @PostMapping()
    public ResponseEntity<ResponseWithData<Product>> createProduct(@RequestBody Product product) {
        try {
            var newProduct = productService.createProduct(product);
            ResponseWithData<Product> response = new ResponseWithData<>("Success", newProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while create product cont", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{ProductId}")
    public ResponseEntity<ResponseWithData<Product>> updateProduct(@PathVariable long ProductId,@RequestBody Product product) {
        try {
            var updatedProduct = productService.updateProduct(ProductId, product);
            ResponseWithData<Product> response = new ResponseWithData<>("Success", updatedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while updating product", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{ProductId}")
    public ResponseEntity<ResponseWithData<Void>> deleteProduct(@PathVariable Long ProductId) {
        try {
            productService.deleteProductById(ProductId);
            ResponseWithData<Void> response = new ResponseWithData<>("Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while deleting product in cont", e.getMessage());
            ResponseWithData<Void> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

}
