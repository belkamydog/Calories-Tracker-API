package com.body.calories.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String date = LocalDate.now().toString();

    @ManyToOne (fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "meal_dishes",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dishes_id")
    )
    private List<Dish> dishes = new ArrayList<>();

    public int getCountCaloriesOfTheMeal() {
        return dishes.stream().mapToInt(Dish::getCalories).sum();
    }
}
