package com.sbilsky.data.storage.usage.languages;

import com.sbilsky.data.storage.models.LanguageModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface ILanguagesStorage {
    List<LanguageModel> getAllLanguages();

    String getLanguageNameByAbbreviation(String abbreviation);

    void setAllLanguages(List<LanguageModel> languageModels);
}
