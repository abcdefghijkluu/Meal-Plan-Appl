package com.recipe_mealplan.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipe_mealplan.entity.MealPlan;
import com.recipe_mealplan.service.MealPlanService;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    /**
     * Create a new Meal Plan
     * Accepts userId and selectedDates to create a new meal plan
     * and returns the created mealPlanId.
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createMealPlan(

        @RequestParam Integer userId,
        @RequestParam List<String> selectedDates) {

            System.out.println("Received userId: " + userId);
            System.out.println("Received selectedDates: " + selectedDates);

            Integer mealPlanId = mealPlanService.createMealPlan(userId, selectedDates);

            System.out.println("Generated mealPlanId: " + mealPlanId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Meal plan created successfully!");
            response.put("mealPlanId", mealPlanId);

            return ResponseEntity.ok(response);
    }

    /**
     * Get all Meal Plans for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealPlan>> getMealPlansByUser(@PathVariable Integer userId) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByUser(userId);
        return ResponseEntity.ok(mealPlans);
    }

    /**
     * Delete a Meal Plan by its ID.
     */
    @DeleteMapping("/{mealPlanId}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Integer mealPlanId) {
        mealPlanService.deleteMealPlan(mealPlanId);
        return ResponseEntity.noContent().build();
    }
}
