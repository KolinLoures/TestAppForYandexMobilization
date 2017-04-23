package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sbilsky.customviewslibrary.views.CustomSearchView;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.ExtendedFragmentLifeCycle;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.adapters.FavoriteOrHistoryAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.adapters.FavoriteOrHistoryAdaptersContract;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.dialogs.DeleteAllDialogFragment;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.dialogs.DeleteItemDialog;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;
import com.sbilsky.yandexacademytestapplicationtranslator.navigation_activity.NavigationActivityContract;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteOrHistoryFrg extends Fragment implements FavoriteOrHistoryContract.IFavoriteOrHistoryView, ExtendedFragmentLifeCycle, FavoriteOrHistoryDeleteAllContract {
    public static final String FRAGMENT_TYPE_KEY = "FragmentType";
    private final String DIALOG_DELETE_ALL_TAG = "DeleteAllDialog";
    private final String DIALOG_DELETE_ITEM_TAG = "DeleteItemDialog";
    private FragmentType fragmentType;
    private RecyclerView recyclerView;
    private CustomSearchView customSearchView;
    private LinearLayout linearLayoutNoResultInCategory, linearLayoutNoResultFoundInCategory;
    private AppCompatTextView appCompatTextViewNoResultInCategoryDescription, appCompatTextViewNoResultFoundInCategoryDescription;
    private FavoriteOrHistoryPresenter favoriteOrHistoryPresenter;
    private NoDataForDeletingCallBack noDataForDeletingCallBack;
    private DeleteAllDialogFragment.DeleteAllDialogFragmentCallBack deleteAllDialogFragmentCallBack;
    private DeleteItemDialog.DeleteItemDialogFragmentCallBack deleteItemDialogFragmentCallBack;
    private OnFragmentCreatedCallBack onFragmentCreatedCallBackCallBack;
    private int positionForDeleting = -1;
    private FavoriteOrHistoryModel favoriteOrHistoryModelForDeleting = null;
    private final String POSITION_FOR_DELETING_TAG = "positionForDeleting";
    private final String MODEL_FOR_DELETING_TAG = "modelForDeleting";
    private FavoriteOrHistoryAdaptersContract favoriteOrHistoryAdaptersContract;

    public interface NoDataForDeletingCallBack {
        void noDataForDeleting();

        void haveDataForDeleting();
    }

    public interface OnFragmentCreatedCallBack {
        void onFragmentCreated();
    }

    public static FavoriteOrHistoryFrg newInstance(FragmentType fragmentType) {
        Bundle args = new Bundle();
        args.putSerializable(FRAGMENT_TYPE_KEY, fragmentType);
        FavoriteOrHistoryFrg fragment = new FavoriteOrHistoryFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onHideFragment() {
        // ignore
    }

    @Override
    public void onShowFragment() {
        favoriteOrHistoryPresenter.getDataFromFragmentType(fragmentType);
    }

    @Override
    public void onDeleteAllButtonPressed() {
        if (fragmentType == FragmentType.FAVORITE) {
            favoriteOrHistoryPresenter.getDataForDeleteAllDialog(getString(R.string.fragment_favorite_title));
        } else if (fragmentType == FragmentType.HISTORY) {
            favoriteOrHistoryPresenter.getDataForDeleteAllDialog(getString(R.string.fragment_history_title));
        }

    }


    public void registerNoDataForDeletingCallBack(NoDataForDeletingCallBack noDataForDeletingCallBack) {
        this.noDataForDeletingCallBack = noDataForDeletingCallBack;
    }

    public void registerOnFragmentCreatedCallBack(OnFragmentCreatedCallBack onFragmentCreatedCallBack) {
        this.onFragmentCreatedCallBackCallBack = onFragmentCreatedCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_or_history_fragment, container, false);
        if (getArguments().getSerializable(FRAGMENT_TYPE_KEY) != null) {
            fragmentType = (FragmentType) getArguments().getSerializable(FRAGMENT_TYPE_KEY);
        }
        initDialogDeleteAllCallBack();
        checkIsDialogDeleteAllCallBackLose(savedInstanceState);
        initDialogDeleteItemCallBack();
        checkIsDialogDeleteItemCallBackLose(savedInstanceState);
        restorePositionForDeletingState(savedInstanceState);
        initRecyclerView(rootView);
        initViews(rootView);
        initPresenter();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (onFragmentCreatedCallBackCallBack != null) {
            onFragmentCreatedCallBackCallBack.onFragmentCreated();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void initDialogDeleteAllCallBack() {
        deleteAllDialogFragmentCallBack = () -> favoriteOrHistoryPresenter.deleteAllItems(fragmentType);
    }

    private void initDialogDeleteItemCallBack() {
        deleteItemDialogFragmentCallBack = () -> favoriteOrHistoryPresenter.deleteItem(fragmentType, positionForDeleting, favoriteOrHistoryModelForDeleting);
    }

    private void checkIsDialogDeleteAllCallBackLose(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            DeleteAllDialogFragment dpf = (DeleteAllDialogFragment) getChildFragmentManager()
                    .findFragmentByTag(DIALOG_DELETE_ALL_TAG);
            if (dpf != null) {
                if (deleteAllDialogFragmentCallBack != null)
                    dpf.registerCallBack(deleteAllDialogFragmentCallBack);
            }
        }
    }

    private void checkIsDialogDeleteItemCallBackLose(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            DeleteItemDialog dpf = (DeleteItemDialog) getChildFragmentManager()
                    .findFragmentByTag(DIALOG_DELETE_ITEM_TAG);
            if (dpf != null) {
                if (deleteItemDialogFragmentCallBack != null)
                    dpf.registerDeleteItemDialogFragmentCallBack(deleteItemDialogFragmentCallBack);
            }
        }
    }

    private void restorePositionForDeletingState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            positionForDeleting = savedInstanceState.getInt(POSITION_FOR_DELETING_TAG, -1);
            favoriteOrHistoryModelForDeleting = savedInstanceState.getParcelable(MODEL_FOR_DELETING_TAG);
        }
    }

    private void initRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favorite_or_history_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
    }

    private void initViews(View rootView) {
        linearLayoutNoResultInCategory = (LinearLayout) rootView.findViewById(R.id.favorite_or_history_no_result_in_category);
        appCompatTextViewNoResultInCategoryDescription = (AppCompatTextView) linearLayoutNoResultInCategory.findViewById(R.id.no_result_in_category_description);
        linearLayoutNoResultFoundInCategory = (LinearLayout) rootView.findViewById(R.id.no_result_found_in_category);
        appCompatTextViewNoResultFoundInCategoryDescription = (AppCompatTextView) linearLayoutNoResultFoundInCategory.findViewById(R.id.no_result_found_in_category_description);
        customSearchView = (CustomSearchView) rootView.findViewById(R.id.favorite_or_history_custom_search_view);
        customSearchView.setOnTextChangeListener(text -> {
            if (favoriteOrHistoryAdaptersContract != null)
                favoriteOrHistoryPresenter.filterByText(favoriteOrHistoryAdaptersContract.getData(), text);
        });
    }

    private void initPresenter() {
        if (favoriteOrHistoryPresenter == null) {
            favoriteOrHistoryPresenter = new FavoriteOrHistoryPresenter();
        }
        favoriteOrHistoryPresenter.attachView(this);
    }

    @Override
    public void showData(List<FavoriteOrHistoryModel> favoriteOrHistoryModels) {
        if (favoriteOrHistoryAdaptersContract == null) {
            FavoriteOrHistoryAdapter favoriteOrHistoryAdapter = new FavoriteOrHistoryAdapter(favoriteOrHistoryModels,
                    (position, favoriteOrHistoryModel) -> {
                        favoriteOrHistoryPresenter.setItemDeleted(position, favoriteOrHistoryModel);
                        positionForDeleting = position;
                        favoriteOrHistoryModelForDeleting = favoriteOrHistoryModel;
                    },
                    (position, id) -> ((NavigationActivityContract) getActivity()).showTranslateInTranslatorFragment(position, id, fragmentType),
                    (position, favoriteOrHistoryModel) -> favoriteOrHistoryPresenter.saveOrDeleteFavorite(position, favoriteOrHistoryModel));
            favoriteOrHistoryAdaptersContract = favoriteOrHistoryAdapter;
            recyclerView.setAdapter(favoriteOrHistoryAdapter);
        } else {
            favoriteOrHistoryAdaptersContract.updateData(favoriteOrHistoryModels);
        }
        favoriteOrHistoryPresenter.filterByText(favoriteOrHistoryModels, customSearchView.getText());
    }

    @Override
    public void showClearCategoryIcon() {
        if (noDataForDeletingCallBack != null)
            noDataForDeletingCallBack.haveDataForDeleting();
    }

    @Override
    public void hideClearCategoryIcon() {
        if (noDataForDeletingCallBack != null)
            noDataForDeletingCallBack.noDataForDeleting();
    }

    @Override
    public void showNoResultInCategory() {
        linearLayoutNoResultInCategory.setVisibility(View.VISIBLE);
        appCompatTextViewNoResultInCategoryDescription.setText(getString(R.string.no_result_in_category));
    }

    @Override
    public void hideNoResultInCategory() {
        linearLayoutNoResultInCategory.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultFoundInCategory() {
        linearLayoutNoResultFoundInCategory.setVisibility(View.VISIBLE);
        appCompatTextViewNoResultFoundInCategoryDescription.setText(getString(R.string.no_result_found_in_category));
    }

    @Override
    public void hideNoResultFoundInCategory() {
        linearLayoutNoResultFoundInCategory.setVisibility(View.GONE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchView() {
        customSearchView.clearText();
        customSearchView.setVisibility(View.GONE);
    }

    @Override
    public void showSearchView() {
        customSearchView.setVisibility(View.VISIBLE);
    }


    @Override
    public void showDeleteAllDialog(String categoryName) {
        DeleteAllDialogFragment deleteAllDialogFragment = DeleteAllDialogFragment.newInstance(categoryName);
        deleteAllDialogFragment.registerCallBack(deleteAllDialogFragmentCallBack);
        deleteAllDialogFragment.show(getChildFragmentManager(), DIALOG_DELETE_ALL_TAG);
    }

    @Override
    public void showDeleteItemDialog(int position) {
        DeleteItemDialog deleteItemDialog = new DeleteItemDialog();
        deleteItemDialog.registerDeleteItemDialogFragmentCallBack(deleteItemDialogFragmentCallBack);
        deleteItemDialog.show(getChildFragmentManager(), DIALOG_DELETE_ITEM_TAG);
    }

    @Override
    public void showDeleteItem(int position) {
        if (favoriteOrHistoryAdaptersContract != null) {
            favoriteOrHistoryAdaptersContract.itemRemove(position);
            favoriteOrHistoryPresenter.checkListSizes(recyclerView.getAdapter().getItemCount(), favoriteOrHistoryAdaptersContract.getData().size());
        }

    }

    @Override
    public void showAddedOrDeletedFavorite(int position, boolean status) {
        if (favoriteOrHistoryAdaptersContract != null)
            favoriteOrHistoryAdaptersContract.updateFavoriteStatus(position, status);
    }

    @Override
    public void updateAdapterBySearch(List<FavoriteOrHistoryModel> favoriteOrHistoryModelsSearch) {
        if (favoriteOrHistoryAdaptersContract != null)
            favoriteOrHistoryAdaptersContract.updateAdapterBySearch(favoriteOrHistoryModelsSearch);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (positionForDeleting >= 0)
            outState.putInt(POSITION_FOR_DELETING_TAG, positionForDeleting);
        if (favoriteOrHistoryModelForDeleting != null)
            outState.putParcelable(MODEL_FOR_DELETING_TAG, favoriteOrHistoryModelForDeleting);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteOrHistoryPresenter.detachView();
        noDataForDeletingCallBack = null;
        favoriteOrHistoryAdaptersContract = null;
        deleteAllDialogFragmentCallBack = null;
        deleteItemDialogFragmentCallBack = null;
        onFragmentCreatedCallBackCallBack = null;
    }
}
