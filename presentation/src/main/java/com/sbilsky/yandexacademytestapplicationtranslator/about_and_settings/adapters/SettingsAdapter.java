package com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.sbilsky.yandexacademytestapplicationtranslator.R;
import com.sbilsky.yandexacademytestapplicationtranslator.about_and_settings.models.SettingsModel;

import java.util.List;

/**
 * @author Cвятослав Бильский s.bislky
 */

class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {
    private List<SettingsModel> settingsModels;
    private AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener;
    private AboutAndSettingsAdapterListeners.OnSwitchClickedListener onSwitchClickedListener;

    SettingsAdapter(List<SettingsModel> settingsModels, AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener) {
        this.settingsModels = settingsModels;
        this.onItemClickListener = onItemClickListener;
    }

    SettingsAdapter(List<SettingsModel> settingsModels, AboutAndSettingsAdapterListeners.OnItemClickListener onItemClickListener, AboutAndSettingsAdapterListeners.OnSwitchClickedListener onSwitchClickedListener) {
        this.settingsModels = settingsModels;
        this.onItemClickListener = onItemClickListener;
        this.onSwitchClickedListener = onSwitchClickedListener;
    }

    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_and_settings_recycler_view_item_recycler_view_item, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsViewHolder holder, int position) {
        if (settingsModels.get(position).getSwitchModel() != null) {
            holder.switchCompat.setVisibility(View.VISIBLE);
            holder.switchCompat.setChecked(settingsModels.get(position).getSwitchModel().isStatus());
        } else {
            holder.switchCompat.setVisibility(View.GONE);
        }
        holder.appCompatTextViewSettingsName.setText(settingsModels.get(position).getSettingsName());
    }


    @Override
    public int getItemCount() {
        return settingsModels.size();
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView appCompatTextViewSettingsName;
        SwitchCompat switchCompat;

        SettingsViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(settingsModels.get(getAdapterPosition()).getSettingsName());
                if (settingsModels.get(getAdapterPosition()).getSwitchModel() != null && onSwitchClickedListener != null) {
                    switchCompat.setChecked(!switchCompat.isChecked());
                }
            });

        }

        private void initViews(View itemView) {
            appCompatTextViewSettingsName = (AppCompatTextView) itemView.findViewById(R.id.about_and_settings_recycler_view_item_recycler_view_item_settings_name);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.about_and_settings_recycler_view_item_recycler_view_item_switch);
            switchCompat.setOnClickListener(view ->
            {
                if (onSwitchClickedListener != null) {
                    onSwitchClickedListener.onSwitchClick(settingsModels.get(getAdapterPosition()).getSettingsName());
                }
            });
        }
    }
}
