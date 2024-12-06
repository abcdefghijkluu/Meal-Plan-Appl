package com.recipe_mealplan.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe_mealplan.Repository.MealPlanRepository;
import com.recipe_mealplan.Repository.UserRepository;
import com.recipe_mealplan.entity.MealPlan;
import com.recipe_mealplan.entity.User;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;

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






