package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class SwitchModel {
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public SwitchModel(boolean status) {
        this.status = status;
    }
}
