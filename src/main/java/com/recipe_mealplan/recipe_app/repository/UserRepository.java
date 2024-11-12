package com.recipe_mealplan.recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe_mealplan.recipe_app.entity.User;

// This class interacts with database using JPA

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
