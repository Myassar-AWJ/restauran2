package com.restaurant.restaurantdemo.boundary.input.controller.menu;

import com.restaurant.restaurantdemo.application.dto.menu.MenuDocument;
import com.restaurant.restaurantdemo.domain.menu.Menu;
import java.util.List;
import java.util.stream.Collectors;

public class MenuDocumentFactory {

    static List<MenuDocument> toMenusDocument(List<Menu> menus) {
        menus.stream()
                 .map(MenuDocumentFactory::toMenuDocument)
                 .collect(Collectors.toList());
    }

}
