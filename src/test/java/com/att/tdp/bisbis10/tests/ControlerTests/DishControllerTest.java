package com.att.tdp.bisbis10.tests.ControlerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.Matchers.*;

import com.att.tdp.bisbis10.controller.DishControler;
import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.services.DishService;
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

@WebMvcTest(DishControler.class)
public class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishService dishService;

    private Dish dish;

    @BeforeEach
    void setUp() {
        dish = new Dish();
        dish.setId(1L);
        dish.setName("Margherita Pizza");
        dish.setDescription("Classic with tomato sauce and cheese");
        dish.setPrice(15.00);
    }

    @Test
    void getDishesByRestaurantId() throws Exception {
        List<Dish> allDishes = Arrays.asList(dish);
        given(dishService.getDishesByRestaurantId(anyLong())).willReturn(allDishes);

        mockMvc.perform(get("/restaurants/{resturantId}/dishes", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(dish.getName())));
    }

    @Test
    void addDish() throws Exception {
        mockMvc.perform(post("/restaurants/{resturantId}/dishes", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Dish\", \"description\": \"New description\", \"price\": 20.00}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateDish() throws Exception {
        mockMvc.perform(put("/restaurants/{id}/dishes/{dishId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Dish\", \"description\": \"Updated description\", \"price\": 25.00}"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(delete("/restaurants/{resturantId}/dishes/{dishId}", 1, 1))
                .andExpect(status().isNoContent());
        verify(dishService).deleteDish(1L, 1L);
    }
}

