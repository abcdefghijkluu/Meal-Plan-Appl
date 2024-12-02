package com.recipe_mealplan.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Models.RecipesDto;
import com.recipe_mealplan.Repsoitory.RecipesRespository;

import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class RecipeController {

    @Autowired
    private RecipesRespository recipesrepo;

    // Display all recipes
    @GetMapping("recipes/recipe") 
    public String recipePage(Model model) {
        var recipes = recipesrepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("recipes", recipes);
        return "recipes/recipe"; // Display all recipes page
    }

    // Display the filter form page
    @GetMapping("/recipes/filter")
    public String filterPage(Model model) {
        // Categories as food ethnicities
        model.addAttribute("categories", List.of("Indian", "Asian", "European", "American", "African", "Hispanic"));
        // Optionally, meal types like Breakfast, Lunch, Dinner (if you have those as a separate field)
        model.addAttribute("foodTypes", List.of("Breakfast", "Lunch/Dinner","Dessert","Drinks"));
        return "recipes/filter";  // This refers to the filter.html template
    }
    

    // Handle the POST request from the filter form and redirect to the filtered recipes page
    @PostMapping("/recipes/filter")
    public String applyFilters(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> foodTypes,
            Model model) {
        
        // Debugging: log the values of the filters
        System.out.println("Categories: " + categories);
        System.out.println("Food Types: " + foodTypes);
    
        List<Recipes> filteredRecipes;
        if (categories != null && !categories.isEmpty() && foodTypes != null && !foodTypes.isEmpty()) {
            filteredRecipes = recipesrepo.findByCategoryInAndFoodTypeIn(categories, foodTypes);
        } else if (categories != null && !categories.isEmpty()) {
            filteredRecipes = recipesrepo.findByCategoryIn(categories);
        } else if (foodTypes != null ) {
            filteredRecipes = recipesrepo.findByFoodTypeIn(foodTypes);
        } else {
            filteredRecipes = recipesrepo.findAll();
        }
    
        model.addAttribute("filtered", filteredRecipes);

        return "recipes/filtered";
    }
    
    

    // Show filtered recipes based on the query parameters passed
    @GetMapping("/recipes/filtered")
    public String showFilteredRecipes(
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) List<String> foodType,
            Model model) {

        List<Recipes> filteredRecipes;

        // Apply filters if provided
        if (category != null && foodType != null) {
            filteredRecipes = recipesrepo.findByCategoryInAndFoodTypeIn(category, foodType);
        } else if (category != null) {
            filteredRecipes = recipesrepo.findByCategoryIn(category);
        } else if (foodType != null) {
            filteredRecipes = recipesrepo.findByFoodTypeIn(foodType);
        } else {
            filteredRecipes = recipesrepo.findAll(); // No filters
        }

        // Add filtered recipes to the model
        model.addAttribute("filtered", filteredRecipes);
        return "recipes/filtered";  // Return the filtered recipes page
    }
    // getting new recipes
    @GetMapping("recipes/create")
    public String createRecipes(Model model) {
        RecipesDto recipesDto = new RecipesDto();
        model.addAttribute("recipeDto", recipesDto);
        return "recipes/create";
    }
    @PostMapping("recipes/create")
    public String RecipesCreate(@ModelAttribute RecipesDto recipesDto) {
        Recipes recipes = new Recipes();
        recipes.setRecipeName(recipesDto.getRecipeName());
        recipes.setInstructions(recipesDto.getInstructions());
        recipes.setImageName(recipesDto.getImageName());
        recipes.setCategory(recipesDto.getCategory());
        recipes.setFoodType(recipesDto.getFoodType());
    
        // Save to database (if applicable)
        recipesrepo.save(recipes);
    
        return "redirect:/recipes/recipe";
    }
    
    
    
}
