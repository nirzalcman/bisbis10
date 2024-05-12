package com.att.tdp.bisbis10.tests.service_tests;


import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.att.tdp.bisbis10.entities.Rating;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.RatingRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;
import com.att.tdp.bisbis10.services.RatingService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RatingService ratingService;

    private Restaurant restaurant;
    private Rating rating;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setNumberOfRatings(0);
        restaurant.setAverageRating(0.0);

        rating = new Rating();
        rating.setRestaurantId(1L);
        rating.setRating(4.0);
    }

    @Test
    void addRating_Successful() {
        when(restaurantRepository.findById(rating.getRestaurantId())).thenReturn(Optional.of(restaurant));

        ratingService.addRating(rating);

        assertEquals(1, restaurant.getNumberOfRatings());
        assertEquals(4.0, restaurant.getAverageRating());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void addRating_RestaurantNotFound() {
        when(restaurantRepository.findById(rating.getRestaurantId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ratingService.addRating(rating));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void addRating_UpdateAverageRating() {
        // Set initial state for the restaurant
        restaurant.setNumberOfRatings(2);
        restaurant.setAverageRating(3.0);

        // New rating to add
        Rating newRating = new Rating();
        newRating.setRestaurantId(1L);
        newRating.setRating(5.0);

        when(restaurantRepository.findById(newRating.getRestaurantId())).thenReturn(Optional.of(restaurant));

        ratingService.addRating(newRating);

        assertEquals(3, restaurant.getNumberOfRatings());
        assertEquals(3.6666666666666665, restaurant.getAverageRating(), 0.0001);
        verify(restaurantRepository).save(restaurant);
    }
}

