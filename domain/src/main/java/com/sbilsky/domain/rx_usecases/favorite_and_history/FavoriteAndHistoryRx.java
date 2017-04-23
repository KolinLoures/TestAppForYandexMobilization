package com.sbilsky.domain.rx_usecases.favorite_and_history;

import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.domain.Data;

import java.util.List;

import rx.Observable;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteAndHistoryRx {
    public static Observable<List<FavoriteOrHistoryModel>> getAllFavorite() {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getAllFavorite()));
    }

    public static Observable<List<FavoriteOrHistoryModel>> getAllHistory() {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getAllHistory()));
    }

    public static Observable<TranslationModel> getHistoryFromPosition(int position) {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getHistoryFromPosition(position)));
    }

    public static Observable<TranslationModel> getFavoriteFromPosition(int position) {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getFavoriteFromPosition(position)));
    }

    public static Observable<TranslationModel> getEntryFromID(int id) {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getEntryById(id)));
    }

    public static Observable<Boolean> getFavoriteStatusById(int id) {
        return Observable.defer(() -> Observable.just(Data.favoriteAndHistory().getFavoriteStatusById(id)));
    }

    public static Observable<Boolean> addToFavorite(int id) {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().addToFavorite(id);
            return Observable.just(true);
        }));
    }

    public static Observable<Boolean> deleteAllFavorite() {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().deleteAllFavorite();
            return Observable.just(true);
        }));
    }

    public static Observable<Boolean> deleteAllHistory() {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().deleteAllHistory();
            return Observable.just(true);
        }));
    }

    public static Observable<Boolean> deleteItemFromFavorite(int id) {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().deleteItemFromFavorite(id);
            return Observable.just(true);
        }));
    }

    public static Observable<Boolean> deleteItemFromHistory(int id) {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().deleteItemFromHistory(id);
            return Observable.just(true);
        }));
    }

    public static Observable<Boolean> clearDataNoFavoriteAndNoHistory() {
        return Observable.defer(() -> Observable.just(0).flatMap(it -> {
            Data.favoriteAndHistory().clearDataNoFavoriteAndNoHistory();
            return Observable.just(true);
        }));
    }
}
