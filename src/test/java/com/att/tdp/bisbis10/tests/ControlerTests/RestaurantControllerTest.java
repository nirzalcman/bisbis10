package com.att.tdp.bisbis10.tests.ControlerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.*;

import com.att.tdp.bisbis10.controller.ResturantControler;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.services.ResturantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ResturantControler.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResturantService restaurantService;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(); // Create a an resturant object to be used in the tests
        restaurant.setName("Testaurant");
        restaurant.setCuisines(Arrays.asList("nircuisine"));
        restaurant.setIsKosher(true);
    }

    @Test
    void getRestaurants() throws Exception {
        List<Restaurant> allRestaurants = Arrays.asList(restaurant);
        given(restaurantService.getRestaurants()).willReturn(allRestaurants);

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(restaurant.getName())));
    }

    @Test
    void getRestaurantById() throws Exception {
        given(restaurantService.getRestaurantById(anyLong())).willReturn(restaurant);

        mockMvc.perform(get("/restaurants/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restaurant.getName())));
    }


    @Test
    void addRestaurant() throws Exception {
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Testaurant\", \"description\":\"A new test restaurant\", \"cuisine\":\"French\", \"isKosher\":true}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateRestaurant() throws Exception {
        mockMvc.perform(put("/restaurants/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Testaurant\", \"description\":\"An updated test restaurant\", \"cuisine\":\"German\", \"isKosher\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete("/restaurants/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
