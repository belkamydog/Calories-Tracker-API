package com.body.calories.controller;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.models.Dish;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.body.calories.service.DishesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest (DishesController.class)
class DishControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DishesService dishesService;

	@Autowired
	private ObjectMapper objectMapper;

	private final Dish testDish = new Dish(1, "Test Dish", 300, 20, 10, 30);

	@Test
	void addDish_ShouldReturnCreatedDish() throws Exception {
		Dish newDish = new Dish(1, "New Dish", 250, 15, 8, 25);
		Dish savedDish = new Dish(1, "New Dish", 250, 15, 8, 25);

		Mockito.when(dishesService.addDish(newDish)).thenReturn(savedDish);

		mockMvc.perform(post("/dishes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDish)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("New Dish"));
	}

	@Test
	void getDishById_ShouldReturnDish() throws Exception {
		Mockito.when(dishesService.getDishById(1L)).thenReturn(testDish);
		mockMvc.perform(get("/dishes/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Test Dish"));
	}

	@Test
	void getDishById_ShouldReturnNotFound_WhenDishNotExists() throws Exception {
		Mockito.when(dishesService.getDishById(99L))
				.thenThrow(new DishNotFoundException(99L));
		mockMvc.perform(get("/dishes/99")).andExpect(status().isNotFound());
	}

}
