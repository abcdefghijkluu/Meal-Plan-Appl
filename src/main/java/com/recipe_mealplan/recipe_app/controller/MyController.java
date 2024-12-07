package com.recipe_mealplan.recipe_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

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
    public String userHomePage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("userId", userId);
        return "userhome";
    }


        
    // //OEISY
    @GetMapping("/mealplandetails")
    public String mealplandetails() {
        return "mealplandetails";
    }

    @RequestMapping("/custom-error")
    public String handleError() {
        return "error"; 
    }
    
}
