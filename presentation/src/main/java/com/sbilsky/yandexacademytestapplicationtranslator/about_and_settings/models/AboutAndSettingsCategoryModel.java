package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AboutAndSettingsCategoryModel {
    private String title;
    private List<SettingsModel> settingsModels;

    public AboutAndSettingsCategoryModel(String title, List<SettingsModel> settingsModels) {
        this.title = title;
        this.settingsModels = settingsModels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SettingsModel> getSettingsModels() {
        return settingsModels;
    }

    public void setSettingsModels(List<SettingsModel> settingsModels) {
        this.settingsModels = settingsModels;
    }
}
