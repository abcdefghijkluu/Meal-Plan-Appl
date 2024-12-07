package com.recipe_mealplan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recipe_mealplan.Models.RecipeIngredientDTO;
import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Repository.RecipeIngredientRepository;
import com.recipe_mealplan.Repository.RecipesRepository;
import com.recipe_mealplan.entity.RecipeIngredient;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipesRepository recipesRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipesRepository recipesRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipesRepository = recipesRepository;
    }

    // Add a new ingredient to a recipe
    public RecipeIngredient addRecipeIngredient(RecipeIngredientDTO dto) {
        Recipes recipe = recipesRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        RecipeIngredient ingredient = new RecipeIngredient();
        ingredient.setRecipe(recipe);
        ingredient.setIngredientName(dto.getIngredientName());
        ingredient.setQuantity(dto.getQuantity());
        ingredient.setUnit(dto.getUnit());

        return recipeIngredientRepository.save(ingredient);
    }

    // Get all ingredients for a specific recipe
    public List<RecipeIngredientDTO> getIngredientsByRecipeId(Integer recipeId) {
        return recipeIngredientRepository.findByRecipe_Id(recipeId).stream()
                .map(ingredient -> {
                    RecipeIngredientDTO dto = new RecipeIngredientDTO();
                    dto.setRecipeIngredientId(ingredient.getRecipeIngredientId());
                    dto.setRecipeId(recipeId);
                    dto.setIngredientName(ingredient.getIngredientName());
                    dto.setQuantity(ingredient.getQuantity());
                    dto.setUnit(ingredient.getUnit());
                    return dto;
                }).collect(Collectors.toList());
    }

    // Delete an ingredient by ID
    public void deleteIngredient(Long recipeIngredientId) {
        if (!recipeIngredientRepository.existsById(recipeIngredientId)) {
            throw new IllegalArgumentException("Ingredient not found");
        }
        recipeIngredientRepository.deleteById(recipeIngredientId);
    }
}
