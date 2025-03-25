package com.body.calories.repository;

import com.body.calories.models.Meal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findMealsByUserIdAndDate(Long user_id, String date);
    Page <Meal> findMealsByUserId(Long user_id, Pageable pageable);
}
