package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings;


import com.sbilsky.data.storage.api.preferences.Preferences;
import com.sbilsky.yandexacademytestapplicationtranslator.simpleMVP.AbstractPresenter;

/**
 * @author Cвятослав Бильский s.bislky
 */

class AboutAndSettingPresenter extends AbstractPresenter<AboutAndSettingsContract.IAboutAndSettingsView> implements AboutAndSettingsContract.IAboutAndSettingsPresenter {
    @Override
    public void loadSettings() {
        initSettings(Preferences.DICTIONARY_ENABLED.getBoolean());
    }

    @Override
    public void switchShowDictionaryStatus() {
        Preferences.DICTIONARY_ENABLED.set(!Preferences.DICTIONARY_ENABLED.getBoolean());
    }

    private void initSettings(boolean showDictionary) {
        if (getView() != null) {
            getView().initSettings(showDictionary);
        }
    }
}
