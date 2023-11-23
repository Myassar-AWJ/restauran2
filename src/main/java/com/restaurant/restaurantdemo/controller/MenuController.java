package com.restaurant.restaurantdemo.controller;


import com.restaurant.restaurantdemo.model.Menu;
import com.restaurant.restaurantdemo.model.ResponseWithData;
import com.restaurant.restaurantdemo.service.LoggerService;
import com.restaurant.restaurantdemo.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;
    private final LoggerService logger;

    @Autowired
    public MenuController(MenuService menuService, LoggerService logger) {
        this.menuService = menuService;
        this.logger = logger;
    }


    @GetMapping
    public ResponseEntity<ResponseWithData<List<Menu>>> getAllMenus() {
        try {
            List<Menu> menus = menuService.getAllMenus();
            ResponseWithData<List<Menu>> response = new ResponseWithData<>("Success", menus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While Getting All Menus from cont", e.getMessage());
            ResponseWithData<List<Menu>> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{MenuId}")
    public ResponseEntity<ResponseWithData<Menu>> getMenuById(@PathVariable Long MenuId) {
        try {
            Menu menu = menuService.getMenuById(MenuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
            ResponseWithData<Menu> response = new ResponseWithData<>("Success", menu);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While Getting the Menu in Cont", e.getMessage());
            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
//    @GetMapping("/{MenuId}/products")
//    public ResponseEntity<ResponseWithData<Menu>> findByIdWithProducts(@PathVariable Long MenuId) {
//        try {
//            Menu menu = menuService.findByIdWithProducts(MenuId);
//            ResponseWithData<Menu> response = new ResponseWithData<>("Success", menu);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            logger.error("Error While Getting the Menu in Cont", e.getMessage());
//            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
//    }



    @PostMapping()
    public ResponseEntity<ResponseWithData<Menu>> createMenu(@RequestBody Menu menu) {
        try {
            Menu newMenu = menuService.createMenu(menu);
            ResponseWithData<Menu> response = new ResponseWithData<>("Success", newMenu);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While Creating Menu in Cont", e.getMessage());
            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{MenuId}")
    public ResponseEntity<ResponseWithData<Menu>> updateMenu(@PathVariable Long MenuId, @RequestBody Menu menu) {
        try {
            Menu newMenu = menuService.updateMEnu(MenuId, menu);
            ResponseWithData<Menu> response = new ResponseWithData<>("Success", newMenu);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While updating Menu in Cont", e.getMessage());
            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{MenuId}")
    public ResponseEntity<ResponseWithData<Void>> deleteMenu(@PathVariable Long MenuId) {
        try {
            menuService.deleteMenu(MenuId);
            ResponseWithData<Void> response = new ResponseWithData<>("Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While Deleteing the Menu", e.getMessage());
            ResponseWithData<Void> errorRespose = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespose);
        }
    }

    //    @PutMapping("/linkItemsToMenu/{MenuId}")
//    public ResponseEntity<ResponseWithData<Menu>> linkItemsToMenu(@PathVariable Long MenuId,@RequestBody List<Long> productsId){
//        try{
//            Menu menu=menuService.linkItemsWithMenu(MenuId,productsId);
//            ResponseWithData<Menu> response=new ResponseWithData<>("Success",menu);
//            return  ResponseEntity.ok(response);
//        }catch (Exception e){
//            logger.error("Error While link Items to Menu",e.getMessage());
//            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
//            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
//    }
    @PutMapping("/linkItemsToMenu/{MenuId}")
    public ResponseEntity<ResponseWithData<Menu>> linkItemsToMenu(@PathVariable Long MenuId, @RequestBody Map<String, List<Long>> request) {
        try {
            List<Long> productsId = request.get("productsId");
            // Now you have the list of product IDs from the "productsId" field in the request.

            Menu menu = menuService.linkItemsWithMenu(MenuId, productsId);
            ResponseWithData<Menu> response = new ResponseWithData<>("Success", menu);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error While linking Items to Menu", e.getMessage());
            ResponseWithData<Menu> errorResponse = new ResponseWithData<>(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
