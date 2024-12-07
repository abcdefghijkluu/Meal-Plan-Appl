package com.recipe_mealplan.recipe_app.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDTO {
    private Long recipeIngredientId;
    private Integer recipeId;
    private String ingredientName;
    private Double quantity;
    private String unit;
}