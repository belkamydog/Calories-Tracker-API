package com.body.calories.controller;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.exceptions.UserNotFoundException;
import com.body.calories.models.Dish;
import com.body.calories.models.User;
import com.body.calories.service.DishesService;
import com.body.calories.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (UserController.class)
class UserControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private final User testUser = new User(1L, "Test User", "test@example.com",
            (short) 30, 70, 175, User.Target.Maintenance);

	@Test
	void createUser_ShouldReturnCreatedUser() throws Exception {
		User newUser = new User(1L, "New User", "new@example.com",
                (short) 25, 65,  170, User.Target.WeightLoss);
		User savedUser = new User(1L, "New User", "new@example.com",
				(short) 25, 65,  170, User.Target.WeightLoss);
		Mockito.when(userService.createUser(newUser)).thenReturn(savedUser);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.firstName").value("New User"))
				.andExpect(jsonPath("$.email").value("new@example.com"));
	}

	@Test
	void getUser_ShouldReturnUser() throws Exception {
		Mockito.when(userService.findUserById(1L)).thenReturn(testUser);
		mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.firstName").value("Test User"))
				.andExpect(jsonPath("$.email").value("test@example.com"));
	}

	@Test
	void getUser_ShouldReturnNotFound_WhenUserNotExists() throws Exception {
		Mockito.when(userService.findUserById(99L)).thenThrow(UserNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/users/99"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createUser_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
		User invalidUser = new User((Long) null, "Bad User", "bad@example.com",
                (short) -1, -10, -5, User.Target.WeightLoss);

		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(invalidUser)))
				.andExpect(status().isBadRequest());
	}

}
