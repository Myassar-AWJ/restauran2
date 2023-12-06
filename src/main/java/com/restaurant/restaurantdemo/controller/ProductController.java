package com.restaurant.restaurantdemo.controller;

import com.restaurant.restaurantdemo.model.Menu;
import com.restaurant.restaurantdemo.model.Product;
import com.restaurant.restaurantdemo.model.ResponseWithData;
import com.restaurant.restaurantdemo.service.LoggerService;
import com.restaurant.restaurantdemo.service.MenuService;
import com.restaurant.restaurantdemo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseWithData<Product>> getProductBId(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ResponseWithData<Product> response = new ResponseWithData<>("Success", product);
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            // Handle the case where the product is not found
            logger.error("Product not found: ", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>("Success", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while getting product by id", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }


    @GetMapping("/products/name/{productName}")
    public ResponseEntity<ResponseWithData<Product>> getProductByName(@PathVariable String productName) {
        try {
            Product product = productService.getProductByName(productName).orElseThrow(() -> new EntityNotFoundException("Product not found"));

            ResponseWithData<Product> response = new ResponseWithData<>("Success", product);
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            // Handle the case where the product is not found
            logger.error("Product not found: ", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>("Success", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while getting product by id", e.getMessage());
            ResponseWithData<Product> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @PostMapping()
    public ResponseEntity<ResponseWithData<Product>> createProduct(@RequestBody  @Valid Product product , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // Collect error messages and return as a response
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());

                ResponseWithData<Product> errorResponse = new ResponseWithData<>("Failed", errors);
                return ResponseEntity.badRequest().body(errorResponse);
            }

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
    public ResponseEntity<ResponseWithData<Product>> updateProduct(@PathVariable long ProductId, @RequestBody @Valid Product product , BindingResult bindingResult)  {
        try {
            if (bindingResult.hasErrors()) {
                // Collect error messages and return as a response
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());

                ResponseWithData<Product> errorResponse = new ResponseWithData<>("Failed", errors);
                return ResponseEntity.badRequest().body(errorResponse);
            }
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
