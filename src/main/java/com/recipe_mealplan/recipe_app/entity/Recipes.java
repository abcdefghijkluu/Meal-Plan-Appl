package com.recipe_mealplan.recipe_app.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer id;

    @Column(name = "recipe_name")
    private String recipeName;

    private String instructions;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "food_origin")
    private String category;

    @Column(name = "food_type")
    private String foodType;

}