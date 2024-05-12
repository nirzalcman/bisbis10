package com.att.tdp.bisbis10.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.entities.Rating;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.RatingRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void addRating(Rating rating) {
        Restaurant restaurant = restaurantRepository.findById(rating.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + rating.getRestaurantId() + " not found."));
        
        int new_number_ratings = restaurant.getNumberOfRatings() +1;
        double average_rating = restaurant.getAverageRating();
        restaurant.setNumberOfRatings(new_number_ratings);
        restaurant.setAverageRating((average_rating + rating.getRating()) / (new_number_ratings));
        restaurantRepository.save(restaurant);
    }

    
}
