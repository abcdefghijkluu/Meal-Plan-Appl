package com.recipe_mealplan.Repsoitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe_mealplan.Models.Recipes;

public interface RecipeRepository extends JpaRepository <Recipes, Integer>{
    public Recipes findById(int id);


}
