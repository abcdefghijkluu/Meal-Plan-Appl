package com.recipe_mealplan.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe_mealplan.Models.RecipeIngredientDTO;
import com.recipe_mealplan.entity.RecipeIngredient;
import com.recipe_mealplan.service.RecipeIngredientService;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    // Add a new ingredient
    @PostMapping
    public ResponseEntity<RecipeIngredient> addRecipeIngredient(@RequestBody RecipeIngredientDTO dto) {
        RecipeIngredient ingredient = recipeIngredientService.addRecipeIngredient(dto);
        return ResponseEntity.ok(ingredient);
    }

    // Get all ingredients for a recipe
    @GetMapping("/{recipeId}")
    public ResponseEntity<List<RecipeIngredientDTO>> getIngredientsByRecipeId(@PathVariable Integer recipeId) {
        List<RecipeIngredientDTO> ingredients = recipeIngredientService.getIngredientsByRecipeId(recipeId);
        return ResponseEntity.ok(ingredients);
    }

    // Delete an ingredient
    @DeleteMapping("/{recipeIngredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long recipeIngredientId) {
        recipeIngredientService.deleteIngredient(recipeIngredientId);
        return ResponseEntity.noContent().build();
    }
}
