package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.sbilsky.customviewslibrary.views.CustomEditText;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.AdapterSwipeListener;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslatorPanelAdapterListeners;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslatorPanelUpdateContract;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslatorPanelAdapter extends RecyclerView.Adapter<TranslatorPanelAdapter.TranslatorPanelViewHolder> implements AdapterSwipeListener, TranslatorPanelUpdateContract {
    private int position = -1;
    private int size = 1;
    private String text = "";
    private TranslatorPanelAdapterListeners.OnPositionChangeListener translatorAdapterContract;
    private TranslatorPanelAdapterListeners.OnClickItemButtonListener onClickItemButtonListener;
    private TranslatorPanelAdapterListeners.OnTextChangeListener onTextChangeListener;
    private TranslatorPanelAdapterListeners.OnKeyboardHiddenListener onKeyboardHiddenListener;
    private TranslatorPanelAdapterListeners.OnEditTextFocusChangedListener onEditTextFocusChangedListener;

    public TranslatorPanelAdapter(TranslatorPanelAdapterListeners.OnPositionChangeListener translatorAdapterContract, TranslatorPanelAdapterListeners.OnClickItemButtonListener onClickItemButtonListener, TranslatorPanelAdapterListeners.OnTextChangeListener onTextChangeListener, TranslatorPanelAdapterListeners.OnKeyboardHiddenListener onKeyboardHiddenListener, TranslatorPanelAdapterListeners.OnEditTextFocusChangedListener onEditTextFocusChangedListener) {
        this.translatorAdapterContract = translatorAdapterContract;
        this.onClickItemButtonListener = onClickItemButtonListener;
        this.onTextChangeListener = onTextChangeListener;
        this.onKeyboardHiddenListener = onKeyboardHiddenListener;
        this.onEditTextFocusChangedListener = onEditTextFocusChangedListener;
    }

    @Override
    public TranslatorPanelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translator_fragment_recycler_view_translator_panel_item, parent, false);
        return new TranslatorPanelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslatorPanelViewHolder holder, int position) {
        holder.customEditText.setText(text);
        holder.customEditText.requestFocus();
        holder.customEditText.setSelection(text.length());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void swipeTo(int direction) {
        if (direction == ItemTouchHelper.START) {
            position = -1;
            text = "";
            if (onClickItemButtonListener != null)
                onClickItemButtonListener.onClearButtonClick();
        } else if (direction == ItemTouchHelper.END) {
            if (position < size - 1) {
                position = position + 1;
                if (translatorAdapterContract != null)
                    translatorAdapterContract.onPositionBySwipeChanged(position);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void updateFocus() {
        notifyDataSetChanged();
    }

    @Override
    public void updateData(String text, int position, int size) {
        this.text = text;
        this.position = position;
        this.size = size;
        notifyDataSetChanged();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void restoreAdapterData(String text, int position, int size) {
        this.text = text;
        this.position = position;
        this.size = size;
        notifyDataSetChanged();
    }

    @Override
    public void goToStart() {
        this.position = -1;
        this.size = 1;
    }

    class TranslatorPanelViewHolder extends RecyclerView.ViewHolder {
        CustomEditText customEditText;
        AppCompatImageButton appCompatImageButtonClearText;

        TranslatorPanelViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            customEditText = (CustomEditText) itemView.findViewById(R.id.translator_fragment_edit_text);
            appCompatImageButtonClearText = (AppCompatImageButton) itemView.findViewById(R.id.translator_fragment_clear_button);

            customEditText.setOnEditorActionListener((textView, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE) {
                    if (onTextChangeListener != null) {
                        onTextChangeListener.onTextChanged(textView.getText().toString());
                    }
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    if (onKeyboardHiddenListener != null)
                        onKeyboardHiddenListener.onKeyboardHidden();
                    return true;
                }
                return false;
            });
            customEditText.setOnKeyBoardHideListener(() -> {
                if (onKeyboardHiddenListener != null)
                    onKeyboardHiddenListener.onKeyboardHidden();
            });
            customEditText.setOnFocusChangeListener((view, b) -> {
                if (onEditTextFocusChangedListener != null)
                    onEditTextFocusChangedListener.onFocusChanged(b);
            });
            customEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 0) {
                        appCompatImageButtonClearText.setVisibility(View.VISIBLE);
                    } else {
                        appCompatImageButtonClearText.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    text = editable.toString();
                }
            });
            appCompatImageButtonClearText.setOnClickListener(view -> {
                text = "";
                position = -1;
                customEditText.setText(text);
                if (onClickItemButtonListener != null)
                    onClickItemButtonListener.onClearButtonClick();
            });

        }
    }
}
