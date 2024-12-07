package com.recipe_mealplan.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.entity.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    void deleteByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
    List<Meal> findByMealPlan_Id(Integer mealPlanId);
    List<Meal> findByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);
    int countByMealPlan_IdAndDate(Integer mealPlanId, LocalDate date);

    @Query("SELECT r.recipeName FROM Meal m JOIN m.recipe r WHERE m.mealPlan.id = :mealPlanId AND m.date = :date")
    List<String> findRecipesByMealPlanIdAndDate(@Param("mealPlanId") Integer mealPlanId, @Param("date") LocalDate date);
}
