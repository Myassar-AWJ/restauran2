package com.restaurant.restaurantdemo.application.service.menu;


import com.restaurant.restaurantdemo.application.service.LoggerService;
import com.restaurant.restaurantdemo.application.service.product.ProductService;
import com.restaurant.restaurantdemo.domain.menu.Menu;
import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.boundary.output.jpa.menu.MenuRepository;


import com.restaurant.restaurantdemo.boundary.output.jpa.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final LoggerService logger;

    @Autowired
    public MenuService(MenuRepository menuRepository, LoggerService logger, ProductService productService ,ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.logger = logger;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public List<Menu> getAllMenus() {
        try {
            return menuRepository.findAll();
        } catch (Exception e) {
            // Log the exception
            logger.error("Error while retrieving all manus", e.getMessage());
            throw new RuntimeException("Error while retrieving all menus", e);
        }
    }

    public Optional<Menu> getMenuById(Long id) {
        try {
         return menuRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while retrieving menu by id", e.getMessage());
            throw new RuntimeException("Error while retrieving menu by id", e);
        }
    }



    public Menu createMenu(Menu menu) {
        try {
            if (menuRepository.findByName(menu.getName()) != null) {
                throw new IllegalStateException("A Menu with the name '" + menu.getName() + "' already exists.");
            }
            return menuRepository.save(menu);
        } catch (Exception e) {
            logger.error("Error while creating menu", e.getMessage());
            throw new RuntimeException("Error while creating menu", e);
        }
    }

    public Menu updateMEnu(Long id, Menu menu) {
        try {
            if (menuRepository.findByNameAndIdNot(menu.getName(),id) != null) {
                throw new IllegalStateException("A Menu with the name '" + menu.getName() + "' already exists.");
            }
            Menu oldMenu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu Not Found"));
            oldMenu.setName(menu.getName());
            return menuRepository.save(oldMenu);

        } catch (Exception e) {
            logger.error("Error While Updating menu", e.getMessage());
            throw new RuntimeException("Error while Updating menu", e);
        }
    }

    public void deleteMenu(Long id) {
        try {
            menuRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error while deleting menu", e.getMessage());
            throw new RuntimeException("Error while deleting menu");
        }
    }

    @Transactional
    public Menu linkItemsWithMenu(Long menuId, List<Long> productsId) {
        try {
            Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));

            for (Long id : productsId) {
                Product product = productService.getProductById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
                menu.getProducts().add(product);
            }

            return menuRepository.save(menu);
        } catch (Exception e) {
            logger.error("Error While Link Items to Menu", e.getMessage());
            throw new RuntimeException("Error While Link Items to Menu");
        }
    }

    @Transactional
    public Menu unlinkItemsWithMenu(Long menuId, List<Long> productsId) {
        try {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id " + menuId));

            for (Long id : productsId) {
                Product product = productService.getProductById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
                menu.getProducts().remove(product);
            }

            return  menuRepository.save(menu);
        } catch (Exception e) {
            logger.error("Error While Unlink Items to Menu", e.getMessage());
            throw new RuntimeException("Error While Unlink Items to Menu");
        }
    }



}