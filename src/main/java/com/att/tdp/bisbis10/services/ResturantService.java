package com.att.tdp.bisbis10.services;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.att.tdp.bisbis10.entities.Restaurant;
import java.util.*;


@Service
public class ResturantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + id + " not found."));
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisinesContaining(cuisine);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void updateRestaurant( Long id, Restaurant restaurant) {
        Restaurant optionalRestaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + id + " not found."));

        if (restaurant.getName() != null && !restaurant.getName().isEmpty()){
            optionalRestaurant.setName(restaurant.getName());
        }

        if (restaurant.getCuisines() != null && !restaurant.getCuisines().isEmpty()){
            optionalRestaurant.setCuisines(restaurant.getCuisines());
        

        if (restaurant.getisKosher() != null){
            optionalRestaurant.setIsKosher(restaurant.getisKosher());
        }
    }

        restaurantRepository.save(optionalRestaurant);
    }

    public void deleteRestaurant(Long id) {
        Restaurant optionalRestaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + id + " not found."));

        restaurantRepository.delete(optionalRestaurant);
    }


    
}
