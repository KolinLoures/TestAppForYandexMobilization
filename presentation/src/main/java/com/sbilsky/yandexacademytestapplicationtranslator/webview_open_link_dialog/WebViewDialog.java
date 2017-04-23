package com.sbilsky.yandexacademytestapplicationtranslator.webview_open_link_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sbilsky.yandexacademytestapplicationtranslator.R;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class WebViewDialog extends DialogFragment {
    private Toolbar toolbar;
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private String title, url;

    public static final String URL_BUNDLE_TAG = "Url";
    public static final String TITLE_BUNDLE_TAG = "title";

    public static WebViewDialog newInstance(String url, String title) {
        Bundle args = new Bundle();
        args.putString(URL_BUNDLE_TAG, url);
        args.putString(TITLE_BUNDLE_TAG, title);
        WebViewDialog fragment = new WebViewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.web_view_dialog, container, false);
        getData();
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.web_view_dialog_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        toolbar.setNavigationOnClickListener(view -> dismiss());
        contentLoadingProgressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.web_view_dialog_progress_bar);
        WebView webView = (WebView) rootView.findViewById(R.id.web_view_dialog_web_view);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setTitle("Loading...");
                showProgress();
                if (progress == 100) {
                    hideProgress();
                    setTitle(title);
                }


            }

        });
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl(url);
    }

    private void getData() {
        if (getArguments() != null) {
            url = getArguments().getString(URL_BUNDLE_TAG);
            title = getArguments().getString(TITLE_BUNDLE_TAG);
        }
    }

    private void setTitle(String title) {
        toolbar.setTitle(title);
    }

    private void showProgress() {
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        contentLoadingProgressBar.setVisibility(View.GONE);
    }

    private static class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.toString());
            return true;
        }
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
}
