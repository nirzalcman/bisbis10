package com.att.tdp.bisbis10.repositories;

import com.att.tdp.bisbis10.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByRestaurantId(Long restaurantId);
}
 
