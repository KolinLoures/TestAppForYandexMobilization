package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history;

import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface FavoriteOrHistoryAdaptersListeners {
    interface OnLongPressListener {
        void onItemLongPressed(int position, FavoriteOrHistoryModel favoriteOrHistoryModel);
    }

    interface OnClickElementListener {
        void onItemClick(int position, int id);
    }

    interface OnFavoriteButtonItemPressedListener {
        void onFavoriteButtonClicked(int position, FavoriteOrHistoryModel favoriteOrHistoryModel);
    }

}
