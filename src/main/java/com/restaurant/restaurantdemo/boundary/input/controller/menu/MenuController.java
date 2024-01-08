package com.restaurant.restaurantdemo.boundary.input.controller.menu;

import com.restaurant.restaurantdemo.application.dto.menu.MenuDTO;

import com.restaurant.restaurantdemo.domain.menu.Menu;
import com.restaurant.restaurantdemo.application.service.ResponseWithData;
import com.restaurant.restaurantdemo.application.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    @Autowired
    private final MenuService menuService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<MenuDTO> getAllMenus() {

        List<Menu> menus = menuService.getAllMenus();
        return menus.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/{menuId}")
    public MenuDTO getMenuById(@PathVariable Long menuId) {

        Menu menu = menuService.getMenuById(menuId);
        return modelMapper.map(menu, MenuDTO.class);

    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createMenu(@RequestBody @Valid MenuDTO menuDto, BindingResult bindingResult) {
        Menu menu = modelMapper.map(menuDto, Menu.class);
        Menu newMenu = menuService.createMenu(menu);
        return ResponseEntity.ok(newMenu.getId());

    }

    @PutMapping("/{menuId}")
    public void updateMenu(@PathVariable Long menuId, @RequestBody @Valid MenuDTO menuDto) {

        Menu menu = modelMapper.map(menuDto, Menu.class);
        Menu newMenu = menuService.updateMEnu(menuId, menu);

    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ResponseWithData<Void>> deleteMenu(@PathVariable Long MenuId) {

        menuService.deleteMenu(MenuId);
        ResponseWithData<Void> response = new ResponseWithData<>("Success");
        return ResponseEntity.ok(response);

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
