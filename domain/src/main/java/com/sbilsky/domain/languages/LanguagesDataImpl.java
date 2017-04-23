package com.sbilsky.domain.languages;


import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.data.storage.usage.languages.ILanguagesStorage;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguagesDataImpl implements ILanguagesData {
    private ILanguagesStorage iLanguagesStorage;

    public LanguagesDataImpl(ILanguagesStorage iLanguagesStorage) {
        this.iLanguagesStorage = iLanguagesStorage;
    }

    @Override
    public List<LanguageModel> getAllLanguages() {
        return iLanguagesStorage.getAllLanguages();
    }

    @Override
    public String getLanguageNameByAbbreviation(String abbreviation) {
        return iLanguagesStorage.getLanguageNameByAbbreviation(abbreviation);
    }

    @Override
    public void setAllLanguages(List<LanguageModel> languageModels) {
        iLanguagesStorage.setAllLanguages(languageModels);
    }
}
