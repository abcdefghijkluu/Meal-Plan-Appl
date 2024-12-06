package com.recipe_mealplan.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     public ResponseEntity<String> saveMealsForDate(
             @RequestParam Integer mealPlanId,
             @RequestParam String date,
             @RequestParam List<Integer> recipeIds) {
         System.out.println("Saving meals for mealPlanId: " + mealPlanId);
         System.out.println("Date: " + date);
         System.out.println("Recipe IDs: " + recipeIds);
     
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