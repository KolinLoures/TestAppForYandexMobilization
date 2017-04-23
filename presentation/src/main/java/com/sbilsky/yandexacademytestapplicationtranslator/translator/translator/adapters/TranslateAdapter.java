package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.data.storage.models.TranslateModel;
import com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper.TranslateAdapterContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslateAdapter extends RecyclerView.Adapter<TranslateAdapter.TranslateViewHolder> implements TranslateAdapterContract {
    private List<TranslateModel> translateModels;

    public TranslateAdapter(List<TranslateModel> translateModels) {
        this.translateModels = translateModels;
    }

    @Override
    public TranslateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translator_fragment_translate_recycler_view_item_translate, parent, false);
        return new TranslateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslateViewHolder holder, int position) {
        TranslateModel translateModel = translateModels.get(position);
        holder.appCompatTextViewTranslateFrom.setText(translateModel.getWordFromTranslate());
        holder.appCompatTextViewPartOfSpeech.setText(translateModel.getPartOfSpeech());
        if (translateModel.getVariantTranslateModels() != null && translateModel.getVariantTranslateModels().size() > 0) {
            holder.recyclerViewVariants.setVisibility(View.VISIBLE);
            holder.recyclerViewVariants.setAdapter(new TranslateVariantAdapter(translateModel.getVariantTranslateModels()));
        } else {
            holder.recyclerViewVariants.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return translateModels.size();
    }

    @Override
    public ArrayList<TranslateModel> getData() {
        return (ArrayList<TranslateModel>) translateModels;
    }

    class TranslateViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewTranslateFrom, appCompatTextViewPartOfSpeech;
        RecyclerView recyclerViewVariants;

        TranslateViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            initRecyclerViews(itemView);
        }

        private void initViews(View itemView) {
            appCompatTextViewTranslateFrom = (AppCompatTextView) itemView.findViewById(R.id.from_text);
            appCompatTextViewPartOfSpeech = (AppCompatTextView) itemView.findViewById(R.id.part_of_speech);

        }

        private void initRecyclerViews(View itemView) {
            recyclerViewVariants = (RecyclerView) itemView.findViewById(R.id.translate_variants);
            recyclerViewVariants.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewVariants.setHasFixedSize(true);
            recyclerViewVariants.setNestedScrollingEnabled(false);
        }
    }


}
