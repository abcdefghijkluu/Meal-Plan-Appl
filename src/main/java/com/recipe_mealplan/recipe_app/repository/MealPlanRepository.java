package com.recipe_mealplan.recipe_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.recipe_app.entity.MealPlan;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Integer> {
    List<MealPlan> findByUserId(Integer userId);

}
