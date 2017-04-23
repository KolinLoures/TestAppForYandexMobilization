package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.adapters;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface AboutAndSettingsAdapterListeners {
    interface OnItemClickListener {
        void onItemClick(String title);
    }

    interface OnSwitchClickedListener {
        void onSwitchClick(String title);
    }
}
