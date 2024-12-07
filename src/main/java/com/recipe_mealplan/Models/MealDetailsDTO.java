package com.recipe_mealplan.Models;

import java.time.LocalDate;
import java.util.List;

import com.recipe_mealplan.entity.RecipeIngredient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealDetailsDTO {
    private LocalDate date;
    private String recipeName;
    private List<RecipeIngredient> ingredients;
    private String instructions;
}