package com.sbilsky.domain.favorite_and_history;

import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.data.storage.models.TranslationModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface IFavoriteAndHistoryData {
    List<FavoriteOrHistoryModel> getAllFavorite();

    List<FavoriteOrHistoryModel> getAllHistory();

    TranslationModel getHistoryFromPosition(int position);

    TranslationModel getFavoriteFromPosition(int position);

    TranslationModel getEntryById(int id);

    Boolean getFavoriteStatusById(int id);

    void addToFavorite(int id);

    void deleteAllFavorite();

    void deleteAllHistory();

    void deleteItemFromFavorite(int id);

    void deleteItemFromHistory(int id);

    void clearDataNoFavoriteAndNoHistory();

    void addToTable(String wordFromTranslate,
                    String wordToTranslate,
                    String dictionaryJson,
                    String languageFromTranslate,
                    String languageToTranslate);
}
