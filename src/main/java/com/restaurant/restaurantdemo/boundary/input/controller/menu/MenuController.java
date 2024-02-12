package com.restaurant.restaurantdemo.boundary.input.controller.menu;

import com.restaurant.restaurantdemo.application.RestaurantFacade;
import com.restaurant.restaurantdemo.application.dto.menu.MenuDocument;

import com.restaurant.restaurantdemo.application.service.menu.CreateMenuCommand;
import com.restaurant.restaurantdemo.domain.menu.Menu;
import com.restaurant.restaurantdemo.application.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static com.restaurant.restaurantdemo.boundary.input.controller.menu.MenuDocumentFactory.toMenusDocument;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    @Autowired
    private final MenuService menuService;
    @Autowired
    private final ModelMapper modelMapper;

    private final RestaurantFacade restaurantFacade;


    @GetMapping
    public List<MenuDocument> getAllMenus() {

        List<Menu> menus = menuService.getAllMenus();
        return toMenusDocument(menus);
        // we usually create class like MenuDocumentFactory with static methods toMenuDocument and toMenusDocument
        return menus.stream()
                .map(menu -> modelMapper.map(menu, MenuDocument.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/{menuId}")
    public MenuDocument getMenuById(@PathVariable Long menuId) {

        Menu menu = menuService.getMenuById(menuId);
        // log.info("Getting menu: {}", menu);
        log.info("getMenuById");
        log.info(menu.toString());
        return modelMapper.map(menu, MenuDocument.class);

    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long createMenu(@RequestBody @Valid MenuDocument menuDocument, BindingResult bindingResult) { // not used parameter
        CreateMenuCommand createMenuCommand = modelMapper.map(menuDocument, CreateMenuCommand.class);
        return restaurantFacade.handle(createMenuCommand);
        // no need for ResponseEntity if you use @RestController

    }

    @PutMapping("/{menuId}")
    public void updateMenu(@PathVariable Long menuId, @RequestBody @Valid MenuDocument menuDocument) {

        Menu menu = modelMapper.map(menuDocument, Menu.class);
        Menu newMenu = menuService.updateMEnu(menuId, menu);

    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void  deleteMenu(@PathVariable Long MenuId) {
        menuService.deleteMenu(MenuId);
    }

    @PutMapping("/linkItemsToMenu/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkItemsToMenu(@PathVariable Long menuId, @RequestBody Map<String, List<Long>> request) {
        List<Long> productsId = request.get("productsId");
        Menu menu = menuService.linkItemsWithMenu(menuId, productsId);
    }

    @PutMapping("/unlinkItemsToMenu/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkItemsWithMenu(@PathVariable Long menuId, @RequestBody Map<String, List<Long>> request) {
        List<Long> productsId = request.get("productsId");
        Menu menu = menuService.unlinkItemsWithMenu(menuId, productsId);
    }


}
