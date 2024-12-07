package com.recipe_mealplan.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe_mealplan.Models.MealDetailsDTO;
import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Repository.MealPlanRepository;
import com.recipe_mealplan.Repository.MealRepository;
import com.recipe_mealplan.Repository.RecipeIngredientRepository;
import com.recipe_mealplan.Repository.RecipesRepository;
import com.recipe_mealplan.entity.Meal;
import com.recipe_mealplan.entity.MealPlan;
import com.recipe_mealplan.entity.RecipeIngredient;



@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MealPlanRepository mealPlanRepository;
    private final RecipesRepository recipeRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;


    public MealService(MealRepository mealRepository, MealPlanRepository mealPlanRepository, RecipesRepository recipeRepository) {
        this.mealRepository = mealRepository;
        this.mealPlanRepository = mealPlanRepository;
        this.recipeRepository = recipeRepository;
    }

    // Add or update meals for a specific date in a meal plan
    public void saveMealsForDate(Integer mealPlanId, String date, List<Integer> recipeIds) {
        // Parse the date string to LocalDate
        LocalDate mealDate = LocalDate.parse(date);

        // Fetch the MealPlan entity
        MealPlan mealPlan = mealPlanRepository.findById(mealPlanId)
                .orElseThrow(() -> new IllegalArgumentException("MealPlan not found"));

        // Clear existing meals for this date in the meal plan
        mealRepository.deleteByMealPlan_IdAndDate(mealPlanId, mealDate);

        // Save new meals for the date
        for (Integer recipeId : recipeIds) {
            Recipes recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

            Meal meal = new Meal();
            meal.setMealPlan(mealPlan);
            meal.setDate(mealDate);
            meal.setRecipe(recipe);

            mealRepository.save(meal);
        }
    }






    // public List<MealDetailsDTO> getMealPlanDetails(Integer userId) {
    //     List<MealDetailsDTO> mealDetailsList = new ArrayList<>();
        
    //     // Fetch the meal plans for the user
    //     List<MealPlan> mealPlans = mealPlanRepository.findByUserId(userId);
    //     System.out.println("Meal plans found: " + mealPlans.size());
        
    //     for (MealPlan mealPlan : mealPlans) {
    //         for (Meal meal : mealPlan.getMeals()) {
    //             MealDetailsDTO dto = new MealDetailsDTO();
    //             dto.setMealDate(meal.getDate());
    //             dto.setRecipeName(meal.getRecipe().getRecipeName());
                
    //             // Fetch the ingredients for the recipe
    //             List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipe_Id(meal.getRecipe().getId());
    //             dto.setIngredients(ingredients);
                
    //             // Set instructions
    //             dto.setInstructions(meal.getRecipe().getInstructions());
                
    //             mealDetailsList.add(dto);
    //         }
    //     }
        
    //     System.out.println("Meal details count: " + mealDetailsList.size());
    //     return mealDetailsList;
    // }
    

    // //OEISY
    public List<MealDetailsDTO> getMealPlanDetails(Integer userId) {
        List<MealDetailsDTO> mealDetailsList = new ArrayList<>();
        
        // Fetch the meal plans for the user
        List<MealPlan> mealPlans = mealPlanRepository.findByUserId(userId);
        
        for (MealPlan mealPlan : mealPlans) {
            for (Meal meal : mealPlan.getMeals()) {
                MealDetailsDTO dto = new MealDetailsDTO();
                dto.setDate(meal.getDate());
                dto.setRecipeName(meal.getRecipe().getRecipeName());
                
                // Fetch the ingredients for the recipe
                List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(meal.getRecipe().getId());
                dto.setIngredients(ingredients);
                
                // Set instructions
                dto.setInstructions(meal.getRecipe().getInstructions());
                
                mealDetailsList.add(dto);
            }
        }
        
        return mealDetailsList;
    }

    
    // Get meals for a specific date in a meal plan
    public List<Meal> getMealsForDate(Integer mealPlanId, LocalDate date) {
        return mealRepository.findByMealPlan_IdAndDate(mealPlanId, date);
    }

    // Get meal count for a specific date in a meal plan
    public int getMealCountForDate(Integer mealPlanId, LocalDate date) {
        return mealRepository.countByMealPlan_IdAndDate(mealPlanId, date);
    }

    // Delete a meal by ID
    public void deleteMeal(Long mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new IllegalArgumentException("Meal not found");
        }
        mealRepository.deleteById(mealId);
    }

    // Fetch all meals for a meal plan (for summary)
    public List<Meal> getMealsForMealPlan(Integer mealPlanId) {
        return mealRepository.findByMealPlan_Id(mealPlanId);
    }
}