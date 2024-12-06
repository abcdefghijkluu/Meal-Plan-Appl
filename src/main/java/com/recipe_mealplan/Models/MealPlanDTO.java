package com.recipe_mealplan.Models;

import java.util.List;

public class MealPlanDTO {

    private Integer userId;
    private List<String> selectedDates;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(List<String> selectedDates) {
        this.selectedDates = selectedDates;
    }
}

