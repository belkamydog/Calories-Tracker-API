package com.body.calories.service;

import com.body.calories.exceptions.DishNotFoundException;
import com.body.calories.exceptions.InvalidDishIds;
import com.body.calories.exceptions.MealAddException;
import com.body.calories.exceptions.UserNotFoundException;
import com.body.calories.models.Dish;
import com.body.calories.models.Meal;
import com.body.calories.models.User;
import com.body.calories.repository.DishesRepository;
import com.body.calories.repository.MealRepository;
import com.body.calories.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository  userRepository;
    private final DishesRepository dishesRepository;

    public MealService(MealRepository mealRepository, UserRepository userRepository, DishesRepository dishesRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.dishesRepository = dishesRepository;
    }

    public Meal addEating(Long userId, List<Long> dishIds) throws DishNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        checkDishesIds(dishIds);
        if (dishIds.isEmpty()) throw new MealAddException("Dishes cannot be empty");
        List<Dish> dishes = dishesRepository.findAllById(dishIds);
        Meal meal = new Meal();
        meal.setUser(user);
        meal.getDishes().addAll(dishes);
        meal.setDishes(dishes);
        return mealRepository.save(meal);
    }

    private void checkDishesIds(List<Long> dishes) throws DishNotFoundException {
        for (Long dish : dishes) {
            if (dish < 0) throw new InvalidDishIds();
            if (!dishesRepository.existsById(dish)) throw new DishNotFoundException(dish);
        }
    }
}
