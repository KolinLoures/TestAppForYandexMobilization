package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog;

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

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.adapters.AuthorLinkAdapter;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.models.AuthorLinkModel;
import com.sbilsky.yandexacademytestapplicationtranslator.webview_open_link_dialog.WebViewDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AboutAuthorDialog extends DialogFragment {
    private RecyclerView recyclerViewLinks;
    private final String WEB_VIEW_DIALOG_TAG = "webView";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_author_dialog, container, false);
        initViews(rootView);
        addItems();
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
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.about_author_dialog_toolbar);
        toolbar.setTitle(R.string.about_author_title);
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(view -> dismiss());
        recyclerViewLinks = (RecyclerView) rootView.findViewById(R.id.about_author_dialog_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLinks.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewLinks.getContext(),
                linearLayoutManager.getOrientation());
        recyclerViewLinks.addItemDecoration(dividerItemDecoration);
        recyclerViewLinks.setHasFixedSize(true);
    }

    private void addItems() {
        List<AuthorLinkModel> authorLinkModels = new ArrayList<>();
        AuthorLinkModel authorLinkModelGit = new AuthorLinkModel(R.drawable.github_mark, getString(R.string.about_author_dialog_git_hub_text), getString(R.string.about_author_dialog_git_hub_link));
        authorLinkModels.add(authorLinkModelGit);
        AuthorLinkModel authorLinkModelHH = new AuthorLinkModel(R.drawable.hh_logo, getString(R.string.about_author_dialog_hh_text), getString(R.string.about_author_dialog_hh_link));
        authorLinkModels.add(authorLinkModelHH);
        AuthorLinkModel authorLinkModelLinkedIn = new AuthorLinkModel(R.drawable.ic_linkedin_logo, getString(R.string.about_author_dialog_linked_in_text), getString(R.string.about_author_dialog_linked_in_link));
        authorLinkModels.add(authorLinkModelLinkedIn);
        AuthorLinkModel authorLinkModelVk = new AuthorLinkModel(R.drawable.ic_vk_social_network_logo, getString(R.string.about_author_dialog_vk_text), getString(R.string.about_author_dialog_vk_link));
        authorLinkModels.add(authorLinkModelVk);
        AuthorLinkAdapter authorLinkAdapter = new AuthorLinkAdapter(authorLinkModels, (link, title) -> {
            if (getChildFragmentManager().findFragmentByTag(WEB_VIEW_DIALOG_TAG) == null) {
                WebViewDialog webViewDialog = WebViewDialog.newInstance(link, title);
                webViewDialog.show(getChildFragmentManager(), WEB_VIEW_DIALOG_TAG);
            }
        });
        recyclerViewLinks.setAdapter(authorLinkAdapter);
    }
}
