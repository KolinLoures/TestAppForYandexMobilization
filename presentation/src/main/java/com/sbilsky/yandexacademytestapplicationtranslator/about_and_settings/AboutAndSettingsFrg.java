package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.adapters.AboutAndSettingsAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.AboutAuthorDialog;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_program_dialog.AboutProgramDialog;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.AboutAndSettingsCategoryModel;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.SettingsModel;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.SwitchModel;
import com.sbilsky.yandexacademytestapplicationtranslator.webview_open_link_dialog.WebViewDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AboutAndSettingsFrg extends Fragment implements AboutAndSettingsContract.IAboutAndSettingsView {
    public static AboutAndSettingsFrg newInstance() {
        return new AboutAndSettingsFrg();
    }

    private RecyclerView recyclerViewSettings;
    private AboutAndSettingPresenter aboutAndSettingPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_and_settings_fragment, container, false);
        initToolbar(rootView);
        initRecyclerView(rootView);
        initPresenter();
        aboutAndSettingPresenter.loadSettings();
        return rootView;
    }

    private void initPresenter() {
        if (aboutAndSettingPresenter == null) {
            aboutAndSettingPresenter = new AboutAndSettingPresenter();
        }
        aboutAndSettingPresenter.attachView(this);
    }

    private void initToolbar(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.about_and_settings_toolbar);
        toolbar.setTitle(R.string.about_fragment_title);
    }

    private void initRecyclerView(View rootView) {
        recyclerViewSettings = (RecyclerView) rootView.findViewById(R.id.about_and_settings_recycler_view);
        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSettings.setHasFixedSize(true);
    }

    @Override
    public void initSettings(boolean dictionaryEnable) {
        List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels = new ArrayList<>();
        List<SettingsModel> settingsModelsGeneral = new ArrayList<>();
        settingsModelsGeneral.add(new SettingsModel(getString(R.string.about_and_settings_show_dictionary), new SwitchModel(dictionaryEnable)));
        AboutAndSettingsCategoryModel aboutAndSettingsCategoryModelGeneral = new AboutAndSettingsCategoryModel(getString(R.string.about_and_settings_general_settings_title), settingsModelsGeneral);
        List<SettingsModel> settingsModelsGeneralAbout = new ArrayList<>();
        settingsModelsGeneralAbout.add(new SettingsModel(getString(R.string.about_program_settings)));
        settingsModelsGeneralAbout.add(new SettingsModel(getString(R.string.about_author_settings)));
        settingsModelsGeneralAbout.add(new SettingsModel(getString(R.string.official_reference_settings)));
        AboutAndSettingsCategoryModel aboutAndSettingsCategoryModelInformation = new AboutAndSettingsCategoryModel(getString(R.string.about_and_settings_information_title), settingsModelsGeneralAbout);
        aboutAndSettingsCategoryModels.add(aboutAndSettingsCategoryModelGeneral);
        aboutAndSettingsCategoryModels.add(aboutAndSettingsCategoryModelInformation);
        showSettings(aboutAndSettingsCategoryModels);
    }

    @Override
    public void showSettings(List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels) {
        String aboutProgram = getString(R.string.about_program_settings);
        String aboutAuthor = getString(R.string.about_author_settings);
        String reference = getString(R.string.official_reference_settings);
        String showDictionary = getString(R.string.about_and_settings_show_dictionary);
        AboutAndSettingsAdapter aboutAndSettingsAdapter = new AboutAndSettingsAdapter(aboutAndSettingsCategoryModels, title -> {

            if (title.equals(aboutProgram)) {
                showAboutProgramDialog();
            } else if (title.equals(aboutAuthor)) {
                showAboutAuthorDialog();
            } else if (title.equals(reference)) {
                showReference();
            } else if (title.equals(showDictionary)) {
                aboutAndSettingPresenter.switchShowDictionaryStatus();
            }

        }, title -> {
            if (title.equals(showDictionary)) {
                aboutAndSettingPresenter.switchShowDictionaryStatus();
            }
        });
        recyclerViewSettings.setAdapter(aboutAndSettingsAdapter);
    }


    @Override
    public void showAboutProgramDialog() {
        String ABOUT_PROGRAM_DIALOG_TAG = "aboutProgram";
        if (getChildFragmentManager().findFragmentByTag(ABOUT_PROGRAM_DIALOG_TAG) == null) {
            AboutProgramDialog aboutProgramDialog = new AboutProgramDialog();
            aboutProgramDialog.show(getChildFragmentManager(), ABOUT_PROGRAM_DIALOG_TAG);
        }
    }

    @Override
    public void showAboutAuthorDialog() {
        String ABOUT_AUTHOR_DIALOG_TAG = "aboutAuthor";
        if (getChildFragmentManager().findFragmentByTag(ABOUT_AUTHOR_DIALOG_TAG) == null) {
            AboutAuthorDialog aboutAuthorDialog = new AboutAuthorDialog();
            aboutAuthorDialog.show(getChildFragmentManager(), ABOUT_AUTHOR_DIALOG_TAG);
        }
    }

    @Override
    public void showReference() {
        String WEB_VIEW_DIALOG_TAG = "wvAbout";
        if (getChildFragmentManager().findFragmentByTag(WEB_VIEW_DIALOG_TAG) == null) {
            WebViewDialog webViewDialog = WebViewDialog.newInstance(getString(R.string.official_reference_url), getString(R.string.official_reference_settings));
            webViewDialog.show(getChildFragmentManager(), WEB_VIEW_DIALOG_TAG);
        }
    }
}
