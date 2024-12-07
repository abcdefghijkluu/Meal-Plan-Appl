package com.recipe_mealplan.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.entity.RecipeIngredient;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    List<Recipes> findByCategoryIn(List<String> category);  // Changed from categories to mealTypes
    List<Recipes> findByFoodTypeIn(List<String> foodType);  // No change here, but ensure it matches the entity field name
    public List<Recipes> findByFoodType(String foodType);
    List<Recipes> findByCategoryInAndFoodTypeIn(List<String> category, List<String> foodType);   // Changed from categories to mealTypes
    List<RecipeIngredient> findByRecipe_Id(Integer recipeId);
}
