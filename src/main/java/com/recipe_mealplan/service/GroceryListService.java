
package com.recipe_mealplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recipe_mealplan.Repository.TestRepository;
import com.recipe_mealplan.Models.RecipeIngredientDTO; // Ensure you import the correct DTO class

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroceryListService {

    @Autowired
    private TestRepository testRepository;

    // Ensure the list is declared with the correct type: List<RecipeIngredientDTO>
    public List<RecipeIngredientDTO> getGroceryListForMealPlan(Long mealPlanId) {
        List<Object[]> results = testRepository.getGroceryList(mealPlanId);

        // Map results to DTO
        return results.stream()
                .map(result -> {
                    RecipeIngredientDTO dto = new RecipeIngredientDTO();
                    dto.setIngredientName((String) result[0]);
                    dto.setQuantity((Double) result[1]);
                    dto.setUnit((String) result[2]);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

