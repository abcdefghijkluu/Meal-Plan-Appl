package com.recipe_mealplan.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipe_mealplan.entity.MealPlan;
import com.recipe_mealplan.service.MealPlanService;


@Controller
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

    @PostMapping("/api/mealplans/finalize") // 10:29
    public ResponseEntity<?> finalizeMealPlan(
            @RequestParam Integer userId,
            @RequestParam Integer mealPlanId) {
        try {
            mealPlanService.finalizeMealPlan(userId, mealPlanId); // Implement logic in the service layer
            return ResponseEntity.ok("Meal plan finalized successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to finalize meal plan.");
        }
    }

    @GetMapping("/userhome")
    public String getUserMealPlan(Model model, @RequestParam Integer userId) {
        // Get the meal plan with recipes
        Map<String, List<String>> mealPlan = mealPlanService.getMealPlanWithRecipes(userId);

        // Add the meal plan data to the model
        model.addAttribute("mealPlan", mealPlan);

        // Render userhome.html
        return "userhome";
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
