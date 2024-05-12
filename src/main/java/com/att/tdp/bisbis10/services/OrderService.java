package com.att.tdp.bisbis10.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Order;
import com.att.tdp.bisbis10.entities.OrderItem;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.OrderRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Order addOrder(Order order) {
        System.out.println(order.getOrderItems().size());
        Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + order.getRestaurantId() + " not found."));
        
        List<Long> validDishIds = restaurant.getDishes().stream().map(Dish::getId).collect(Collectors.toList());

        for (OrderItem item : order.getOrderItems()) {
            if (!validDishIds.contains(item.getDishId())) {
                throw new EntityNotFoundException("Dish with id " + item.getDishId() + " does not exist in restaurant with id " + order.getRestaurantId());
            }
            item.setOrder(order);  // Link the item to the order
        }

        order.setRestaurant(restaurant);
        return orderRepository.save(order);
    }
    
}
