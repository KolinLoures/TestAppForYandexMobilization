package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator;

import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface TranslatorFrgLoadingIniciator {
    void getTranslateByTypeAndPosition(int position, int id, FragmentType fragmentTypeFrom);
}
