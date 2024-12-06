package com.recipe_mealplan.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }
    @GetMapping("/userpage")
    public String userPage() {
        return "userpage";
    }
    

    @GetMapping("/home")
    public String homePage() {
        return "home"; // This should render home.html
    }

    // Login page route
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This should render login.html
    }

    
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }


    @GetMapping("/recipes")
    public String recipesPage() {
        return "signup";
    }

    @GetMapping("/userhome")
    public String userHomePage() {
        return "userhome";
    }

    @RequestMapping("/custom-error")
    public String handleError() {
        return "error"; 
    }
    
}

    



