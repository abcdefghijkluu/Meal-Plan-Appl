package com.recipe_mealplan.recipe_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recipe_mealplan.recipe_app.dto.UserDTO;
import com.recipe_mealplan.recipe_app.entity.User;
import com.recipe_mealplan.recipe_app.repository.UserRepository;
import com.recipe_mealplan.recipe_app.service.UserService;

// handles incoming http request and returns response

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    //constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public ModelAndView createUser(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            ModelAndView modelAndView = new ModelAndView("signup");
            modelAndView.addObject("errorMessage", "Username is already taken. Please choose a different one.");
            return modelAndView;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        
        User createdUser = userService.createUser(userDTO);
        
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }


    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            User existingUser = user.get();

            if (existingUser.getPassword().equals(password)) {
                return new ModelAndView("redirect:/userhome");
            } else {
                model.addAttribute("error", "Invalid password.");
                return new ModelAndView("login"); 
            }
        }
        model.addAttribute("error", "User not found.");
        return new ModelAndView("login");
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}