package com.att.tdp.bisbis10.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.DishRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    

    public void addDish(Dish dish , Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + restaurantId + " not found."));
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);// add dish to the restaurant dishes list
        
    }
    public void updateDish(Long resturantId, Long dishId ,Dish dish) {
        Restaurant restaurant = restaurantRepository.findById(resturantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + resturantId + " not found."));

        Dish optionalDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id " + dishId + " not found."));

        if (dish.getName() != null && !dish.getName().isEmpty()) {
            optionalDish.setName(dish.getName());
        }
        if (dish.getDescription() != null && !dish.getDescription().isEmpty()) {
            optionalDish.setDescription(dish.getDescription());
        }
        if (dish.getPrice() != null && dish.getPrice() > 0){
            optionalDish.setPrice(dish.getPrice());
        }
        dishRepository.save(optionalDish);
    }

    public void deleteDish(Long id, Long dishId) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + id + " not found."));

        Dish optionalDish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id " + id + " not found."));

        dishRepository.delete(optionalDish);
    }

    public List<Dish> getDishesByRestaurantId(Long id) {
        return dishRepository.findAllByRestaurantId(id);
    }
    
}
