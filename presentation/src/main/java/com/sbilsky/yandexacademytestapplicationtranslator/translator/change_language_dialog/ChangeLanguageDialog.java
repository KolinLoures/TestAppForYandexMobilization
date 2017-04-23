package com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.adapters.LanguageAdapter;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class ChangeLanguageDialog extends DialogFragment implements ChangeLanguageDialogContract.IChangeLanguageDialogView {
    private RecyclerView recyclerViewLanguages;
    private ChangeLanguageDialogPresenter changeLanguageDialogPresenter;
    private ChangeLanguageDialogCallBack changeLanguageDialogCallBack;
    public static final String CHANGE_LANGUAGE_DIALOG_TYPE_KEY = "ChangeLanguageDialogType";
    public static final String SELECTED_LANGUAGE_KEY = "language";
    private ChangeLanguageDialogType changeLanguageDialogType;

    public static ChangeLanguageDialog newInstance(ChangeLanguageDialogType changeLanguageDialogType, String selectedLanguage) {
        Bundle args = new Bundle();
        args.putSerializable(CHANGE_LANGUAGE_DIALOG_TYPE_KEY, changeLanguageDialogType);
        args.putString(SELECTED_LANGUAGE_KEY, selectedLanguage);
        ChangeLanguageDialog fragment = new ChangeLanguageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public interface ChangeLanguageDialogCallBack {
        void onLanguageChanged(ChangeLanguageDialogType changeLanguageDialogType, LanguageModel languageModel);
    }

    public void registerCallBack(ChangeLanguageDialogCallBack changeLanguageDialogCallBack) {
        this.changeLanguageDialogCallBack = changeLanguageDialogCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_language_dialog, container, false);
        if (getArguments().getSerializable(CHANGE_LANGUAGE_DIALOG_TYPE_KEY) != null) {
            changeLanguageDialogType = (ChangeLanguageDialogType) getArguments().getSerializable(CHANGE_LANGUAGE_DIALOG_TYPE_KEY);
        }
        String selected = "";
        if (getArguments().getString(SELECTED_LANGUAGE_KEY) != null) {
            selected = getArguments().getString(SELECTED_LANGUAGE_KEY);
        }

        initRecyclerView(rootView);
        initViews(rootView);
        if (changeLanguageDialogPresenter == null) {
            changeLanguageDialogPresenter = new ChangeLanguageDialogPresenter();
        }
        changeLanguageDialogPresenter.attachView(this);
        changeLanguageDialogPresenter.loadLanguages(selected);
        return rootView;
    }

    private void initRecyclerView(View rootView) {
        recyclerViewLanguages = (RecyclerView) rootView.findViewById(R.id.change_language_dialog_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLanguages.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewLanguages.getContext(),
                linearLayoutManager.getOrientation());
        recyclerViewLanguages.addItemDecoration(dividerItemDecoration);
        recyclerViewLanguages.setHasFixedSize(true);
    }

    private void initViews(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.change_language_dialog_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(view -> dismiss());
        String title;
        if (changeLanguageDialogType == ChangeLanguageDialogType.FROM) {
            title = getString(R.string.language_text);
        } else {
            title = getString(R.string.language_translate);
        }
        toolbar.setTitle(title);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getContext(), R.style.CustomDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void showLanguages(List<LanguageModel> languageModels, int active) {
        LanguageAdapter languageAdapter = new LanguageAdapter(languageModels, active, languageModel -> {
            if (changeLanguageDialogCallBack != null) {
                changeLanguageDialogCallBack.onLanguageChanged(changeLanguageDialogType, languageModel);
                dismiss();
            }
        });
        recyclerViewLanguages.setAdapter(languageAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        changeLanguageDialogPresenter.detachView();
    }
}
