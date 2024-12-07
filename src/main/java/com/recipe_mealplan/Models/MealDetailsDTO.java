package com.recipe_mealplan.Models;


import java.time.LocalDate;
import java.util.List;

import com.recipe_mealplan.entity.RecipeIngredient;

import lombok.Getter;
import lombok.Setter;


public class MealDetailsDTO {
    private LocalDate date;
    private String recipeName;
    private List<RecipeIngredient> ingredients;
    private String instructions;
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
}