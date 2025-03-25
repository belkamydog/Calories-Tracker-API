package com.body.calories.controller;


import com.body.calories.exceptions.InvalidDishIds;
import com.body.calories.exceptions.MealAddException;
import com.body.calories.exceptions.UserNotFoundException;
import com.body.calories.models.Meal;
import com.body.calories.service.MealService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
class MealControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealService mealService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addMeal_ShouldReturnCreatedMeal() throws Exception {
        List<Long> dishIds = Arrays.asList(1L, 2L, 3L);
        Meal createdMeal = new Meal();
        createdMeal.setId(1);
        Mockito.when(mealService.addEating(1L, dishIds)).thenReturn(createdMeal);
        mockMvc.perform(post("/meals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishIds)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void addMeal_ShouldReturnBadRequest_WhenEmptyDishList() throws Exception {
        List<Long> emptyDishIds = List.of();
        Mockito.when(mealService.addEating(1L, emptyDishIds)).thenThrow(new MealAddException());
        mockMvc.perform(post("/meals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyDishIds)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMeal_ShouldReturnNotFound_WhenUserNotExists() throws Exception {
        List<Long> dishIds = Arrays.asList(1L, 2L);
        Mockito.when(mealService.addEating(99L, dishIds))
                .thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(post("/meals/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishIds)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addMeal_ShouldReturnBadRequest_WhenInvalidDishId() throws Exception {
        List<Long> invalidDishIds = Arrays.asList(-100L, -200L);
        Mockito.when(mealService.addEating(1L, invalidDishIds)).thenThrow(InvalidDishIds.class);
        mockMvc.perform(post("/meals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDishIds)))
                .andExpect(status().isBadRequest());
    }
}