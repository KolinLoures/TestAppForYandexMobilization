package com.sbilsky.data.storage.models;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguageModel {
    private String abbreviation, name;

    public LanguageModel(String name, String abbreviation) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
