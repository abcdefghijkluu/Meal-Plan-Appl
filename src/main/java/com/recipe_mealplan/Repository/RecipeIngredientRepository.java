package com.recipe_mealplan.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.entity.RecipeIngredient;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientRepository, Long> {
    List<RecipeIngredient> findByRecipe_Id(Integer recipeId);
    List<RecipeIngredient> findByRecipeId(Integer recipeId);
}
