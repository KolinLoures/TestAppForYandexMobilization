package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class SettingsModel {
    private String settingsName;
    private SwitchModel switchModel;

    public SettingsModel(String settingsName) {
        this.settingsName = settingsName;
    }

    public SettingsModel(String settingsName, SwitchModel switchModel) {
        this.settingsName = settingsName;
        this.switchModel = switchModel;
    }

    public SwitchModel getSwitchModel() {
        return switchModel;
    }

    public void setSwitchModel(SwitchModel switchModel) {
        this.switchModel = switchModel;
    }

    public String getSettingsName() {

        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }
}
