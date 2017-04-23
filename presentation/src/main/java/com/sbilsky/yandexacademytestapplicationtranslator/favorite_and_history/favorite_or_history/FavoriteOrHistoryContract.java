package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history;

import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

interface FavoriteOrHistoryContract {
    interface IFavoriteOrHistoryView {
        void showData(List<FavoriteOrHistoryModel> favoriteOrHistoryModels);

        void showClearCategoryIcon();

        void hideClearCategoryIcon();

        void showNoResultInCategory();

        void hideNoResultInCategory();

        void showNoResultFoundInCategory();

        void hideNoResultFoundInCategory();

        void hideRecyclerView();

        void showRecyclerView();

        void hideSearchView();

        void showSearchView();

        void showDeleteAllDialog(String categoryName);

        void showDeleteItemDialog(int position);

        void showDeleteItem(int position);

        void showAddedOrDeletedFavorite(int position, boolean status);

        void updateAdapterBySearch(List<FavoriteOrHistoryModel> favoriteOrHistoryModelsSearch);
    }

    interface IFavoriteOrHistoryPresenter {
        void getDataFromFragmentType(FragmentType fragmentType);

        void getDataForDeleteAllDialog(String title);

        void setItemDeleted(int position, FavoriteOrHistoryModel itemDeleted);

        void deleteItem(FragmentType fragmentType, int position, FavoriteOrHistoryModel itemDeleted);

        void deleteAllItems(FragmentType fragmentType);

        void saveOrDeleteFavorite(int position, FavoriteOrHistoryModel itemDeleted);

        void filterByText(List<FavoriteOrHistoryModel> favoriteOrHistoryModels, String text);

        void checkListSizes(int listDisplayedSize, int totalList_size);
    }
}
