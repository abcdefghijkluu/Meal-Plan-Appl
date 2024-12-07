package com.recipe_mealplan.recipe_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recipe_mealplan.recipe_app.Models.GroceryItemDTO;
import com.recipe_mealplan.recipe_app.entity.GroceryItem;
import com.recipe_mealplan.recipe_app.entity.MealPlan;
import com.recipe_mealplan.recipe_app.entity.RecipeIngredient;
import com.recipe_mealplan.recipe_app.repository.GroceryItemRepository;
import com.recipe_mealplan.recipe_app.repository.MealPlanRepository;
import com.recipe_mealplan.recipe_app.repository.RecipeIngredientRepository;


@Service
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;
    private final MealPlanRepository mealPlanRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository,
                              MealPlanRepository mealPlanRepository,
                              RecipeIngredientRepository recipeIngredientRepository) {
        this.groceryItemRepository = groceryItemRepository;
        this.mealPlanRepository = mealPlanRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    // Create Grocery Item
    public GroceryItem createGroceryItem(GroceryItemDTO groceryItemDTO) {
        MealPlan mealPlan = mealPlanRepository.findById(groceryItemDTO.getMealPlanId())
                .orElseThrow(() -> new IllegalArgumentException("MealPlan not found"));

        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(groceryItemDTO.getRecipeIngredientId())
                .orElseThrow(() -> new IllegalArgumentException("RecipeIngredient not found"));

        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setMealPlan(mealPlan);
        groceryItem.setRecipeIngredient(recipeIngredient);
        groceryItem.setQuantity(groceryItemDTO.getQuantity());
        groceryItem.setUnit(groceryItemDTO.getUnit());

        return groceryItemRepository.save(groceryItem);
    }

    // Retrieve Grocery Items for a Meal Plan
    public List<GroceryItemDTO> getGroceryItemsByMealPlan(Integer mealPlanId) {
        return groceryItemRepository.findByMealPlanId(mealPlanId).stream()
                .map(groceryItem -> {
                    GroceryItemDTO dto = new GroceryItemDTO();
                    dto.setGroceryItemId(groceryItem.getGroceryItemId());
                    dto.setMealPlanId(groceryItem.getMealPlan().getId());
                    dto.setRecipeIngredientId(groceryItem.getRecipeIngredient().getRecipeIngredientId());
                    dto.setQuantity(groceryItem.getQuantity());
                    dto.setUnit(groceryItem.getUnit());
                    return dto;
                }).collect(Collectors.toList());
    }
}
