package com.recipe_mealplan.recipe_app.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroceryItemDTO {

    private Long groceryItemId; 
    private Integer mealPlanId;   
    private Long recipeIngredientId; 
    private String ingredientName;   
    private Double quantity;        
    private String unit;            

}
