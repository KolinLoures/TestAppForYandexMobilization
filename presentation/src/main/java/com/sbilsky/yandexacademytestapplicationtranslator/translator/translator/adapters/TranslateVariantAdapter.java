package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.data.storage.models.VariantTranslateModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

class TranslateVariantAdapter extends RecyclerView.Adapter<TranslateVariantAdapter.TranslateVariantViewHolder> {
    private List<VariantTranslateModel> variantTranslateModels;

    TranslateVariantAdapter(List<VariantTranslateModel> variantTranslateModels) {
        this.variantTranslateModels = variantTranslateModels;
    }

    @Override
    public TranslateVariantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translator_fragment_translate_recycler_view_item_variant_of_translate, parent, false);
        return new TranslateVariantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslateVariantViewHolder holder, int position) {
        VariantTranslateModel variantTranslateModel = variantTranslateModels.get(position);
        if (variantTranslateModels.size() > 1) {
            SpannableString spannableString = new SpannableString((position + 1) + " " + variantTranslateModel.getTranslate());
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGray)), 0, 1, 0);
            holder.appCompatTextViewTo.setText(spannableString);
        } else {
            holder.appCompatTextViewTo.setText(variantTranslateModel.getTranslate());
        }
        if (variantTranslateModel.getFromVariant() != null) {
            holder.appCompatTextViewFrom.setVisibility(View.VISIBLE);
            holder.appCompatTextViewFrom.setText(variantTranslateModel.getFromVariant());
        } else {
            holder.appCompatTextViewFrom.setVisibility(View.GONE);
        }
        if (variantTranslateModel.getSamples() != null) {
            holder.appCompatTextViewVariants.setVisibility(View.VISIBLE);
            holder.appCompatTextViewVariants.setText(variantTranslateModel.getSamples());
        } else {
            holder.appCompatTextViewVariants.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return variantTranslateModels.size();
    }

    class TranslateVariantViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewFrom, appCompatTextViewTo, appCompatTextViewVariants;

        TranslateVariantViewHolder(View itemView) {
            super(itemView);
            appCompatTextViewFrom = (AppCompatTextView) itemView.findViewById(R.id.translate_recycler_view_item_from);
            appCompatTextViewTo = (AppCompatTextView) itemView.findViewById(R.id.translate_recycler_view_item_to);
            appCompatTextViewVariants = (AppCompatTextView) itemView.findViewById(R.id.translate_recycler_view_item_variants);
        }
    }
}
