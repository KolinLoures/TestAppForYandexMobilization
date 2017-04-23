package com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity;

import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface NavigationActivityContract {
    void showTranslateInTranslatorFragment(int position, int id, FragmentType fragmentTypeFrom);
}
