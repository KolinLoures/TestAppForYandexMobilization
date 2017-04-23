package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator;

import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.ChangeLanguageDialogType;
import com.sbilsky.data.storage.models.TranslationModel;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface TranslatorContract {
    interface ITranslatorView {
        void showTranslate(TranslationModel translationModel);

        void updateTranslatorPanel(String word, int position, int size);

        void goTranslatorPanelToStart();

        void showProgress();

        void hideProgress();

        void showNoInternetConnectionError();

        void hideNoInternetConnectionError();

        void openChangeLanguageDialog(ChangeLanguageDialogType changeLanguageDialogType, String language);

        void showLanguageTo(String language);

        void showLanguageFrom(String language);

        void showReplacedLanguage();

        void showFavoriteButton();

        void hideFavoriteButton();

        void showButtonStatusIsFavorite(boolean isFavorite);

        void showClearText();

        void setDictionaryVisible();

        void setDictionaryInvisible();
    }

    interface ITranslatorPresenter {
        void loadTextTranslate(String text);

        void checkIsDictionaryShow();

        void getLanguageName();

        void getHistoryItemFromPosition(int position);

        void getEntryFromId(int id);

        void getWordStatusFavoriteOrNo(int id);

        void getLanguageFromTranslate();

        void getLanguageToTranslate();

        void setChangedLanguage(ChangeLanguageDialogType changeLanguageDialogType, LanguageModel languageModel);

        void replaceLanguage();

        void addOrDeleteTranslateToFavorite(int id, boolean favoriteStatus);

        void clearText();
    }
}
