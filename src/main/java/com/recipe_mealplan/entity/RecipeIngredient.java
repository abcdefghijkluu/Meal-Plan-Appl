package com.recipe_mealplan.entity;

import com.recipe_mealplan.Models.Recipes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// Represent the relationship between recipes and ingredients, detailing the specific quantities and units for advanced function.

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private Long recipeIngredientId;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipes recipe;

    @Column(name = "ingredient_name", nullable = false)
    private String ingredientName;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "unit", nullable = false)
    private String unit;
}