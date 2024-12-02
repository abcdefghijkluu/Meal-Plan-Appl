package com.recipe_mealplan.Repsoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe_mealplan.Models.Recipes;

public interface RecipesRespository extends JpaRepository<Recipes, Integer>{
    List<Recipes> findByCategoryIn(List<String> category);  // Changed from categories to mealTypes
    List<Recipes> findByFoodTypeIn(List<String> foodType);  // No change here, but ensure it matches the entity field name
    public List<Recipes> findByFoodType(String foodType);
    List<Recipes> findByCategoryInAndFoodTypeIn(List<String> category, List<String> foodType);   // Changed from categories to mealTypes
}
