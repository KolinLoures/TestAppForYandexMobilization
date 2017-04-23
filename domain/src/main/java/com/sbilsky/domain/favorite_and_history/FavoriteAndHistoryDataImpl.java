package com.sbilsky.domain.favorite_and_history;

import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.data.storage.usage.favorite_and_history.IFavoriteAndHistoryStorage;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteAndHistoryDataImpl implements IFavoriteAndHistoryData {
    private IFavoriteAndHistoryStorage iFavoriteAndHistoryStorage;

    public FavoriteAndHistoryDataImpl(IFavoriteAndHistoryStorage iFavoriteAndHistoryStorage) {
        this.iFavoriteAndHistoryStorage = iFavoriteAndHistoryStorage;
    }

    @Override
    public List<FavoriteOrHistoryModel> getAllFavorite() {
        return iFavoriteAndHistoryStorage.getAllFavorite();
    }

    @Override
    public List<FavoriteOrHistoryModel> getAllHistory() {
        return iFavoriteAndHistoryStorage.getAllHistory();
    }

    @Override
    public TranslationModel getHistoryFromPosition(int position) {
        return iFavoriteAndHistoryStorage.getHistoryFromPosition(position);
    }

    @Override
    public TranslationModel getFavoriteFromPosition(int position) {
        return iFavoriteAndHistoryStorage.getFavoriteFromPosition(position);
    }

    @Override
    public TranslationModel getEntryById(int id) {
        return iFavoriteAndHistoryStorage.getEntryById(id);
    }

    @Override
    public Boolean getFavoriteStatusById(int id) {
        return iFavoriteAndHistoryStorage.getFavoriteStatusById(id);
    }

    @Override
    public void addToFavorite(int id) {
        iFavoriteAndHistoryStorage.addToFavorite(id);
    }

    @Override
    public void deleteAllFavorite() {
        iFavoriteAndHistoryStorage.deleteAllFavorite();
    }

    @Override
    public void deleteAllHistory() {
        iFavoriteAndHistoryStorage.deleteAllHistory();
    }

    @Override
    public void deleteItemFromFavorite(int id) {
        iFavoriteAndHistoryStorage.deleteItemFromFavorite(id);
    }

    @Override
    public void deleteItemFromHistory(int id) {
        iFavoriteAndHistoryStorage.deleteItemFromHistory(id);
    }

    @Override
    public void clearDataNoFavoriteAndNoHistory() {
        iFavoriteAndHistoryStorage.clearDataNoFavoriteAndNoHistory();
    }

    @Override
    public void addToTable(String wordFromTranslate, String wordToTranslate, String dictionaryJson, String languageFromTranslate, String languageToTranslate) {
        iFavoriteAndHistoryStorage.addToTable(wordFromTranslate, wordToTranslate, dictionaryJson, languageFromTranslate, languageToTranslate);
    }
}
