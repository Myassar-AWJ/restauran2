package com.restaurant.restaurantdemo.boundary.input.controller.product;

import com.restaurant.restaurantdemo.application.RestaurantFacade;
import com.restaurant.restaurantdemo.application.dto.product.ProductDocument;
import com.restaurant.restaurantdemo.application.service.product.CreateProductCommand;
import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.application.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final ModelMapper modelMapper;

    private final RestaurantFacade restaurantFacade;


    @GetMapping
    public List<ProductDocument> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDocument.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/products/{productId}")
    public ProductDocument getProductBId(@PathVariable Long productId) {
            Product product = productService.getProductById(productId);
            return modelMapper.map(product, ProductDocument.class);
    }


    @GetMapping("/products/name/{productName}")
    public ProductDocument getProductByName(@PathVariable String productName) {
            Product product = productService.getProductByName(productName);
            return modelMapper.map(product, ProductDocument.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long createProduct(@RequestBody @Valid ProductDocument productDocument) {
            var command = modelMapper.map(productDocument, CreateProductCommand.class);
            return restaurantFacade.handle(command);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable long productId, @RequestBody @Valid ProductDocument productDocument) {

             Product product =modelMapper.map(productDocument, Product.class);
             Product updatedProduct = productService.updateProduct(productId, product);

    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
            productService.deleteProductById(productId);
    }

}
