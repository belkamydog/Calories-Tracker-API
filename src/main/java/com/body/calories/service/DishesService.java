package com.body.calories.service;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.models.Dish;
import com.body.calories.repository.DishesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishesService {
    private final DishesRepository dishesRepository;

    public DishesService(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    public Dish addDish(Dish dish) {
        return dishesRepository.save(dish);
    }

    public Dish getDishById(Long dishId) throws DishNotFoundException {
        Optional<Dish> dish = dishesRepository.findById(dishId);
        if (dish.isEmpty()) throw new DishNotFoundException(dishId);
        return dishesRepository.findById(dishId).get();
    }
}
