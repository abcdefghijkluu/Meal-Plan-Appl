package com.recipe_mealplan.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.Models.RecipeIngredientDTO;

@Repository
public interface TestRepository extends CrudRepository<RecipeIngredientDTO, Long> {

    @Query(value = """
        SELECT 
            ri.ingredient_name, 
            SUM(ri.quantity) AS total_quantity, 
            ri.unit
        FROM 
            recipe_ingredient ri
        INNER JOIN 
            recipes r ON ri.recipe_id = r.recipe_id
        INNER JOIN 
            meal m ON r.recipe_id = m.recipe_id
        INNER JOIN 
            meal_plan mp ON m.meal_plan_id = mp.meal_plan_id
        WHERE 
            mp.meal_plan_id = :mealPlanId
        GROUP BY 
            ri.ingredient_name, ri.unit
        """, nativeQuery = true)
    List<Object[]> getGroceryList(@Param("mealPlanId") Long mealPlanId);
}
