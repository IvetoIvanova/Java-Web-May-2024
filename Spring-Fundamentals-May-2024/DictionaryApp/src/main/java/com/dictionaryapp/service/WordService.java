package com.dictionaryapp.service;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.AddWordDTO;
import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.LanguageEnum;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.repo.LanguageRepository;
import com.dictionaryapp.repo.UserRepository;
import com.dictionaryapp.repo.WordRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordService {
    private final LanguageRepository languageRepository;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final UserSession userSession;

    public WordService(LanguageRepository languageRepository, WordRepository wordRepository, UserRepository userRepository, UserSession userSession) {
        this.languageRepository = languageRepository;
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean add(AddWordDTO data) {
        if (!userSession.isUserLoggedIn()) {
            return false;
        }

        Optional<User> byId = userRepository.findById(userSession.userId());

        if (byId.isEmpty()) {
            return false;
        }

        Optional<Language> byName = languageRepository.findByLanguageName(data.getLanguage());

        if (byName.isEmpty()) {
            return false;
        }

        Word word = new Word();
        word.setTerm(data.getTerm());
        word.setTranslation(data.getTranslation());
        word.setExample(data.getExample());
        word.setDate(data.getInputDate());
        word.setLanguage(byName.get());
        word.setAddedBy(byId.get());

        wordRepository.save(word);

        return true;
    }

    public Map<LanguageEnum, List<Word>> findAllByLanguage() {
        Map<LanguageEnum, List<Word>> result = new HashMap<>();

        List<Language> allCategories = languageRepository.findAll();

        for (Language language : allCategories) {
            List<Word> words = wordRepository.findAllByLanguage(language);

            result.put(language.getLanguageName(), words);
        }

        return result;
    }

    public void delete(String id) {
        userRepository.findById(userSession.userId())
                .flatMap(user -> wordRepository.findByIdAndAddedBy(id, user))
                .ifPresent(wordRepository::delete);
    }

    public void deleteAll() {
        userRepository.findById(userSession.userId())
                .map(wordRepository::findAllByAddedBy)
                .ifPresent(wordRepository::deleteAll);
    }
}
