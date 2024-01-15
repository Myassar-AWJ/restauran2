package com.restaurant.restaurantdemo.application.service.menu;


import com.restaurant.restaurantdemo.application.service.product.ProductService;
import com.restaurant.restaurantdemo.domain.menu.Menu;
import com.restaurant.restaurantdemo.domain.prodact.Product;
import com.restaurant.restaurantdemo.boundary.output.jpa.menu.MenuRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    @Autowired
    private final MenuRepository menuRepository;
    @Autowired
    private final ProductService productService;


    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {

        return menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found"));

    }


    public Menu createMenu(Menu menu) {

        if (menuRepository.findByName(menu.getName()) != null) {
            throw new IllegalStateException("A Menu with the name '" + menu.getName() + "' already exists.");
        }
        return menuRepository.save(menu);

    }

    public Menu updateMEnu(Long id, Menu menu) {
        if (menuRepository.findByNameAndIdNot(menu.getName(), id) != null) {
            throw new IllegalStateException("A Menu with the name '" + menu.getName() + "' already exists.");
        }
        Menu oldMenu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu Not Found"));
        oldMenu.setName(menu.getName());
        return menuRepository.save(oldMenu);

    }

    public void deleteMenu(Long id) {

        menuRepository.deleteById(id);

    }

    @Transactional
    public Menu linkItemsWithMenu(Long menuId, List<Long> productsId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));

        for (Long id : productsId) {
            Product product = productService.getProductById(id);
            menu.getProducts().add(product);
        }

        return menuRepository.save(menu);

    }

    @Transactional
    public Menu unlinkItemsWithMenu(Long menuId, List<Long> productsId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id " + menuId));

        for (Long id : productsId) {
            Product product = productService.getProductById(id);
            menu.getProducts().remove(product);
        }

        return menuRepository.save(menu);

    }


}
