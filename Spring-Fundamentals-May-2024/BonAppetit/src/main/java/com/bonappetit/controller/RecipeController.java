package com.bonappetit.controller;

import com.bonappetit.config.UserSession;
import com.bonappetit.model.dto.AddRecipeDTO;
import com.bonappetit.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final UserSession userSession;

    public RecipeController(RecipeService recipeService, UserSession userSession) {
        this.recipeService = recipeService;
        this.userSession = userSession;
    }

    @ModelAttribute("recipeData")
    public AddRecipeDTO recipeData() {
        return new AddRecipeDTO();
    }


    @GetMapping("/add-recipe")
    public String viewAddRecipe() {
        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        return "recipe-add";
    }

    @PostMapping("/add-recipe")
    public String doAddRecipe(
            @Valid AddRecipeDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.recipeData", bindingResult);

            return "redirect:/add-recipe";
        }

        boolean success = recipeService.create(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("recipeData", data);

            return "redirect:/add-recipe";
        }

        return "redirect:/home";
    }

    @PostMapping("/add-to-favourites/{recipeId}")
    public String addToFavourites(@PathVariable long recipeId) {
        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        recipeService.addToFavourites(userSession.id(), recipeId);

        return "redirect:/home";
    }

}
