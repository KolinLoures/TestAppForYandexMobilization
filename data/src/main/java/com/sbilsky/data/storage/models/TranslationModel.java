package com.sbilsky.data.storage.models;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslationModel {
    private int id;
    private String word, translate;
    private LanguageModel languageModelFrom, languageModelTo;
    private List<TranslateModel> translateModels;
    private boolean favorite;
    private int cursorSize;

    public TranslationModel(int id, String word, String translate, boolean favorite, int cursorSize, LanguageModel languageModelFrom, LanguageModel languageModelTo) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.favorite = favorite;
        this.cursorSize = cursorSize;
        this.languageModelFrom = languageModelFrom;
        this.languageModelTo = languageModelTo;
    }

    public TranslationModel(int id, String word, String translate, List<TranslateModel> translateModels, boolean favorite, int cursorSize, LanguageModel languageModelFrom, LanguageModel languageModelTo) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.translateModels = translateModels;
        this.favorite = favorite;
        this.cursorSize = cursorSize;
        this.languageModelFrom = languageModelFrom;
        this.languageModelTo = languageModelTo;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public List<TranslateModel> getTranslateModels() {
        return translateModels;
    }

    public void setTranslateModels(List<TranslateModel> translateModels) {
        this.translateModels = translateModels;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getCursorSize() {
        return cursorSize;
    }

    public void setCursorSize(int cursorSize) {
        this.cursorSize = cursorSize;
    }

    public LanguageModel getLanguageModelFrom() {
        return languageModelFrom;
    }

    public void setLanguageModelFrom(LanguageModel languageModelFrom) {
        this.languageModelFrom = languageModelFrom;
    }

    public LanguageModel getLanguageModelTo() {
        return languageModelTo;
    }

    public void setLanguageModelTo(LanguageModel languageModelTo) {
        this.languageModelTo = languageModelTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
