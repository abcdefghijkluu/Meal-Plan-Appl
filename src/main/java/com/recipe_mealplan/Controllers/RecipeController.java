package com.recipe_mealplan.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Repsoitory.RecipeRepository;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class RecipeController {
    @Autowired
    private RecipeRepository recipesrepo;
    @GetMapping("recipes/index") // Ensure only one controller handles /recipe
    public String recipePage(Model model) {
        int recipeId = 1; // Replace with the desired ID
        


        model.addAttribute("recipes", recipe);
        return "recipes/index"; // Display recipes page
    }
    

    // @GetMapping("recipes/index")

    // public String mealPlanPage(
    //     @RequestParam(name = "category", required = false) List<String> categories, 
    //     @RequestParam(name = "foodType", required = false) List<String> foodTypes, // Use foodType here
    //     Model model) {
    
    //     // If no categories or foodTypes are selected, set defaults
    //     if (categories == null) categories = List.of(); // Empty list if none selected
    //     if (foodTypes == null) foodTypes = List.of(); // Empty list if none selected
    
    //     // Query the database with selected categories and foodTypes
    //     List<Recipes> filteredRecipes = reciperepo.findByCategoryInAndFoodTypeIn(categories, foodTypes); // Updated to foodTypes
        
    //     // Add the filtered recipes to the model
    //     model.addAttribute("recipes", filteredRecipes);
        
    //     // Add categories and foodTypes for the form dropdown
    //     model.addAttribute("categories", List.of("Breakfast", "Lunch/Dinner"));
    //     model.addAttribute("foodTypes", List.of("Indian", "Asian", "European", "American", "African", "Hispanic")); // Changed from ethnicities to foodTypes
    
    //     return "recipes/index";
    // }


}
