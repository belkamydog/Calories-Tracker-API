package com.body.calories.models.reports;

import com.body.calories.models.Meal;
import lombok.Data;

@Data
public class MealReport {
    private Integer mealId;
    private String date;
    private Integer calories = 0;

    public MealReport(Meal meal) {
        this.mealId = meal.getId();
        this.date = meal.getDate();
        this.calories = meal.getCountCaloriesOfTheMeal();
    }
}