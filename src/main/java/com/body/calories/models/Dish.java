package com.body.calories.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "dishes")
public class Dish {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Value can't be less than 0")
    private int calories = 0;

    @Min(value = 0, message = "Value can't be less than 0")
    private int proteins = 0;

    @Min(value = 0, message = "Value can't be less than 0")
    private int fats = 0;

    @Min(value = 0, message = "Value can't be less than 0")
    private int carbohydrates = 0;

}
