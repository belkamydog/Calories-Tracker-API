package com.body.calories.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String email;

    @Min(value = 0, message = "Age can't be less then zero")
    @Max(value = 130, message = "Age can't be more then 130")
    private int age = 0;

    @Min(value = 0, message = "Weight can't be less then zero")
    @Max(value = 300, message = "Weight can't be more then 300")
    private int weight = 0;

    @Min(value = 0, message = "Height can't be less then zero")
    private int height = 0;

    @Column(nullable = false)
    private Target target = Target.Maintenance;

    public enum Target {
        WeightLoss, Maintenance, MassGain
    }

    public double calculateBMR() {
        double bmr;
        if (target ==  Target.WeightLoss)
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        else if (target == Target.Maintenance)
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        else
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age) + 500;
        return bmr;
    }
}
