package com.sbilsky.domain.rx_usecases.language;

import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.domain.Data;

import java.util.List;

import rx.Observable;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguageRx {

    public static Observable<List<LanguageModel>> getAllLanguage() {
        return Observable.defer(() -> Observable.just(Data.languages().getAllLanguages()));
    }

    public static Observable<String> getLanguageNameByAbbreviation(String abbreviation) {
        return Observable.defer(() -> Observable.just(Data.languages().getLanguageNameByAbbreviation(abbreviation)));
    }

}
