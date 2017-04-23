package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.sbilsky.yandexacademytestapplicationtranslator.R;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class DeleteItemDialog extends DialogFragment {
    private DeleteItemDialogFragmentCallBack deleteItemDialogFragmentCallBack;

    public interface DeleteItemDialogFragmentCallBack {
        void onButtonDeletePressed();
    }

    public void registerDeleteItemDialogFragmentCallBack(DeleteItemDialogFragmentCallBack deleteItemDialogFragmentCallBack) {
        this.deleteItemDialogFragmentCallBack = deleteItemDialogFragmentCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_item_dialog, container, false);
        view.setOnClickListener(view1 -> {
            if (deleteItemDialogFragmentCallBack != null)
                deleteItemDialogFragmentCallBack.onButtonDeletePressed();
            dismiss();
        });
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
