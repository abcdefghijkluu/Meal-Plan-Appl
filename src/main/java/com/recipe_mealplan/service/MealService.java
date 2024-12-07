package com.recipe_mealplan.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private final RecipeIngredientRepository recipeIngredientRepository; // Declare recipeIngredientRepository

    // Inject all repositories, including recipeIngredientRepository
    public MealService(MealRepository mealRepository, 
                       MealPlanRepository mealPlanRepository, 
                       RecipesRepository recipeRepository, 
                       RecipeIngredientRepository recipeIngredientRepository) {
        this.mealRepository = mealRepository;
        this.mealPlanRepository = mealPlanRepository;
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public void saveMealsForDate(Integer mealPlanId, String date, List<Integer> recipeIds) {
        LocalDate mealDate = parseDate(date);
        MealPlan mealPlan = fetchMealPlan(mealPlanId);
        clearExistingMeals(mealPlanId, mealDate);
        saveNewMeals(mealPlan, mealDate, recipeIds);
    }

    private void saveNewMeals(MealPlan mealPlan, LocalDate mealDate, List<Integer> recipeIds) {
        for (Integer recipeId : recipeIds) {
            Recipes recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + recipeId));

            Meal meal = new Meal();
            meal.setMealPlan(mealPlan);
            meal.setDate(mealDate);
            meal.setRecipe(recipe);
            mealRepository.save(meal); // Save each meal individually
        }
    }

    public List<Meal> getMealsForDate(Integer mealPlanId, LocalDate date) {
        return mealRepository.findByMealPlan_IdAndDate(mealPlanId, date);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
        }
    }

    private MealPlan fetchMealPlan(Integer mealPlanId) {
        return mealPlanRepository.findById(mealPlanId)
                .orElseThrow(() -> new IllegalArgumentException("MealPlan not found with ID: " + mealPlanId));
    }

    private void clearExistingMeals(Integer mealPlanId, LocalDate mealDate) {
        mealRepository.deleteByMealPlan_IdAndDate(mealPlanId, mealDate);
    }

    public void deleteMeal(Long mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new IllegalArgumentException("Meal not found");
        }
        mealRepository.deleteById(mealId);
    }

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
                List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipe_Id(meal.getRecipe().getId());
                dto.setIngredients(ingredients);

                // Set instructions
                dto.setInstructions(meal.getRecipe().getInstructions());

                mealDetailsList.add(dto);
            }
        }

        return mealDetailsList;
    }
}

