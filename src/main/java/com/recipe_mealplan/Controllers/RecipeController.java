package com.recipe_mealplan.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipe_mealplan.Models.Recipes;
import com.recipe_mealplan.Models.RecipesDto;
import com.recipe_mealplan.Repository.RecipesRepository;

@Controller
public class RecipeController {

    @Autowired
    private RecipesRepository recipesrepo;

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
    @GetMapping("recipes/delete")
    public String deleteRecipe(@RequestParam int id) {
        Optional<Recipes> optionalRecipe = recipesrepo.findById(id); // Returns Optional<Recipes>
        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get(); // Get the Recipes object from Optional
            recipesrepo.delete(recipe);
        }
        return "redirect:/recipes/recipe"; // Redirect to the recipes listing page
    }
    @GetMapping("recipes/edit")
    public String editRecipes(Model model, @RequestParam int id) {
        Optional<Recipes> optionalRecipe = recipesrepo.findById(id);
        
        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get();
            RecipesDto recipesDto = new RecipesDto();
    
            // Populate DTO fields
            recipesDto.setId(recipe.getId());  // Ensure ID is set here
            recipesDto.setRecipeName(recipe.getRecipeName());
            recipesDto.setInstructions(recipe.getInstructions());
            recipesDto.setImageName(recipe.getImageName());
            recipesDto.setCategory(recipe.getCategory());
            recipesDto.setFoodType(recipe.getFoodType());
    
            // Add to model
            model.addAttribute("recipeDto", recipesDto);
        } else {
            return "redirect:/recipes/recipe";
        }
    
        return "recipes/edit";
    }
    
    @PostMapping("recipes/edit")
    public String editedRecipes(@RequestParam int id, @ModelAttribute RecipesDto recipesDto) {
        System.out.println("Received ID: " + id);
        System.out.println("Received DTO: " + recipesDto);
    
        Optional<Recipes> recipesOptional = recipesrepo.findById(id);
        if (recipesOptional.isPresent()) {
            Recipes recipes = recipesOptional.get();
    
            // Update fields
            recipes.setRecipeName(recipesDto.getRecipeName());
            recipes.setInstructions(recipesDto.getInstructions());
            recipes.setImageName(recipesDto.getImageName());
            recipes.setCategory(recipesDto.getCategory());
            recipes.setFoodType(recipesDto.getFoodType());
    
            recipesrepo.save(recipes);
            System.out.println("Updated Recipe: " + recipes);
        }
    
        return "redirect:/recipes/recipe";
    }  
}
