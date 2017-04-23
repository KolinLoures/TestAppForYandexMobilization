package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.dialogs.about_author_dialog.models.AuthorLinkModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AuthorLinkAdapter extends RecyclerView.Adapter<AuthorLinkAdapter.AuthorLinkViewHolder> {
    private List<AuthorLinkModel> authorLinkModels;
    private OnAuthorLinkClick onAuthorLinkClick;

    public AuthorLinkAdapter(List<AuthorLinkModel> authorLinkModels, OnAuthorLinkClick onAuthorLinkClick) {
        this.authorLinkModels = authorLinkModels;
        this.onAuthorLinkClick = onAuthorLinkClick;
    }

    @Override
    public AuthorLinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_author_recycler_view_item, parent, false);
        return new AuthorLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuthorLinkViewHolder holder, int position) {
        holder.appCompatImageViewIcon.setImageResource(authorLinkModels.get(position).getIconId());
        holder.appCompatTextViewTitle.setText(authorLinkModels.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return authorLinkModels.size();
    }

    class AuthorLinkViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewTitle;
        AppCompatImageView appCompatImageViewIcon;

        AuthorLinkViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(view -> {
                if (onAuthorLinkClick != null)
                    onAuthorLinkClick.onAuthorLinkClicked(authorLinkModels.get(getAdapterPosition()).getLink(), authorLinkModels.get(getAdapterPosition()).getName());
            });
        }

        private void initViews(View itemView) {
            appCompatTextViewTitle = (AppCompatTextView) itemView.findViewById(R.id.about_author_recycler_view_title);
            appCompatImageViewIcon = (AppCompatImageView) itemView.findViewById(R.id.about_author_recycler_view_icon);
        }
    }
}
