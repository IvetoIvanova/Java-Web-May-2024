package com.bonappetit.model.dto;

import com.bonappetit.model.enums.CategoryName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddRecipeDTO {

    @NotNull
    @Size(min = 2, max = 40, message = "Name length must be between 2 and 40 characters!")
    private String name;
    @Size(min = 2, max = 150, message = "Ingredients length must be between 2 and 150 characters!")
    private String ingredients;
    @NotNull(message = "You must select a category!")
    private CategoryName category;

    public AddRecipeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }
}
