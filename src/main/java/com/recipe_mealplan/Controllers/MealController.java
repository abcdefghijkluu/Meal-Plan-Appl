package com.recipe_mealplan.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipe_mealplan.entity.Meal;
import com.recipe_mealplan.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    /**
     * Add a meal for a specific date in the meal plan.
     * If a meal already exists for the date, it will append the recipe to the existing meals.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveMealsForDate(@RequestBody Map<String, Object> payload) {
        Integer mealPlanId = (Integer) payload.get("mealPlanId");
        String date = (String) payload.get("date");
        List<Integer> recipeIds = (List<Integer>) payload.get("recipeIds");

        System.out.println("mealPlanId: " + mealPlanId);
        System.out.println("date: " + date);
        System.out.println("recipeIds: " + recipeIds);

        if (mealPlanId == null || date == null || recipeIds == null || recipeIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: Ensure all fields are provided and recipeIds is not empty.");
        }

        mealService.saveMealsForDate(mealPlanId, date, recipeIds);
        return ResponseEntity.ok("Meals saved successfully!");
    }

    /**
     * Get all meals for a specific meal plan and date.
     */
    @GetMapping
    public ResponseEntity<List<Meal>> getMealsForDate(
            @RequestParam Integer mealPlanId,
            @RequestParam String date) {
        List<Meal> meals = mealService.getMealsForDate(mealPlanId, LocalDate.parse(date));
        return ResponseEntity.ok(meals);
    }

    /**
     * Delete a meal by ID (optional).
     */
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }
}
