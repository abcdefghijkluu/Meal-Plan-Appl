package com.recipe_mealplan.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.entity.MealPlan;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Integer> {
    List<MealPlan> findByUserId(Integer userId);

    // Confirm that the MealRepository includes the deleteByMealPlan_IdAndDate method
    void deleteByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
}