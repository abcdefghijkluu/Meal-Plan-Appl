package com.recipe_mealplan.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Repository.MealPlanRepository;
import com.recipe_mealplan.Repository.MealRepository;
import com.recipe_mealplan.Repository.RecipesRepository;
import com.recipe_mealplan.entity.Meal;
import com.recipe_mealplan.entity.MealPlan;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MealPlanRepository mealPlanRepository;
    private final RecipesRepository recipeRepository;

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

