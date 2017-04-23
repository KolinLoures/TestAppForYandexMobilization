package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class DeleteAllDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String CATEGORY_TAG = "category";
    private DeleteAllDialogFragmentCallBack deleteAllDialogFragmentCallBack;

    public static DeleteAllDialogFragment newInstance(String categoryName) {
        Bundle args = new Bundle();
        args.putString(CATEGORY_TAG, categoryName);
        DeleteAllDialogFragment fragment = new DeleteAllDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface DeleteAllDialogFragmentCallBack {
        void onButtonOkPressed();
    }

    public void registerCallBack(DeleteAllDialogFragmentCallBack deleteAllDialogFragmentCallBack) {
        this.deleteAllDialogFragmentCallBack = deleteAllDialogFragmentCallBack;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getContext(), R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_all_favorite_or_history_dialog, container, false);
        String category = "";
        if (getArguments().getString(CATEGORY_TAG) != null) {
            category = getArguments().getString(CATEGORY_TAG);
        }
        initViews(rootView, category);
        return rootView;
    }

    private void initViews(View rootView, String category) {
        AppCompatTextView appCompatTextViewMessage = (AppCompatTextView) rootView.findViewById(R.id.delete_all_favorite_or_history_dialog_message);
        appCompatTextViewMessage.setText(getString(R.string.delete_all_favorite_or_history_question) + " " + category.toLowerCase() + " ?");
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.delete_all_favorite_or_history_toolbar);
        toolbar.setTitle(category);
        AppCompatButton appCompatButtonOk = (AppCompatButton) rootView.findViewById(R.id.delete_all_favorite_or_history_dialog_ok);
        appCompatButtonOk.setOnClickListener(this);
        AppCompatButton appCompatButtonCancel = (AppCompatButton) rootView.findViewById(R.id.delete_all_favorite_or_history_dialog_cancel);
        appCompatButtonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_all_favorite_or_history_dialog_ok:
                if (deleteAllDialogFragmentCallBack != null)
                    deleteAllDialogFragmentCallBack.onButtonOkPressed();
                dismiss();
                break;
            case R.id.delete_all_favorite_or_history_dialog_cancel:
                dismiss();
                break;
        }
    }
}
