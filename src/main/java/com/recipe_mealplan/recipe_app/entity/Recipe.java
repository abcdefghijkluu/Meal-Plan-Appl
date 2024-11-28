package com.recipe_mealplan.recipe_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "food_origin", nullable = false)
    private String foodOrigin;

    @Column(name = "food_type", nullable = false)
    private String foodType; 

}