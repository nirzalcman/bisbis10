package com.att.tdp.bisbis10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.services.DishService;

@RestController
public class DishControler {

    @Autowired
    private DishService dishService;

    @GetMapping("/restaurants/{resturantId}/dishes")
    public ResponseEntity<List<Dish>> getDishesByRestaurantId(@PathVariable Long resturantId) {
        return ResponseEntity.ok(dishService.getDishesByRestaurantId(resturantId));
    }

    @PostMapping("/restaurants/{resturantId}/dishes")
    public ResponseEntity<Void> addDish(@PathVariable Long resturantId, @RequestBody Dish dish) {
        dishService.addDish(dish, resturantId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/restaurants/{id}/dishes/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable Long id, @PathVariable Long dishId, @RequestBody Dish dish ) {
        dishService.updateDish(id, dishId , dish);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/restaurants/{resturantId}/dishes/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long resturantId, @PathVariable Long dishId) {
        dishService.deleteDish(resturantId, dishId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

   

    
}
