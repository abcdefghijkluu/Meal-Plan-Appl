package com.recipe_mealplan.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.recipe_mealplan.Repsoitory.RecipeRepository;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RecipeController {
    @Autowired
    private RecipeRepository reciperepo;
    @GetMapping("recipes/index")
    public String recipesPage(Model model) {
        var recipes = reciperepo.findAll();
        model.addAttribute("recipes", recipes);
        return "recipes/index";
    }
    


}
