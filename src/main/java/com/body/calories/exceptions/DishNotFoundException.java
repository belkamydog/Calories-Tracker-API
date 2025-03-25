package com.body.calories.exceptions;

public class DishNotFoundException  extends Exception{
    public DishNotFoundException(Long dishId) {
        super("Dish with id " + dishId + " not found");
    }
}
