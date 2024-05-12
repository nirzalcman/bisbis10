package com.att.tdp.bisbis10.tests.service_tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repositories.DishRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;
import com.att.tdp.bisbis10.services.DishService;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private DishService dishService;

    private Restaurant restaurant;
    private Dish dish;

    @BeforeEach
    void setUp() {
        restaurant = mock(Restaurant.class); 
        dish = new Dish("Spaghetti", "Tomato and basil", 10.00);
        dish.setRestaurant(restaurant);
    }

    @Test
    void testAddDishSuccess() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        dishService.addDish(dish, 1L);
        verify(dishRepository).save(dish);
        assertEquals(restaurant, dish.getRestaurant());
    }

    @Test
    void testAddDishNonExistentRestaurant() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.addDish(dish, 1L));
    }

    @Test
    void testUpdateDishSuccess() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));

        Dish updatedDish = new Dish("Lasagna", "Cheese and meat", 12.00);
        dishService.updateDish(1L, 1L, updatedDish);

        verify(dishRepository).save(dish);
        assertEquals("Lasagna", dish.getName());
        assertEquals("Cheese and meat", dish.getDescription());
    }

    @Test
    void testUpdateNonExistentDish() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.updateDish(1L, 1L, new Dish()));
    }

    @Test
    void testDeleteDishSuccess() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));

        dishService.deleteDish(1L, 1L);
        verify(dishRepository).delete(dish);
    }

    @Test
    void testDeleteNonExistentDish() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.deleteDish(1L, 1L));
    }

    @Test
    void testGetDishesByRestaurantId() {
        when(dishRepository.findAllByRestaurantId(anyLong())).thenReturn(Arrays.asList(dish));
        List<Dish> dishes = dishService.getDishesByRestaurantId(1L);
        assertFalse(dishes.isEmpty());
        assertEquals(1, dishes.size());
        assertEquals("Spaghetti", dishes.get(0).getName());
    }

    @Test
    void testGetDishesByInvalidRestaurantId() {
        when(dishRepository.findAllByRestaurantId(anyLong())).thenReturn(Arrays.asList());
        List<Dish> dishes = dishService.getDishesByRestaurantId(99L);
        assertTrue(dishes.isEmpty());
    }

    @Test
    void testUpdateDishPartialInfo() {
        Dish partialUpdate = new Dish(null, "Updated Description", null);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(anyLong())).thenReturn(Optional.of(dish));

        dishService.updateDish(1L, 1L, partialUpdate);
        verify(dishRepository).save(dish);
        assertNotNull(dish.getName()); // Ensure original name is retained
        assertEquals("Updated Description", dish.getDescription());
    }

    @Test
    void testDeleteDishWrongRestaurantId() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.deleteDish(99L, 1L));
    }
}
