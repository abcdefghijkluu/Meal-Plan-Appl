package com.recipe_mealplan.recipe_app.dto;

import lombok.Getter;
import lombok.Setter;

//data transfer objects to encapsulate data that is transfered between api layer and client

@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;

}

