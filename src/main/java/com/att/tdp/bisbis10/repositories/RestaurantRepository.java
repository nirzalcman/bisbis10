package com.att.tdp.bisbis10.repositories;

import com.att.tdp.bisbis10.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCuisinesContaining(String cuisine);
}