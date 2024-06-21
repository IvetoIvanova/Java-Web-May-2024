package com.dictionaryapp.model.dto;

import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;

import java.time.LocalDate;

public class WordInfoDTO {

    private long id;
    private String term;
    private String translation;
    private String example;
    private LocalDate date;
    private User addedBy;

    public WordInfoDTO(Word word) {
        this.id = word.getId();
        this.example = word.getExample();
        this.term = word.getTerm();
        this.translation = word.getTranslation();
        this.date = word.getDate();
        this.addedBy = word.getAddedBy();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
}
