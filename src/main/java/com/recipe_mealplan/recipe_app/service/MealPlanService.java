package com.recipe_mealplan.recipe_app.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe_mealplan.recipe_app.entity.MealPlan;
import com.recipe_mealplan.recipe_app.entity.RecipeIngredient;
import com.recipe_mealplan.recipe_app.entity.User;
import com.recipe_mealplan.recipe_app.repository.MealPlanRepository;
import com.recipe_mealplan.recipe_app.repository.RecipeIngredientRepository;
import com.recipe_mealplan.recipe_app.repository.UserRepository;



@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;


    public MealPlanService(MealPlanRepository mealPlanRepository, UserRepository userRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new Meal Plan for a User.
     * Accepts Integer userId and returns the ID of the created meal plan.
     */
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

    /////OEISY
    //finding the meal plan by id
    public MealPlan getMealPlanById(Integer id) {
        return mealPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal Plan not found"));
    }

    //finding the recipeby id
    public List<RecipeIngredient> getIngredientsForRecipe(Integer recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId);
    }
    

 
 
    /**
     * Get all Meal Plans for a specific user.
     */
    public List<MealPlan> getMealPlansByUser(Integer userId) {
        return mealPlanRepository.findByUserId(userId);
    }

    /**
     * Delete a Meal Plan by its ID.
     */
    public void deleteMealPlan(Integer mealPlanId) {
        mealPlanRepository.deleteById(mealPlanId);
    }
}







