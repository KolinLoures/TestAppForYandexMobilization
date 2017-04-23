package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.adapters;

import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface FavoriteOrHistoryAdaptersContract {

    List<FavoriteOrHistoryModel> getData();

    void itemRemove(int position);

    void updateFavoriteStatus(int position, boolean status);

    void updateData(List<FavoriteOrHistoryModel> favoriteOrHistoryModels);

    void updateAdapterBySearch(List<FavoriteOrHistoryModel> favoriteOrHistoryModelsSearch);
}
