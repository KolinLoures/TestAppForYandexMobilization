package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sbilsky.customviewslibrary.views.NoScrollLinearLayoutManager;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.FragmentType;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.ChangeLanguageDialog;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.ChangeLanguageDialogType;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.adapters.TranslateAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.adapters.TranslatorPanelAdapter;
import com.sbilsky.data.storage.models.TranslationModel;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.BorderItemDecoration;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslateAdapterContract;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslatorPanelTouchHelperCallBack;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslatorPanelUpdateContract;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.ExtendedFragmentLifeCycle;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.Utils;


/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslatorFrg extends Fragment implements TranslatorContract.ITranslatorView, View.OnClickListener, TranslatorFrgLoadingIniciator, ExtendedFragmentLifeCycle {
    private AppCompatTextView appCompatTextViewChangeFromLanguage, appCompatTextViewChangeToLanguage, appCompatTextViewToTranslateText;
    private AppCompatImageButton appCompatImageButtonAddToFavorite;
    private RecyclerView recyclerViewTranslatorPanel, recyclerViewTranslation;
    private BorderItemDecoration borderItemDecoration;
    private TranslatorPanelUpdateContract translatorPanelUpdateContract;
    private TranslateAdapterContract translateAdapterContract;
    private Boolean favoriteStatus;
    private int idForSavingOnDeletedFromFavorite = -1;
    private TranslatorPresenter translatorPresenter;
    public static final String CHANGE_LANGUAGE_DIALOG_TAG = "ChangeLanguageDialog";
    private final String TRANSLATOR_PANEL_ADAPTER_TEXT_TAG = "Text";
    private final String TRANSLATOR_PANEL_ADAPTER_SIZE_TAG = "Size";
    private final String TRANSLATOR_PANEL_ADAPTER_POSITION_TAG = "Position";
    private final String TRANSLATE_ADAPTER_DATA_TAG = "List<TranslateModel>";
    private final String TRANSLATE_TAG = "Translate";
    private final String LANGUAGE_FROM_TAG = "From";
    private final String LANGUAGE_TO_TAG = "To";
    private final String FAVORITE_STATUS_KEY = "favorite";
    private final String ID_FOR_SAVE_TO_FAVORITE_KEY = "word";
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private LinearLayout includeWithErrorLayout;
    private ChangeLanguageDialog.ChangeLanguageDialogCallBack changeLanguageDialogCallBack;

    public static TranslatorFrg newInstance() {
        return new TranslatorFrg();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.translator_fragment, container, false);
        initViews(rootView);
        initRecyclerViews(rootView);
        restoreTranslatorPanelData(savedInstanceState);
        restoreTranslateData(savedInstanceState);
        restoreData(savedInstanceState);
        initCallBackForChangeLanguageDialog();
        checkIsCallBackLose(savedInstanceState);
        initPresenter();
        if (savedInstanceState == null) {
            translatorPresenter.getLanguageName();
        }

        translatorPresenter.checkIsDictionaryShow();
        return rootView;
    }

    private void initPresenter() {
        if (translatorPresenter == null) {
            translatorPresenter = new TranslatorPresenter();
        }
        translatorPresenter.attachView(this);
    }

    private void initViews(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.translator_fragment_toolbar);
        contentLoadingProgressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.translator_fragment_progress_bar);
        includeWithErrorLayout = (LinearLayout) rootView.findViewById(R.id.translator_fragment_no_internet_connection_layout);
        AppCompatButton appCompatButtonTryAgain = (AppCompatButton) includeWithErrorLayout.findViewById(R.id.no_internet_connection_layout_try_again_button);
        appCompatButtonTryAgain.setOnClickListener(this);
        appCompatTextViewChangeFromLanguage = (AppCompatTextView) rootView.findViewById(R.id.translator_fragment_change_from_language_text_view);
        appCompatTextViewChangeFromLanguage.setOnClickListener(this);
        appCompatTextViewChangeToLanguage = (AppCompatTextView) rootView.findViewById(R.id.translator_fragment_change_to_language_text_view);
        appCompatTextViewChangeToLanguage.setOnClickListener(this);
        appCompatTextViewToTranslateText = (AppCompatTextView) rootView.findViewById(R.id.translator_fragment_to_translate_text);
        AppCompatImageButton appCompatImageButtonReplaceLanguage = (AppCompatImageButton) rootView.findViewById(R.id.translator_fragment_replace_language);
        appCompatImageButtonReplaceLanguage.setOnClickListener(this);
        appCompatImageButtonAddToFavorite = (AppCompatImageButton) rootView.findViewById(R.id.translator_fragment_add_to_favorite_button);
        appCompatImageButtonAddToFavorite.setOnClickListener(this);

    }

    private void initRecyclerViews(final View rootView) {
        recyclerViewTranslatorPanel = (RecyclerView) rootView.findViewById(R.id.translator_fragment_recycler_view_translator_panel);
        NoScrollLinearLayoutManager noScrollLinearLayoutManager = new NoScrollLinearLayoutManager(getContext());
        noScrollLinearLayoutManager.setScrollEnabled(false);
        noScrollLinearLayoutManager.setStackFromEnd(true);
        recyclerViewTranslatorPanel.setLayoutManager(noScrollLinearLayoutManager);
        recyclerViewTranslatorPanel.setHasFixedSize(true);
        borderItemDecoration = new BorderItemDecoration(Utils.convertDpToPixel(3, getContext()));
        recyclerViewTranslatorPanel.addItemDecoration(borderItemDecoration);
        TranslatorPanelAdapter translatorPanelAdapter = new TranslatorPanelAdapter(
                position -> translatorPresenter.getHistoryItemFromPosition(position),
                () -> translatorPresenter.clearText(),
                text -> translatorPresenter.loadTextTranslate(text),
                rootView::requestFocus,
                isFocus -> {
                    if (isFocus) {
                        borderItemDecoration.setBoarderColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    } else {
                        borderItemDecoration.setBoarderColor(ContextCompat.getColor(getContext(), R.color.colorGray));
                    }
                    if (!recyclerViewTranslatorPanel.isComputingLayout())
                        recyclerViewTranslatorPanel.invalidateItemDecorations();
                });
        translatorPanelUpdateContract = translatorPanelAdapter;
        recyclerViewTranslatorPanel.setAdapter(translatorPanelAdapter);
        TranslatorPanelTouchHelperCallBack translatorPanelTouchHelperCallBack = new TranslatorPanelTouchHelperCallBack(translatorPanelAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(translatorPanelTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerViewTranslatorPanel);
        recyclerViewTranslation = (RecyclerView) rootView.findViewById(R.id.translator_fragment_recycler_view_with_translate);
        recyclerViewTranslation.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTranslation.setHasFixedSize(true);
    }

    private void restoreTranslatorPanelData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (translatorPanelUpdateContract != null) {
                String text = savedInstanceState.getString(TRANSLATOR_PANEL_ADAPTER_TEXT_TAG);
                int position = savedInstanceState.getInt(TRANSLATOR_PANEL_ADAPTER_POSITION_TAG);
                int size = savedInstanceState.getInt(TRANSLATOR_PANEL_ADAPTER_SIZE_TAG);
                translatorPanelUpdateContract.restoreAdapterData(text, position, size);
            }
        }
    }

    private void restoreTranslateData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList(TRANSLATE_ADAPTER_DATA_TAG) != null) {
                TranslateAdapter translateAdapter = new TranslateAdapter(savedInstanceState.getParcelableArrayList(TRANSLATE_ADAPTER_DATA_TAG));
                translateAdapterContract = translateAdapter;
                recyclerViewTranslation.setAdapter(translateAdapter);
            } else {
                appCompatTextViewToTranslateText.setMovementMethod(new ScrollingMovementMethod());
            }
        }
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(TRANSLATE_TAG) != null) {
                String text = savedInstanceState.getString(TRANSLATE_TAG);
                appCompatTextViewToTranslateText.setText(text);
                if (text != null && !text.equals("")) {
                    favoriteStatus = savedInstanceState.getBoolean(FAVORITE_STATUS_KEY);
                    idForSavingOnDeletedFromFavorite = savedInstanceState.getInt(ID_FOR_SAVE_TO_FAVORITE_KEY);
                    showFavoriteButton();
                    showButtonStatusIsFavorite(favoriteStatus);
                }
            }
            if (savedInstanceState.getString(LANGUAGE_FROM_TAG) != null) {
                appCompatTextViewChangeFromLanguage.setText(savedInstanceState.getString(LANGUAGE_FROM_TAG));
            }
            if (savedInstanceState.getString(LANGUAGE_TO_TAG) != null) {
                appCompatTextViewChangeToLanguage.setText(savedInstanceState.getString(LANGUAGE_TO_TAG));
            }

        }
    }

    private void initCallBackForChangeLanguageDialog() {
        changeLanguageDialogCallBack = (changeLanguageDialogType, languageModel) -> translatorPresenter.setChangedLanguage(changeLanguageDialogType, languageModel);
    }

    private void checkIsCallBackLose(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ChangeLanguageDialog dpf = (ChangeLanguageDialog) getChildFragmentManager()
                    .findFragmentByTag(CHANGE_LANGUAGE_DIALOG_TAG);
            if (dpf != null) {
                if (changeLanguageDialogCallBack != null)
                    dpf.registerCallBack(changeLanguageDialogCallBack);
            }
        }
    }

    @Override
    public void showTranslate(TranslationModel translationModel) {
        translatorPresenter.checkIsDictionaryShow();
        idForSavingOnDeletedFromFavorite = translationModel.getId();
        appCompatTextViewToTranslateText.setText(translationModel.getTranslate());
        TranslateAdapter translateAdapter = null;
        if (translationModel.getTranslateModels() != null && translationModel.getTranslateModels().size() > 0) {
            translateAdapter = new TranslateAdapter(translationModel.getTranslateModels());
            translateAdapterContract = translateAdapter;
        } else {
            appCompatTextViewToTranslateText.setMovementMethod(new ScrollingMovementMethod());
        }
        recyclerViewTranslation.setAdapter(translateAdapter);
    }

    @Override
    public void updateTranslatorPanel(String word, int position, int size) {
        if (translatorPanelUpdateContract != null)
            translatorPanelUpdateContract.updateData(word, position, size);
    }

    @Override
    public void goTranslatorPanelToStart() {
        if (translatorPanelUpdateContract != null)
            translatorPanelUpdateContract.goToStart();
    }

    @Override
    public void showProgress() {
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        contentLoadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetConnectionError() {
        includeWithErrorLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideNoInternetConnectionError() {
        includeWithErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void openChangeLanguageDialog(ChangeLanguageDialogType changeLanguageDialogType, String language) {
        if (getChildFragmentManager().findFragmentByTag(CHANGE_LANGUAGE_DIALOG_TAG) == null) {
            ChangeLanguageDialog changeLanguageDialog = ChangeLanguageDialog.newInstance(changeLanguageDialogType, language);
            changeLanguageDialog.show(getChildFragmentManager(), CHANGE_LANGUAGE_DIALOG_TAG);
            changeLanguageDialog.registerCallBack(changeLanguageDialogCallBack);
        }
    }


    @Override
    public void showLanguageTo(String language) {
        appCompatTextViewChangeToLanguage.setText(language);
    }

    @Override
    public void showLanguageFrom(String language) {
        appCompatTextViewChangeFromLanguage.setText(language);
    }

    @Override
    public void showReplacedLanguage() {
        CharSequence to = appCompatTextViewChangeToLanguage.getText();
        CharSequence from = appCompatTextViewChangeFromLanguage.getText();
        appCompatTextViewChangeFromLanguage.setText(to);
        appCompatTextViewChangeToLanguage.setText(from);
    }

    @Override
    public void showFavoriteButton() {
        appCompatImageButtonAddToFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFavoriteButton() {
        appCompatImageButtonAddToFavorite.setVisibility(View.GONE);
    }

    @Override
    public void showButtonStatusIsFavorite(boolean isFavorite) {
        favoriteStatus = isFavorite;
        if (isFavorite) {
            appCompatImageButtonAddToFavorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_yellow_24dp));
        } else {
            appCompatImageButtonAddToFavorite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_bookmark_gray_24dp));
        }
    }


    @Override
    public void showClearText() {
        idForSavingOnDeletedFromFavorite = -1;
        appCompatTextViewToTranslateText.setText("");
        recyclerViewTranslation.setAdapter(null);
        translateAdapterContract = null;
    }

    @Override
    public void setDictionaryVisible() {
        recyclerViewTranslation.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDictionaryInvisible() {
        recyclerViewTranslation.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (translatorPanelUpdateContract != null) {
            outState.putString(TRANSLATOR_PANEL_ADAPTER_TEXT_TAG, translatorPanelUpdateContract.getText());
            outState.putInt(TRANSLATOR_PANEL_ADAPTER_POSITION_TAG, translatorPanelUpdateContract.getPosition());
            outState.putInt(TRANSLATOR_PANEL_ADAPTER_SIZE_TAG, translatorPanelUpdateContract.getSize());
        }
        if (translateAdapterContract != null) {
            outState.putParcelableArrayList(TRANSLATE_ADAPTER_DATA_TAG, translateAdapterContract.getData());
        }
        if (appCompatTextViewToTranslateText.getText() != null) {
            outState.putString(TRANSLATE_TAG, appCompatTextViewToTranslateText.getText().toString());
        }
        if (appCompatTextViewChangeFromLanguage.getText() != null) {
            outState.putString(LANGUAGE_FROM_TAG, appCompatTextViewChangeFromLanguage.getText().toString());
        }
        if (appCompatTextViewChangeToLanguage.getText() != null) {
            outState.putString(LANGUAGE_TO_TAG, appCompatTextViewChangeToLanguage.getText().toString());
        }
        if (favoriteStatus != null) {
            outState.putBoolean(FAVORITE_STATUS_KEY, favoriteStatus);
        }

        if (idForSavingOnDeletedFromFavorite != -1) {
            outState.putInt(ID_FOR_SAVE_TO_FAVORITE_KEY, idForSavingOnDeletedFromFavorite);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        translatorPresenter.detachView();
        translatorPanelUpdateContract = null;
        changeLanguageDialogCallBack = null;
        translateAdapterContract = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.translator_fragment_replace_language:
                translatorPresenter.replaceLanguage();
                break;
            case R.id.translator_fragment_change_from_language_text_view:
                translatorPresenter.getLanguageFromTranslate();
                break;
            case R.id.translator_fragment_change_to_language_text_view:
                translatorPresenter.getLanguageToTranslate();
                break;
            case R.id.translator_fragment_add_to_favorite_button:
                if (idForSavingOnDeletedFromFavorite != -1 && appCompatTextViewToTranslateText.getText() != null)
                    translatorPresenter.addOrDeleteTranslateToFavorite(idForSavingOnDeletedFromFavorite, favoriteStatus);
                break;
            case R.id.no_internet_connection_layout_try_again_button:
                if (translatorPanelUpdateContract.getText() != null && !translatorPanelUpdateContract.getText().equals(""))
                    translatorPresenter.loadTextTranslate(translatorPanelUpdateContract.getText());
                break;
        }
    }

    @Override
    public void getTranslateByTypeAndPosition(int position, int id, FragmentType fragmentTypeFrom) {
        switch (fragmentTypeFrom) {
            case FAVORITE:
                translatorPresenter.getEntryFromId(id);
                break;
            case HISTORY:
                translatorPresenter.getHistoryItemFromPosition(position);
                break;
        }
    }

    @Override
    public void onHideFragment() {
        // ignore
    }

    @Override
    public void onShowFragment() {
        if (translatorPanelUpdateContract != null) {
            translatorPanelUpdateContract.updateFocus();
            if (idForSavingOnDeletedFromFavorite != -1) {
                translatorPresenter.getWordStatusFavoriteOrNo(idForSavingOnDeletedFromFavorite);
            }
            translatorPresenter.checkIsDictionaryShow();
        }
    }
}
