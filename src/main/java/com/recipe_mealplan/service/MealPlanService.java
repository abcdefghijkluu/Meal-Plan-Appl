package com.recipe_mealplan.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe_mealplan.Repository.MealPlanRepository;
import com.recipe_mealplan.Repository.MealRepository;
import com.recipe_mealplan.Repository.RecipeIngredientRepository;
import com.recipe_mealplan.Repository.UserRepository;
import com.recipe_mealplan.entity.MealPlan;
import com.recipe_mealplan.entity.RecipeIngredient;
import com.recipe_mealplan.entity.User;

import jakarta.transaction.Transactional;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final MealRepository mealRepository; 
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository; // Add RecipeIngredientRepository
    private final UserRepository userRepository;

    public MealPlanService(MealPlanRepository mealPlanRepository, 
                           MealRepository mealRepository, 
                           RecipeIngredientRepository recipeIngredientRepository, // Inject RecipeIngredientRepository
                           UserRepository userRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.mealRepository = mealRepository; 
        this.recipeIngredientRepository = recipeIngredientRepository; // Initialize RecipeIngredientRepository
        this.userRepository = userRepository;
    }

    public Integer createMealPlan(Integer userId, List<String> selectedDates) {
        System.out.println("Creating Meal Plan for userId: " + userId);
        System.out.println("Selected Dates: " + selectedDates);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setStartDate(LocalDate.parse(selectedDates.get(0)));
        mealPlan.setEndDate(LocalDate.parse(selectedDates.get(selectedDates.size() - 1)));

        MealPlan savedMealPlan = mealPlanRepository.save(mealPlan);
        System.out.println("Saved MealPlan ID: " + savedMealPlan.getId());
        return savedMealPlan.getId();
    }

    public Map<String, List<String>> getMealPlanWithRecipes(Integer userId) {
        // Retrieve meal plans for the user
        List<MealPlan> mealPlans = mealPlanRepository.findByUserId(userId);

        // Create a map to hold the date and associated recipes
        Map<String, List<String>> mealPlanWithRecipes = new HashMap<>();

        // Populate the map with dates and their corresponding recipes
        for (MealPlan mealPlan : mealPlans) {
            LocalDate date = mealPlan.getStartDate(); // Ensure you use the correct date field
            List<String> recipes = mealRepository.findRecipesByMealPlanIdAndDate(mealPlan.getId(), date); // Ensure this method is properly defined
            mealPlanWithRecipes.put(date.toString(), recipes);
        }

        return mealPlanWithRecipes;
    }

    @Transactional
    public void finalizeMealPlan(Integer userId, Integer mealPlanId) {
        System.out.println("Finalizing meal plan for userId: " + userId + ", mealPlanId: " + mealPlanId);

        // Find the meal plan by ID and user ID
        MealPlan mealPlan = mealPlanRepository.findById(mealPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Meal Plan not found for ID: " + mealPlanId));

        // Verify the meal plan belongs to the user
        if (!mealPlan.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Meal Plan does not belong to the user.");
        }

        // Mark the meal plan as finalized
        mealPlan.setFinalized(true);

        // Save the updated meal plan
        mealPlanRepository.save(mealPlan);

        System.out.println("Meal plan finalized successfully for ID: " + mealPlanId);
    }


    //finding the meal plan by id
    public MealPlan getMealPlanById(Integer id) {
        return mealPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal Plan not found"));
    }

    //finding the recipeby id
    public List<RecipeIngredient> getIngredientsForRecipe(Integer recipeId) {
        return recipeIngredientRepository.findByRecipe_Id(recipeId);
    }

    public List<MealPlan> getMealPlansByUser(Integer userId) {
        return mealPlanRepository.findByUserId(userId);
    }

    public void deleteMealPlan(Integer mealPlanId) {
        mealPlanRepository.deleteById(mealPlanId);
    }
}

