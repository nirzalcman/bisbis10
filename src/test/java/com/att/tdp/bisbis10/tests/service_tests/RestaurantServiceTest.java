package com.att.tdp.bisbis10.tests.service_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;
import com.att.tdp.bisbis10.services.ResturantService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ResturantService resturantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRestaurants() {
        List<Restaurant> expectedRestaurants = Arrays.asList(new Restaurant(), new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(expectedRestaurants);

        List<Restaurant> actualRestaurants = resturantService.getRestaurants();

        assertNotNull(actualRestaurants);
        assertEquals(expectedRestaurants.size(), actualRestaurants.size());
    }

    @Test
    void testGetRestaurantById() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        Restaurant found = resturantService.getRestaurantById(id);

        assertNotNull(found);
    }

    @Test
    void testGetRestaurantByIdNotFound() {
        Long id = 2L;
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resturantService.getRestaurantById(id));
    }

    @Test
    void testGetRestaurantsByCuisine() {
        String cuisine = "Italian";
        List<Restaurant> expectedRestaurants = Arrays.asList(new Restaurant());
        when(restaurantRepository.findByCuisinesContaining(cuisine)).thenReturn(expectedRestaurants);

        List<Restaurant> actualRestaurants = resturantService.getRestaurantsByCuisine(cuisine);

        assertEquals(expectedRestaurants, actualRestaurants);
    }

    @Test
    void testAddRestaurant() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant savedRestaurant = resturantService.addRestaurant(restaurant);

        assertNotNull(savedRestaurant);
    }

    @Test
    void testUpdateRestaurant() {
        Long id = 3L;
        Restaurant existingRestaurant = new Restaurant();
        Restaurant updatedInfo = new Restaurant();
        updatedInfo.setName("New Name");
        updatedInfo.setCuisines(Arrays.asList("New Cuisines"));
        updatedInfo.setIsKosher(true);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(existingRestaurant);

        resturantService.updateRestaurant(id, updatedInfo);

        assertNotNull(existingRestaurant);
        assertEquals("New Name", existingRestaurant.getName());
        assertEquals(Arrays.asList("New Cuisines"), existingRestaurant.getCuisines());
        assertTrue(existingRestaurant.getisKosher());
    }

    @Test
    void testDeleteRestaurant() {
        Long id = 4L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        doNothing().when(restaurantRepository).delete(restaurant);

        resturantService.deleteRestaurant(id);

        verify(restaurantRepository).delete(restaurant);
    }
}
