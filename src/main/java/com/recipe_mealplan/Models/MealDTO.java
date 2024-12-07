package com.recipe_mealplan.Models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealDTO {

    private Long mealId;
    private Long mealPlanId;
    private Long recipeId; // Can be null if no recipe is selected
    private LocalDate date; // The date of the meal
}