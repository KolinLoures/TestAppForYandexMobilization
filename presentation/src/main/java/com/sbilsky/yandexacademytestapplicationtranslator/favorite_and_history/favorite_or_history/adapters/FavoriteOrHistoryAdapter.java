package com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.adapters;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.favorite_and_history.favorite_or_history.FavoriteOrHistoryAdaptersListeners;
import com.sbilsky.data.storage.models.FavoriteOrHistoryModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class FavoriteOrHistoryAdapter extends RecyclerView.Adapter<FavoriteOrHistoryAdapter.FavoriteOrHistoryViewHolder> implements FavoriteOrHistoryAdaptersContract {
    private List<FavoriteOrHistoryModel> favoriteOrHistoryModels;
    private List<FavoriteOrHistoryModel> favoriteOrHistoryModelsDisplayed;
    private FavoriteOrHistoryAdaptersListeners.OnLongPressListener onLongPressListener;
    private FavoriteOrHistoryAdaptersListeners.OnClickElementListener onClickElementListener;
    private FavoriteOrHistoryAdaptersListeners.OnFavoriteButtonItemPressedListener onFavoriteButtonItemPressedListener;

    public FavoriteOrHistoryAdapter(List<FavoriteOrHistoryModel> favoriteOrHistoryModels, FavoriteOrHistoryAdaptersListeners.OnLongPressListener onLongPressListener, FavoriteOrHistoryAdaptersListeners.OnClickElementListener onClickElementListener, FavoriteOrHistoryAdaptersListeners.OnFavoriteButtonItemPressedListener onFavoriteButtonItemPressedListener) {
        this.favoriteOrHistoryModels = favoriteOrHistoryModels;
        this.favoriteOrHistoryModelsDisplayed = favoriteOrHistoryModels;
        this.onLongPressListener = onLongPressListener;
        this.onClickElementListener = onClickElementListener;
        this.onFavoriteButtonItemPressedListener = onFavoriteButtonItemPressedListener;
    }

    @Override
    public FavoriteOrHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_or_history_recycler_view_item, parent, false);
        return new FavoriteOrHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteOrHistoryViewHolder holder, int position) {
        holder.appCompatTextViewFrom.setText(favoriteOrHistoryModelsDisplayed.get(position).getTextFrom());
        holder.appCompatTextViewTo.setText(favoriteOrHistoryModelsDisplayed.get(position).getTextTo());
        holder.appCompatTextViewLangAbbreviation.setText(favoriteOrHistoryModelsDisplayed.get(position).getLangAbbreviationFrom().toUpperCase() + "-" + favoriteOrHistoryModelsDisplayed.get(position).getLangAbbreviationTo().toUpperCase());
        if (favoriteOrHistoryModelsDisplayed.get(position).isFavorite()) {
            holder.appCompatImageButtonAddToFavorite.setImageResource(R.drawable.ic_bookmark_yellow_24dp);
        } else {
            holder.appCompatImageButtonAddToFavorite.setImageResource(R.drawable.ic_bookmark_gray_24dp);
        }
    }


    @Override
    public int getItemCount() {
        return favoriteOrHistoryModelsDisplayed.size();
    }

    @Override
    public List<FavoriteOrHistoryModel> getData() {
        return favoriteOrHistoryModels;
    }

    @Override
    public void itemRemove(int position) {
        int positionInAllData = getPositionInOriginalList(position);
        favoriteOrHistoryModelsDisplayed.remove(position);
        favoriteOrHistoryModels.remove(positionInAllData);
        notifyItemRemoved(position);
    }

    @Override
    public void updateFavoriteStatus(int position, boolean status) {
        int positionInAllData = getPositionInOriginalList(position);
        favoriteOrHistoryModelsDisplayed.get(position).setFavorite(status);
        favoriteOrHistoryModels.get(positionInAllData).setFavorite(status);
        notifyDataSetChanged();
    }

    @Override
    public void updateData(List<FavoriteOrHistoryModel> favoriteOrHistoryModels) {
        this.favoriteOrHistoryModels = favoriteOrHistoryModels;
    }

    @Override
    public void updateAdapterBySearch(List<FavoriteOrHistoryModel> favoriteOrHistoryModelsSearch) {
        favoriteOrHistoryModelsDisplayed = favoriteOrHistoryModelsSearch;
        notifyDataSetChanged();
    }

    private int getPositionInOriginalList(int posInDisplayed) {
        FavoriteOrHistoryModel favoriteOrHistoryModel = favoriteOrHistoryModelsDisplayed.get(posInDisplayed);
        return favoriteOrHistoryModels.indexOf(favoriteOrHistoryModel);
    }

    class FavoriteOrHistoryViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewFrom, appCompatTextViewTo, appCompatTextViewLangAbbreviation;
        AppCompatImageButton appCompatImageButtonAddToFavorite;

        FavoriteOrHistoryViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            appCompatTextViewFrom = (AppCompatTextView) itemView.findViewById(R.id.favorite_or_history_recycler_view_item_from);
            appCompatTextViewTo = (AppCompatTextView) itemView.findViewById(R.id.favorite_or_history_recycler_view_item_to);
            appCompatTextViewLangAbbreviation = (AppCompatTextView) itemView.findViewById(R.id.favorite_or_history_recycler_view_item_lang_abbreviation);
            appCompatImageButtonAddToFavorite = (AppCompatImageButton) itemView.findViewById(R.id.favorite_or_history_recycler_view_item_add_to_favorite);
            itemView.setOnLongClickListener(view -> {
                if (onLongPressListener != null)
                    onLongPressListener.onItemLongPressed(getAdapterPosition(), favoriteOrHistoryModelsDisplayed.get(getAdapterPosition()));
                return false;
            });
            itemView.setOnClickListener(view -> {
                if (onClickElementListener != null) {
                    onClickElementListener.onItemClick(getPositionInOriginalList(getAdapterPosition()), favoriteOrHistoryModelsDisplayed.get(getAdapterPosition()).getId());
                }
            });
            appCompatImageButtonAddToFavorite.setOnClickListener(view -> {
                if (onFavoriteButtonItemPressedListener != null)
                    onFavoriteButtonItemPressedListener.onFavoriteButtonClicked(getAdapterPosition(), favoriteOrHistoryModelsDisplayed.get(getAdapterPosition()));
            });
        }


    }
}
