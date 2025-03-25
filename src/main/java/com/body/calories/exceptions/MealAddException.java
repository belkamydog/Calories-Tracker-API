package com.body.calories.exceptions;

public class MealAddException  extends RuntimeException{
    public MealAddException(String message) {
        super(message);
    }

    public MealAddException() {
        super("Meal Add Error");
    }
}
