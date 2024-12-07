package com.recipe_mealplan.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.recipe_mealplan.service.GroceryListService;
import com.recipe_mealplan.Models.RecipeIngredientDTO;
import com.recipe_mealplan.recipe_app.service.MealService;
import com.recipe_mealplan.Models.MealDetailsDTO;



import java.util.List;

@Controller
 // Ensure your base URL is set here
public class GroceryListController {

    @Autowired
    private GroceryListService groceryListService;

    @GetMapping("/recipes/test/{mealPlanId}")
    public String getGroceryList(@PathVariable Long mealPlanId, Model model) {
        List<RecipeIngredientDTO> groceryList = groceryListService.getGroceryListForMealPlan(mealPlanId);  // Pass dynamic mealPlanId
        model.addAttribute("groceryList", groceryList);
        return "recipes/test";
    }
       //OEISY
       @GetMapping("/details")
       public String getMealPlanDetails(@RequestParam Integer userId, Model model) {
           List<MealDetailsDTO> mealDetails = MealService.getMealPlanDetails(userId);
           model.addAttribute("mealDetails", mealDetails);
           return "mealplan/details"; // Thymeleaf view for meal plan details
       }
    
}