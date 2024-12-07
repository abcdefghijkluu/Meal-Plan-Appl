package com.recipe_mealplan.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.entity.Meal;
import com.recipe_mealplan.entity.RecipeIngredient;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    void deleteByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
    List<Meal> findByMealPlan_Id(Integer mealPlanId);
    List<Meal> findByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
    int countByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);

    List<RecipeIngredient> findByRecipeId(Integer recipeId);
}

