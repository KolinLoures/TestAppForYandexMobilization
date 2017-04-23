package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator;


import com.sbilsky.data.storage.api.preferences.Preferences;
import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.domain.rx_usecases.favorite_and_history.FavoriteAndHistoryRx;
import com.sbilsky.domain.rx_usecases.get_translate.GetTranslateRx;
import com.sbilsky.domain.rx_usecases.language.LanguageRx;
import com.sbilsky.yandexacademytestapplicationtranslator.simpleMVP.AbstractPresenter;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.ChangeLanguageDialogType;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.Utils;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author Cвятослав Бильский s.bislky
 */

class TranslatorPresenter extends AbstractPresenter<TranslatorContract.ITranslatorView> implements TranslatorContract.ITranslatorPresenter {

    @Override
    public void loadTextTranslate(String text) {
        hideNoInternetConnectionError();
        clearText();
        showProgress();
        GetTranslateRx.getTranslation(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<TranslationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        showNoInternetConnectionError();
                    }

                    @Override
                    public void onNext(TranslationModel translationModel) {
                        hideProgress();
                        showTranslate(translationModel);
                        showFavoriteButton();
                        showButtonStatusIsFavorite(translationModel.isFavorite());
                        updateTranslatorPanel(translationModel.getWord(), 0, translationModel.getCursorSize());
                    }
                });


    }

    @Override
    public void checkIsDictionaryShow() {
        if (Preferences.DICTIONARY_ENABLED.getBoolean()) {
            setDictionaryVisible();
        } else {
            setDictionaryInvisible();
        }
    }

    @Override
    public void getLanguageName() {
        LanguageRx.getLanguageNameByAbbreviation(Preferences.LANGUAGE_TO.getString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::showLanguageTo);
        LanguageRx.getLanguageNameByAbbreviation(Preferences.LANGUAGE_FROM.getString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::showLanguageFrom);
    }

    @Override
    public void getHistoryItemFromPosition(int position) {
        hideNoInternetConnectionError();
        FavoriteAndHistoryRx.getHistoryFromPosition(position)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(translationModel -> {
                    if (translationModel != null) {
                        Preferences.LANGUAGE_FROM.set(translationModel.getLanguageModelFrom().getAbbreviation());
                        Preferences.LANGUAGE_TO.set(translationModel.getLanguageModelTo().getAbbreviation());
                        showLanguageTo(translationModel.getLanguageModelTo().getName());
                        showLanguageFrom(translationModel.getLanguageModelFrom().getName());
                        showTranslate(translationModel);
                        showFavoriteButton();
                        showButtonStatusIsFavorite(translationModel.isFavorite());
                        updateTranslatorPanel(translationModel.getWord(), position, translationModel.getCursorSize());
                    } else {
                        clearText();
                    }
                });
    }

    @Override
    public void getEntryFromId(int id) {
        hideNoInternetConnectionError();
        FavoriteAndHistoryRx.getEntryFromID(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(translationModel -> {
                    updateTranslatorPanel(translationModel.getWord(), -1, 1);
                    Preferences.LANGUAGE_FROM.set(translationModel.getLanguageModelFrom().getAbbreviation());
                    Preferences.LANGUAGE_TO.set(translationModel.getLanguageModelTo().getAbbreviation());
                    showLanguageTo(translationModel.getLanguageModelTo().getName());
                    showLanguageFrom(translationModel.getLanguageModelFrom().getName());
                    showTranslate(translationModel);
                    showFavoriteButton();
                    showButtonStatusIsFavorite(translationModel.isFavorite());
                });
    }

    @Override
    public void getWordStatusFavoriteOrNo(int id) {
        FavoriteAndHistoryRx.getFavoriteStatusById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(status -> {
                    if (status != null) {
                        showButtonStatusIsFavorite(status);
                    }
                });
    }

    @Override
    public void getLanguageFromTranslate() {
        openChangeLanguageDialog(ChangeLanguageDialogType.FROM, Preferences.LANGUAGE_FROM.getString());
    }

    @Override
    public void setChangedLanguage(ChangeLanguageDialogType changeLanguageDialogType, LanguageModel languageModel) {
        if (changeLanguageDialogType == ChangeLanguageDialogType.FROM) {
            if (languageModel.getAbbreviation().equals(Preferences.LANGUAGE_TO.getString())) {
                replaceLanguage();
            } else {
                Preferences.LANGUAGE_FROM.set(languageModel.getAbbreviation());
                showLanguageFrom(languageModel.getName());
            }
        } else if (changeLanguageDialogType == ChangeLanguageDialogType.TO) {
            if (languageModel.getAbbreviation().equals(Preferences.LANGUAGE_FROM.getString())) {
                replaceLanguage();
            } else {
                Preferences.LANGUAGE_TO.set(languageModel.getAbbreviation());
                showLanguageTo(languageModel.getName());
            }
        }
    }

    @Override
    public void getLanguageToTranslate() {
        openChangeLanguageDialog(ChangeLanguageDialogType.TO, Preferences.LANGUAGE_TO.getString());
    }


    @Override
    public void replaceLanguage() {
        String from = Preferences.LANGUAGE_FROM.getString();
        String to = Preferences.LANGUAGE_TO.getString();
        Preferences.LANGUAGE_FROM.set(to);
        Preferences.LANGUAGE_TO.set(from);
        showReplacedLanguage();
    }

    @Override
    public void addOrDeleteTranslateToFavorite(int id, boolean favoriteStatus) {
        if (favoriteStatus) {
            FavoriteAndHistoryRx.deleteItemFromFavorite(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            showButtonStatusIsFavorite(false);
        } else {
            FavoriteAndHistoryRx.addToFavorite(id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            showButtonStatusIsFavorite(true);
        }

    }

    @Override
    public void clearText() {
        hideNoInternetConnectionError();
        showClearText();
        hideFavoriteButton();
    }

    private void showTranslate(TranslationModel translationModel) {
        if (getView() != null) {
            getView().showTranslate(translationModel);
        }
    }

    private void updateTranslatorPanel(String word, int position, int size) {
        if (getView() != null) {
            getView().updateTranslatorPanel(word, position, size);
        }
    }

    private void goTranslatorPanelToStart() {
        if (getView() != null) {
            getView().goTranslatorPanelToStart();
        }
    }

    private void showProgress() {
        if (getView() != null) {
            getView().showProgress();
        }
    }

    private void hideProgress() {
        if (getView() != null) {
            getView().hideProgress();
        }
    }

    private void showNoInternetConnectionError() {
        if (getView() != null) {
            getView().showNoInternetConnectionError();
        }
    }

    private void hideNoInternetConnectionError() {
        if (getView() != null) {
            getView().hideNoInternetConnectionError();
        }
    }

    private void openChangeLanguageDialog(ChangeLanguageDialogType changeLanguageDialogType, String language) {
        if (getView() != null) {
            getView().openChangeLanguageDialog(changeLanguageDialogType, language);
        }
    }

    private void showLanguageTo(String language) {
        if (getView() != null) {
            getView().showLanguageTo(Utils.languageFirstSymbolToUpperCase(language));
        }
    }

    private void showLanguageFrom(String language) {
        if (getView() != null) {
            getView().showLanguageFrom(Utils.languageFirstSymbolToUpperCase(language));
        }
    }

    private void showReplacedLanguage() {
        if (getView() != null) {
            getView().showReplacedLanguage();
        }
    }

    private void showFavoriteButton() {
        if (getView() != null) {
            getView().showFavoriteButton();
        }
    }

    private void hideFavoriteButton() {
        if (getView() != null) {
            getView().hideFavoriteButton();
        }
    }

    private void showButtonStatusIsFavorite(boolean isFavorite) {
        if (getView() != null) {
            getView().showButtonStatusIsFavorite(isFavorite);
        }
    }


    private void showClearText() {
        if (getView() != null) {
            getView().showClearText();
        }
    }

    private void setDictionaryVisible() {
        if (getView() != null) {
            getView().setDictionaryVisible();
        }
    }

    private void setDictionaryInvisible() {
        if (getView() != null) {
            getView().setDictionaryInvisible();
        }
    }

}
