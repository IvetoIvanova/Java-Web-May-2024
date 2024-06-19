package com.bonappetit.model.entity;

import com.bonappetit.model.enums.CategoryName;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryName categoryName;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;

    public Category() {
        this.recipes = new ArrayList<>();
    }

    public Category(CategoryName name, String description) {
        super();

        this.categoryName = name;
        this.description = description;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
