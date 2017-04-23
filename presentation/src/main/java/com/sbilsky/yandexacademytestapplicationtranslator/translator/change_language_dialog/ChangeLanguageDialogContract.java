package com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog;

import com.sbilsky.data.storage.models.LanguageModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface ChangeLanguageDialogContract {
    interface IChangeLanguageDialogView {
        void showLanguages(List<LanguageModel> languageModels, int active);
    }

    interface IChangeLanguageDialogPresenter {
        void loadLanguages(String selected);
    }
}
