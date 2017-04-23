package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history;

import com.sbilsky.domain.rx_usecases.favorite_and_history.FavoriteAndHistoryRx;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.yandexacademytestapplicationtranslator.simpleMVP.AbstractPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Cвятослав Бильский s.bislky
 */

class FavoriteOrHistoryPresenter extends AbstractPresenter<FavoriteOrHistoryContract.IFavoriteOrHistoryView> implements FavoriteOrHistoryContract.IFavoriteOrHistoryPresenter {
    @Override
    public void getDataFromFragmentType(FragmentType fragmentType) {
        if (fragmentType == FragmentType.FAVORITE) {
            FavoriteAndHistoryRx.getAllFavorite()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favoriteOrHistoryModels -> {
                        if (favoriteOrHistoryModels.size() > 0) {
                            hideNoResultInCategory();
                            hideNoResultFoundInCategory();
                            showRecyclerView();
                            showSearchView();
                            showData(favoriteOrHistoryModels);
                            showClearCategoryIcon();
                        } else {
                            hideRecyclerView();
                            hideNoResultFoundInCategory();
                            hideSearchView();
                            showNoResultInCategory();
                            hideClearCategoryIcon();
                            updateAdapterBySearch(new ArrayList<>());
                        }
                    });

        } else if (fragmentType == FragmentType.HISTORY) {
            FavoriteAndHistoryRx.getAllHistory()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favoriteOrHistoryModels -> {
                        if (favoriteOrHistoryModels.size() > 0) {
                            hideNoResultInCategory();
                            hideNoResultFoundInCategory();
                            showRecyclerView();
                            showSearchView();
                            showData(favoriteOrHistoryModels);
                            showClearCategoryIcon();
                        } else {
                            hideRecyclerView();
                            hideSearchView();
                            hideNoResultFoundInCategory();
                            showNoResultInCategory();
                            hideClearCategoryIcon();
                            updateAdapterBySearch(new ArrayList<>());
                        }
                    });
        }
    }

    @Override
    public void getDataForDeleteAllDialog(String title) {
        showDeleteAllDialog(title);
    }

    @Override
    public void setItemDeleted(int position, FavoriteOrHistoryModel itemDeleted) {
        showDeleteItemDialog(position);
    }

    @Override
    public void deleteItem(FragmentType fragmentType, int position, FavoriteOrHistoryModel itemDeleted) {
        if (fragmentType == FragmentType.FAVORITE) {
            FavoriteAndHistoryRx.deleteItemFromFavorite(itemDeleted.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(it -> showDeleteItem(position));
        } else if (fragmentType == FragmentType.HISTORY) {
            FavoriteAndHistoryRx.deleteItemFromHistory(itemDeleted.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(it -> showDeleteItem(position));
        }


    }

    @Override
    public void deleteAllItems(FragmentType fragmentType) {
        if (fragmentType == FragmentType.FAVORITE) {
            FavoriteAndHistoryRx.deleteAllFavorite()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            hideClearCategoryIcon();
            hideRecyclerView();
            hideSearchView();
            updateAdapterBySearch(new ArrayList<>());
            hideNoResultFoundInCategory();
            showNoResultInCategory();
        } else if (fragmentType == FragmentType.HISTORY) {
            FavoriteAndHistoryRx.deleteAllHistory()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            hideClearCategoryIcon();
            hideRecyclerView();
            hideSearchView();
            updateAdapterBySearch(new ArrayList<>());
            hideNoResultFoundInCategory();
            showNoResultInCategory();
        }
    }

    @Override
    public void saveOrDeleteFavorite(int position, FavoriteOrHistoryModel itemDeleted) {
        if (itemDeleted.isFavorite()) {
            FavoriteAndHistoryRx.deleteItemFromFavorite(itemDeleted.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            showAddedOrDeletedFavorite(position, false);
        } else {
            FavoriteAndHistoryRx.addToFavorite(itemDeleted.getId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            showAddedOrDeletedFavorite(position, true);
        }

    }

    @Override
    public void filterByText(List<FavoriteOrHistoryModel> favoriteOrHistoryModels, String text) {
        List<FavoriteOrHistoryModel> favoriteOrHistoryModelsTemp = new ArrayList<>();
        for (int i = 0; i < favoriteOrHistoryModels.size(); i++) {
            if (favoriteOrHistoryModels.get(i).getTextFrom().contains(text) || favoriteOrHistoryModels.get(i).getTextTo().contains(text)) {
                favoriteOrHistoryModelsTemp.add(favoriteOrHistoryModels.get(i));
            }
        }
        if (favoriteOrHistoryModelsTemp.size() > 0) {
            hideNoResultFoundInCategory();
            showRecyclerView();
            updateAdapterBySearch(favoriteOrHistoryModelsTemp);
        } else if (favoriteOrHistoryModelsTemp.size() == 0 && !text.equals("")) {
            hideRecyclerView();
            showNoResultFoundInCategory();
            updateAdapterBySearch(favoriteOrHistoryModelsTemp);
        }

    }

    @Override
    public void checkListSizes(int listDisplayedSize, int totalListSize) {
        if (listDisplayedSize == 0 && totalListSize == 0) {
            hideNoResultFoundInCategory();
            hideRecyclerView();
            hideSearchView();
            showNoResultInCategory();
            hideClearCategoryIcon();
        } else if (listDisplayedSize == 0 && totalListSize > 0) {
            hideRecyclerView();
            showNoResultFoundInCategory();
        }
    }

    private void showData(List<FavoriteOrHistoryModel> favoriteOrHistoryModels) {
        if (getView() != null) {
            getView().showData(favoriteOrHistoryModels);
        }
    }

    private void showClearCategoryIcon() {
        if (getView() != null) {
            getView().showClearCategoryIcon();
        }

    }

    private void hideClearCategoryIcon() {
        if (getView() != null) {
            getView().hideClearCategoryIcon();
        }
    }

    private void showNoResultInCategory() {
        if (getView() != null) {
            getView().showNoResultInCategory();
        }
    }

    private void hideNoResultInCategory() {
        if (getView() != null) {
            getView().hideNoResultInCategory();
        }
    }

    private void showDeleteAllDialog(String categoryName) {
        if (getView() != null) {
            getView().showDeleteAllDialog(categoryName);
        }
    }

    private void showDeleteItemDialog(int position) {
        if (getView() != null) {
            getView().showDeleteItemDialog(position);
        }
    }

    private void showDeleteItem(int position) {
        if (getView() != null) {
            getView().showDeleteItem(position);
        }
    }

    private void showAddedOrDeletedFavorite(int position, boolean status) {
        if (getView() != null) {
            getView().showAddedOrDeletedFavorite(position, status);
        }
    }

    private void hideRecyclerView() {
        if (getView() != null) {
            getView().hideRecyclerView();
        }
    }

    private void showRecyclerView() {
        if (getView() != null) {
            getView().showRecyclerView();
        }
    }

    private void hideSearchView() {
        if (getView() != null) {
            getView().hideSearchView();
        }
    }

    private void showSearchView() {
        if (getView() != null) {
            getView().showSearchView();
        }
    }

    private void updateAdapterBySearch(List<FavoriteOrHistoryModel> favoriteOrHistoryModelsSearch) {
        if (getView() != null) {
            getView().updateAdapterBySearch(favoriteOrHistoryModelsSearch);
        }
    }

    private void showNoResultFoundInCategory() {
        if (getView() != null) {
            getView().showNoResultFoundInCategory();
        }
    }

    private void hideNoResultFoundInCategory() {
        if (getView() != null) {
            getView().hideNoResultFoundInCategory();
        }
    }
}
