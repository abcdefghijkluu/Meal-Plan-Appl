package com.recipe_mealplan.recipe_app.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.recipe_mealplan.recipe_app.Models.UserDTO;
import com.recipe_mealplan.recipe_app.entity.User;
import com.recipe_mealplan.recipe_app.repository.UserRepository;
import com.recipe_mealplan.recipe_app.service.UserService;

import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken. Please choose a different one.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        
        userService.createUser(userDTO);
        return ResponseEntity.ok("User created successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<User> user = userRepository.findByUsername(username);
    
        if (user.isPresent()) {
            User existingUser = user.get();
    
            if (existingUser.getPassword().equals(password)) {
                // Store userId in session
                session.setAttribute("userId", existingUser.getId());
                return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
            } else {
                return ResponseEntity.status(401).body("Invalid password.");
            }
        }
        return ResponseEntity.status(404).body("User not found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}