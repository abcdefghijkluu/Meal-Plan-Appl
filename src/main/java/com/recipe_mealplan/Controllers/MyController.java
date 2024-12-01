package com.recipe_mealplan.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;







@Controller
public class MyController {

    // Home page route (default route)
    @GetMapping("/")
    public String homePage() {
        return "home"; // This should render home.html
    }

    // Login page route
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This should render login.html
    }
    @GetMapping("/signup")
    public String SignUpPage() {
        return "signup";
    }
    @GetMapping("/recipes/index")
    public String filterPage(Model model) {
        // Add categories and food types to the model for the filter form
        model.addAttribute("categories", List.of("Breakfast", "Lunch/Dinner"));  // Example categories
        model.addAttribute("foodTypes", List.of("Indian", "Asian", "European", "American", "African", "Hispanic"));
        return "recipes/index";  // Return the filter page (filter.html)
    }
    
}


