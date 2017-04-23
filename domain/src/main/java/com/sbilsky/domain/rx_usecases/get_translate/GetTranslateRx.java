package com.sbilsky.domain.rx_usecases.get_translate;

import com.google.gson.Gson;
import com.sbilsky.data.server.RetrofitSingleton;
import com.sbilsky.data.server.TranslatorRetrofitInterface;
import com.sbilsky.data.server.pojo.Translation;
import com.sbilsky.data.storage.api.preferences.Preferences;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.domain.Data;

import java.io.IOException;

import rx.Observable;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class GetTranslateRx {
    public static Observable<TranslationModel> getTranslation(String text) {
        String languageFrom = Preferences.LANGUAGE_FROM.getString();
        String languageTo = Preferences.LANGUAGE_TO.getString();
        String fromToLanguages = languageFrom + "-" + languageTo;

        return Observable.zip(
                RetrofitSingleton.getInstance().create(TranslatorRetrofitInterface.class)
                        .getTranslate(TranslatorRetrofitInterface.API_KEY_TRNSLATION, text, fromToLanguages),
                RetrofitSingleton.getInstance().create(TranslatorRetrofitInterface.class)
                        .getDictionary(TranslatorRetrofitInterface.API_KEY_DICTIONARY, text, fromToLanguages, "ru").onErrorResumeNext(s -> Observable.just(null)),
                (translation, dictionary) -> {
                    try {
                        if (dictionary != null) {
                            return saveTranslateToDbAndMapToTranslationModel(text, translation.string(), dictionary.string(), languageFrom, languageTo);
                        } else {
                            return saveTranslateToDbAndMapToTranslationModel(text, translation.string(), "", languageFrom, languageTo);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    private static TranslationModel saveTranslateToDbAndMapToTranslationModel(String text, String jsonTranslation, String jsonDictionary, String languageFrom, String languageTo) {
        Gson gson = new Gson();
        Translation translation = gson.fromJson(jsonTranslation, Translation.class);
        Data.favoriteAndHistory().addToTable(text, translation.getText().get(0), jsonDictionary, languageFrom, languageTo);
        return Data.favoriteAndHistory().getHistoryFromPosition(0);
    }


}
