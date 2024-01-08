package com.restaurant.restaurantdemo.application.service.product;


import com.restaurant.restaurantdemo.application.service.LoggerService;
import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.boundary.output.jpa.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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

    public Product createProduct(Product product) {

        return productRepository.save(product);

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
