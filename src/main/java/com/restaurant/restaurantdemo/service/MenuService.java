package com.restaurant.restaurantdemo.service;


import com.restaurant.restaurantdemo.model.Menu;
import com.restaurant.restaurantdemo.model.Product;
import com.restaurant.restaurantdemo.model.ResponseWithData;
import com.restaurant.restaurantdemo.repository.MenuRepository;


import com.restaurant.restaurantdemo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final LoggerService logger;

    @Autowired
    public MenuService(MenuRepository menuRepository, LoggerService logger, ProductService productService) {
        this.menuRepository = menuRepository;
        this.logger = logger;
        this.productService = productService;
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
            return menuRepository.save(menu);
        } catch (Exception e) {
            logger.error("Error while creating menu", e.getMessage());
            throw new RuntimeException("Error while creating menu", e);
        }
    }

    public Menu updateMEnu(Long id, Menu menu) {
        try {
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


}
