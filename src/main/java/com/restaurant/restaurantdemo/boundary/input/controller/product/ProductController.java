package com.restaurant.restaurantdemo.boundary.input.controller.product;

import com.restaurant.restaurantdemo.application.dto.product.ProductDTO;
import com.restaurant.restaurantdemo.application.dto.restaurant.RestaurantDTO;
import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.application.service.ResponseWithData;
import com.restaurant.restaurantdemo.application.service.LoggerService;
import com.restaurant.restaurantdemo.application.service.product.ProductService;
import com.restaurant.restaurantdemo.domain.restaurant.Restaurant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private ModelMapper modelMapper;


    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/products/{productId}")
    public  ProductDTO getProductBId(@PathVariable Long productId) {
            Product product = productService.getProductById(productId);
            return modelMapper.map(product, ProductDTO.class);
    }


    @GetMapping("/products/name/{productName}")
    public ProductDTO getProductByName(@PathVariable String productName) {
            Product product = productService.getProductByName(productName);
            return modelMapper.map(product, ProductDTO.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public  ResponseEntity<ProductDTO>  createProduct(@RequestBody @Valid ProductDTO productDto) {
            Product product =modelMapper.map(productDto, Product.class);
            Product newProduct = productService.createProduct(product);
            ProductDTO newProductDto = modelMapper.map(newProduct, ProductDTO.class);
            return ResponseEntity.ok(newProductDto);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable long productId, @RequestBody @Valid ProductDTO productDto) {

             Product product =modelMapper.map(productDto, Product.class);
             Product updatedProduct = productService.updateProduct(productId, product);

    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
            productService.deleteProductById(productId);
    }

}
