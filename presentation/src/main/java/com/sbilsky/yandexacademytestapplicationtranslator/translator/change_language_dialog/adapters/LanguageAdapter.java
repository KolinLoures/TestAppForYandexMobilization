package com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.data.storage.models.LanguageModel;
import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.change_language_dialog.OnLanguageChangeListener;
import com.sbilsky.yandexacademytestapplicationtranslator.utils.Utils;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private List<LanguageModel> languageModels;
    private int activePosition;
    private OnLanguageChangeListener onLanguageChangeListener;

    public LanguageAdapter(List<LanguageModel> languageModels, int activePosition, OnLanguageChangeListener onLanguageChangeListener) {
        this.languageModels = languageModels;
        this.onLanguageChangeListener = onLanguageChangeListener;
        if (activePosition < languageModels.size()) {
            this.activePosition = activePosition;
        } else {
            throw new IndexOutOfBoundsException("activePosition no more than languageModels.size ");
        }
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_language_dialog_recycler_view_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        String languageName = Utils.languageFirstSymbolToUpperCase(languageModels.get(position).getName());
        holder.appCompatTextViewLanguage.setText(languageName);
        if (position == activePosition) {
            holder.appCompatTextViewLanguage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checkmark, 0);
        } else {
            holder.appCompatTextViewLanguage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

    }


    @Override
    public int getItemCount() {
        return languageModels.size();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewLanguage;

        LanguageViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private boolean isClick;

        private void initView(final View itemView) {
            appCompatTextViewLanguage = (AppCompatTextView) itemView.findViewById(R.id.change_language_dialog_recycler_view_item_text);
            appCompatTextViewLanguage.setOnClickListener(view -> {
                if (!isClick) {
                    isClick = true;
                    activePosition = getAdapterPosition();
                    notifyDataSetChanged();
                    appCompatTextViewLanguage.postDelayed(update, 300);
                }
            });
        }

        private Runnable update = new Runnable() {
            @Override
            public void run() {
                if (onLanguageChangeListener != null)
                    onLanguageChangeListener.onLanguageChanged(languageModels.get(activePosition));
            }
        };
    }
}
