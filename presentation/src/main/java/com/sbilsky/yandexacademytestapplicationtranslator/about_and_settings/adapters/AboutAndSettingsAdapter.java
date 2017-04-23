package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.AboutAndSettingsCategoryModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AboutAndSettingsAdapter extends RecyclerView.Adapter<AboutAndSettingsAdapter.AboutAndSettingsViewHolder> {
    private List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels;
    private AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener;
    private AboutAndSettingsAdapterListeners.OnSwitchClickedListener onSwitchClickedListener;

    public AboutAndSettingsAdapter(List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels, AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener) {
        this.aboutAndSettingsCategoryModels = aboutAndSettingsCategoryModels;
        this.onItemClickListener = onItemClickListener;
    }

    public AboutAndSettingsAdapter(List<AboutAndSettingsCategoryModel> aboutAndSettingsCategoryModels, AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener, AboutAndSettingsAdapterListeners.OnSwitchClickedListener onSwitchClickedListener) {
        this.aboutAndSettingsCategoryModels = aboutAndSettingsCategoryModels;
        this.onItemClickListener = onItemClickListener;
        this.onSwitchClickedListener = onSwitchClickedListener;
    }

    @Override
    public AboutAndSettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_and_settings_recycler_view_item, parent, false);
        return new AboutAndSettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutAndSettingsViewHolder holder, int position) {
        holder.appCompatTextViewTitle.setText(aboutAndSettingsCategoryModels.get(position).getTitle());
        if (onItemClickListener != null && onSwitchClickedListener != null) {
            SettingsAdapter settingsAdapter = new SettingsAdapter(aboutAndSettingsCategoryModels.get(position).getSettingsModels(), onItemClickListener, onSwitchClickedListener);
            holder.recyclerViewSettings.setAdapter(settingsAdapter);
        } else if (onItemClickListener != null) {
            SettingsAdapter settingsAdapter = new SettingsAdapter(aboutAndSettingsCategoryModels.get(position).getSettingsModels(), onItemClickListener);
            holder.recyclerViewSettings.setAdapter(settingsAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return aboutAndSettingsCategoryModels.size();
    }

    class AboutAndSettingsViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewTitle;
        RecyclerView recyclerViewSettings;

        AboutAndSettingsViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            initRecyclerView(itemView);
        }

        private void initView(View itemView) {
            appCompatTextViewTitle = (AppCompatTextView) itemView.findViewById(R.id.about_and_settings_recycler_view_item_settings_title);
        }

        private void initRecyclerView(View itemView) {
            recyclerViewSettings = (RecyclerView) itemView.findViewById(R.id.about_and_settings_recycler_view_item_settings_recycler_view);
            recyclerViewSettings.setNestedScrollingEnabled(false);
            recyclerViewSettings.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerViewSettings.setLayoutManager(linearLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewSettings.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerViewSettings.addItemDecoration(dividerItemDecoration);
        }
    }
}
