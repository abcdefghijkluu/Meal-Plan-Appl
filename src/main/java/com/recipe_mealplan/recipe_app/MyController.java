package com.recipe_mealplan.recipe_app;

import org.springframework.stereotype.Controller;
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
    
}


