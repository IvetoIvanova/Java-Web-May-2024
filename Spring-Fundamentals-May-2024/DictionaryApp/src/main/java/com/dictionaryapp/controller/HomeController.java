package com.dictionaryapp.controller;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.WordInfoDTO;
import com.dictionaryapp.model.entity.LanguageEnum;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.service.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final WordService wordService;
    private final UserService userService;

    public HomeController(UserSession userSession, WordService wordService, UserService userService) {
        this.userSession = userSession;
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String notLogged() {
        if (userSession.isUserLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String logged(Model model) {
        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }

        Map<LanguageEnum, List<Word>> allWords = wordService.findAllByLanguage();

        List<WordInfoDTO> germanWords = allWords.get(LanguageEnum.GERMAN)
                .stream()
                .map(WordInfoDTO::new)
                .toList();

        List<WordInfoDTO> spanishWords = allWords.get(LanguageEnum.SPANISH)
                .stream()
                .map(WordInfoDTO::new)
                .toList();

        List<WordInfoDTO> frenchWords = allWords.get(LanguageEnum.FRENCH)
                .stream()
                .map(WordInfoDTO::new)
                .toList();

        List<WordInfoDTO> italianWords = allWords.get(LanguageEnum.ITALIAN)
                .stream()
                .map(WordInfoDTO::new)
                .toList();


        int allWordsCount = allWords.values().stream()
                .mapToInt(List::size)
                .sum();

        model.addAttribute("germanWordsData", germanWords);
        model.addAttribute("spanishWordsData", spanishWords);
        model.addAttribute("frenchWordsData", frenchWords);
        model.addAttribute("italianWordsData", italianWords);
        model.addAttribute("allWordsData", allWordsCount);

        return "home";
    }
}
