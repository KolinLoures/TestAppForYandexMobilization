package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_program_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.webview_open_link_dialog.WebViewDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AboutProgramDialog extends DialogFragment {
    private AppCompatImageView appCompatImageViewLogo;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private final String WEB_VIEW_DIALOG_TAG = "WVAbout";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_program_dialog, container, false);
        initViews(rootView);
        loadImagesWithPicasso();
        return rootView;
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

    private void initViews(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.about_program_toolbar);
        toolbar.setTitle(R.string.about_program_title);
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(view -> dismiss());
        appCompatImageViewLogo = (AppCompatImageView) rootView.findViewById(R.id.about_program_image_view);
        appCompatImageViewLogo.setOnClickListener(view -> {
            if (getChildFragmentManager().findFragmentByTag(WEB_VIEW_DIALOG_TAG) == null) {
                WebViewDialog webViewDialog = WebViewDialog.newInstance(getString(R.string.project_mobilization_url), getString(R.string.project_mobilization_title));
                webViewDialog.show(getChildFragmentManager(), WEB_VIEW_DIALOG_TAG);
            }
        });
        contentLoadingProgressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.about_program_progress_bar);
    }

    private void loadImagesWithPicasso() {

        Picasso.with(getContext()).load(R.drawable.moblilization_logo).resize(500, 500)
                .into(appCompatImageViewLogo, new Callback() {
                    @Override
                    public void onSuccess() {
                        contentLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
