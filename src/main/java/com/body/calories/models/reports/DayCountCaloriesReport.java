package com.body.calories.models.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayCountCaloriesReport {
    private String date;
    private int calories;
    private double normal;
    private  boolean isNormalCalories = true;
    private List<MealReport> mealsReportList;
}
