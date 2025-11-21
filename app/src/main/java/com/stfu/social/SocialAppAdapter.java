package com.stfu.social;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.List;

public class SocialAppAdapter extends RecyclerView.Adapter<SocialAppAdapter.ViewHolder> {

    public static class SocialAppItem {
        public String packageName;
        public String appName;
        public int iconResId;
        public boolean isInstalled;
        public boolean isDisabled;

        public SocialAppItem(String packageName, String appName, int iconResId) {
            this.packageName = packageName;
            this.appName = appName;
            this.iconResId = iconResId;
            this.isInstalled = false;
            this.isDisabled = false;
        }
    }

    public interface OnSwitchChangeListener {
        void onSwitchChanged(SocialAppItem item, boolean isChecked);
    }

    private final List<SocialAppItem> items;
    private final OnSwitchChangeListener listener;

    public SocialAppAdapter(List<SocialAppItem> items, OnSwitchChangeListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_social_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocialAppItem item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView card;
        private final ImageView icon;
        private final TextView appName;
        private final TextView statusText;
        private final MaterialSwitch appSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.app_card);
            icon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            statusText = itemView.findViewById(R.id.app_status);
            appSwitch = itemView.findViewById(R.id.app_switch);
        }

        public void bind(SocialAppItem item, OnSwitchChangeListener listener) {
            if (item == null) {
                return;
            }

            if (item.appName != null) {
                appName.setText(item.appName);
            }
            icon.setImageResource(item.iconResId);

            appSwitch.setEnabled(item.isInstalled);
            appSwitch.setChecked(item.isDisabled);

            if (!item.isInstalled) {
                statusText.setText(R.string.not_installed);
                card.setAlpha(0.6f);
            } else {
                statusText.setText(item.isDisabled ? R.string.disabled : R.string.enabled);
                card.setAlpha(1.0f);
            }

            // Remove previous listener to avoid multiple triggers
            appSwitch.setOnCheckedChangeListener(null);
            appSwitch.setChecked(item.isDisabled);

            appSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null && item.isInstalled) {
                    // Perform haptic feedback
                    buttonView.performHapticFeedback(
                        HapticFeedbackConstants.CONTEXT_CLICK,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                    );

                    listener.onSwitchChanged(item, isChecked);
                    statusText.setText(isChecked ? R.string.disabled : R.string.enabled);
                    item.isDisabled = isChecked;
                }
            });
        }
    }
}

