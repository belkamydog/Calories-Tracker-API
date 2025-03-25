package com.body.calories.controller;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @Transactional
    @PostMapping("/{user_id}")
    @Operation (summary = "Add meal for user")
    public ResponseEntity<?> addMeal(@RequestBody List<Long> dishes, @PathVariable long user_id) throws DishNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.addEating(user_id, dishes));
    }
}
