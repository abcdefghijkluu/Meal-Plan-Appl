package com.recipe_mealplan.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    // Delete meals by meal plan ID and date
    void deleteByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);

    // Find meals by meal plan ID
    List<Meal> findByMealPlan_Id(Integer mealPlanId);

    // Find meals by meal plan ID and date
    List<Meal> findByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);

    // Count meals by meal plan ID and date
    int countByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
}