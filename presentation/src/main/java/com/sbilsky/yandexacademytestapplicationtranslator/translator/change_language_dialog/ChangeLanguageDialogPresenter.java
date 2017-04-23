package com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog;

import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.domain.rx_usecases.language.LanguageRx;
import com.sbilsky.yandexacademytestapplicationtranslator.simpleMVP.AbstractPresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Cвятослав Бильский s.bislky
 */

class ChangeLanguageDialogPresenter extends AbstractPresenter<ChangeLanguageDialogContract.IChangeLanguageDialogView> implements ChangeLanguageDialogContract.IChangeLanguageDialogPresenter {
    @Override
    public void loadLanguages(String selected) {
        LanguageRx.getAllLanguage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(list -> {
                    int pos = mapToPosition(list, selected);
                    showLanguage(list, pos);
                    return pos;
                })
                .subscribe();


    }

    private void showLanguage(List<LanguageModel> languageModels, int active) {
        if (getView() != null) {
            getView().showLanguages(languageModels, active);
        }
    }

    private int mapToPosition(List<LanguageModel> languageModels, String selected) {
        int position = 0;
        for (int i = 0; i < languageModels.size(); i++) {
            if (languageModels.get(i).getAbbreviation().equals(selected)) {
                return i;
            }
        }
        return position;
    }
}
