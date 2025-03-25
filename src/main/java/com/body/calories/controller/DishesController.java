package com.body.calories.controller;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.models.Dish;
import com.body.calories.service.DishesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dishes")
public class DishesController {
    private final DishesService dishesService;

    public DishesController(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    @PostMapping
    @Transactional
    @Operation (summary = "Add new dish")
    public ResponseEntity<Object> addDish(@Valid @RequestBody Dish dish) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishesService.addDish(dish));
    }

    @GetMapping("/{id}")
    @Operation (summary = "Get dish by id")
    public ResponseEntity<Object> getDishById(@PathVariable long id) throws DishNotFoundException {
        return ResponseEntity.ok(dishesService.getDishById(id));
    }
}
