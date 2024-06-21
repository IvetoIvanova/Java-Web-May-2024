package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.AddWordDTO;
import com.dictionaryapp.service.WordService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WordController {

    private final WordService wordService;
    private final UserSession userSession;

    public WordController(WordService wordService, UserSession userSession) {
        this.wordService = wordService;
        this.userSession = userSession;
    }

    @ModelAttribute("addWordData")
    public AddWordDTO wordData() {
        return new AddWordDTO();
    }

    @GetMapping("/add-word")
    public String viewAddWord() {
        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        return "word-add";
    }

    @PostMapping("/add-word")
    public String doAddWord(
            @Valid AddWordDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addWordData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addWordData", bindingResult);

            return "redirect:/add-word";
        }

        boolean success = wordService.add(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("addWordData", data);

            return "redirect:/add-word";
        }

        return "redirect:/home";
    }

    @GetMapping("/words/{id}")
    public String deleteWord(@PathVariable("id") String id) {
        wordService.delete(id);

        return "redirect:/home";
    }

    @GetMapping("/home/remove-all")
    public String deleteAllWords() {
        wordService.deleteAll();

        return "redirect:/home";
    }
}
