package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface TranslatorPanelUpdateContract {
    void updateFocus();

    void updateData(String text, int position, int size);

    String getText();

    int getPosition();

    int getSize();

    void restoreAdapterData(String text, int position, int size);

    void goToStart();
}
