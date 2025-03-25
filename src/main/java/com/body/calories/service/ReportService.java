package com.body.calories.service;

import com.body.calories.models.Meal;
import com.body.calories.models.User;
import com.body.calories.models.reports.DayCountCaloriesReport;
import com.body.calories.models.reports.MealReport;
import com.body.calories.repository.DishesRepository;
import com.body.calories.repository.MealRepository;
import com.body.calories.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public ReportService(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public DayCountCaloriesReport getDayReport(String date, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Meal> dayMealsList = mealRepository.findMealsByUserIdAndDate(userId, date);
        List<MealReport> mealReportList = dayMealsList.stream()
                .map(MealReport::new)
                .collect(Collectors.toList());
        int totalDayCalories = mealReportList.stream()
                .mapToInt(MealReport::getCalories)
                .sum();
        double normalCalories = user.calculateBMR();
        boolean isNormal = Double.compare(totalDayCalories, normalCalories) <= 0;
        return  new DayCountCaloriesReport(date, totalDayCalories, normalCalories, isNormal, mealReportList);
    }

    public Page <Meal> getHistoryReport(Long userId, Pageable pageable) {
       return mealRepository.findMealsByUserId(userId, pageable);
    }

}
