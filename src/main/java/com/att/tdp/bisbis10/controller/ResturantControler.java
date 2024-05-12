package com.att.tdp.bisbis10.controller;

import java.util.List;
import com.att.tdp.bisbis10.entities.Restaurant;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.services.ResturantService;

@RestController
public class ResturantControler {

    @Autowired
    private ResturantService resturantService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        return ResponseEntity.ok(resturantService.getRestaurants());
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(resturantService.getRestaurantById(id));
    }

    @GetMapping("/restaurants?cuisine={cuisine}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@RequestParam String cuisine) {
        return ResponseEntity.ok(resturantService.getRestaurantsByCuisine(cuisine));
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Void> addRestaurant(@RequestBody Restaurant restaurant) {
        resturantService.addRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        resturantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        resturantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }





    
}
