package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings;

import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.AboutAndSettingsCategoryModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface AboutAndSettingsContract {
    interface IAboutAndSettingsView {
        void initSettings(boolean dictionaryEnable);

        void showSettings(List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels);

        void showAboutProgramDialog();

        void showAboutAuthorDialog();

        void showReference();

    }

    interface IAboutAndSettingsPresenter {
        void loadSettings();

        void switchShowDictionaryStatus();
    }
}
