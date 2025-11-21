package com.stfu.social;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.DynamicColors;
import com.topjohnwu.superuser.Shell;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SocialAppAdapter.OnSwitchChangeListener {
    // Existing social apps
    private static final String WHATSAPP_PACKAGE = "com.whatsapp";
    private static final String SNAPCHAT_PACKAGE = "com.snapchat.android";
    private static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private static final String REDDIT_PACKAGE = "com.reddit.frontpage";
    private static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private static final String DISCORD_PACKAGE = "com.discord";

    // Additional social apps
    private static final String TWITTER_PACKAGE = "com.twitter.android";
    private static final String TIKTOK_PACKAGE = "com.zhiliaoapp.musically";
    private static final String LINKEDIN_PACKAGE = "com.linkedin.android";
    private static final String MESSENGER_PACKAGE = "com.facebook.orca";
    private static final String THREADS_PACKAGE = "com.instagram.barcelona";
    private static final String PINTEREST_PACKAGE = "com.pinterest";

    private RecyclerView recyclerView;
    private SocialAppAdapter adapter;
    private List<SocialAppAdapter.SocialAppItem> appItems;
    private boolean allAppsDisabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Apply Material You dynamic colors on Android 12+
        DynamicColors.applyToActivityIfAvailable(this);

        // Enable edge-to-edge display using modern WindowCompat API
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main_new);

        MaterialCardView logoCard = findViewById(R.id.logo_card);
        recyclerView = findViewById(R.id.apps_recycler_view);

        if (recyclerView == null) {
            Toast.makeText(this, "Error: RecyclerView not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setupRecyclerView();
        checkAndSetupApps();

        // Setup logo click listener to toggle all apps
        if (logoCard != null) {
            logoCard.setOnClickListener(v -> {
                v.performHapticFeedback(
                    android.view.HapticFeedbackConstants.LONG_PRESS,
                    android.view.HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                );
                toggleAllApps();
            });
        }
    }

    private void setupRecyclerView() {
        appItems = new ArrayList<>();

        // Add all social apps to the list (in alphabetical order for better UX)
        appItems.add(new SocialAppAdapter.SocialAppItem(DISCORD_PACKAGE, getString(R.string.discord), R.drawable.icons8_discord));
        appItems.add(new SocialAppAdapter.SocialAppItem(FACEBOOK_PACKAGE, getString(R.string.facebook), R.drawable.icons8_facebook));
        appItems.add(new SocialAppAdapter.SocialAppItem(INSTAGRAM_PACKAGE, getString(R.string.instagram), R.drawable.icons8_instagram));
        appItems.add(new SocialAppAdapter.SocialAppItem(LINKEDIN_PACKAGE, getString(R.string.linkedin), R.drawable.icons8_linkedin));
        appItems.add(new SocialAppAdapter.SocialAppItem(MESSENGER_PACKAGE, getString(R.string.messenger), R.drawable.icons8_messenger));
        appItems.add(new SocialAppAdapter.SocialAppItem(PINTEREST_PACKAGE, getString(R.string.pinterest), R.drawable.icons8_pinterest));
        appItems.add(new SocialAppAdapter.SocialAppItem(REDDIT_PACKAGE, getString(R.string.reddit), R.drawable.icons8_reddit));
        appItems.add(new SocialAppAdapter.SocialAppItem(SNAPCHAT_PACKAGE, getString(R.string.snapchat), R.drawable.icons8_snapchat));
        appItems.add(new SocialAppAdapter.SocialAppItem(TELEGRAM_PACKAGE, getString(R.string.telegram), R.drawable.icons8_telegram_app));
        appItems.add(new SocialAppAdapter.SocialAppItem(THREADS_PACKAGE, getString(R.string.threads), R.drawable.icons8_threads));
        appItems.add(new SocialAppAdapter.SocialAppItem(TIKTOK_PACKAGE, getString(R.string.tiktok), R.drawable.icons8_tiktok));
        appItems.add(new SocialAppAdapter.SocialAppItem(TWITTER_PACKAGE, getString(R.string.twitter), R.drawable.icons8_twitter));
        appItems.add(new SocialAppAdapter.SocialAppItem(WHATSAPP_PACKAGE, getString(R.string.whatsapp), R.drawable.icons8_whatsapp));

        adapter = new SocialAppAdapter(appItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void checkAndSetupApps() {
        PackageManager pm = getPackageManager();

        for (SocialAppAdapter.SocialAppItem item : appItems) {
            item.isInstalled = isAppInstalled(pm, item.packageName);
            if (item.isInstalled) {
                item.isDisabled = checkStatus(item.packageName);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private boolean isAppInstalled(PackageManager pm, String packageName) {
        if (packageName == null || pm == null) {
            return false;
        }
        try {
            pm.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean checkStatus(String packageName) {
        if (packageName == null) {
            return false;
        }
        try {
            Shell.Result result = Shell.cmd("pm list packages -d").exec();
            if (result == null || result.getOut() == null) {
                return false;
            }
            String output = TextUtils.join("\n", result.getOut());
            return output != null && output.contains(packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onSwitchChanged(SocialAppAdapter.SocialAppItem item, boolean isChecked) {
        if (item == null || item.packageName == null) {
            return;
        }

        String command = isChecked ?
            "pm disable-user --user 0 " + item.packageName :
            "pm enable " + item.packageName;

        try {
            Shell.Result result = Shell.cmd(command).exec();
            if (result != null && result.isSuccess()) {
                item.isDisabled = isChecked;
                Toast.makeText(this,
                    item.appName + " " + (isChecked ? getString(R.string.disabled) : getString(R.string.enabled)),
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                    "Failed to " + (isChecked ? "disable" : "enable") + " " + item.appName,
                    Toast.LENGTH_SHORT).show();
                // Revert the switch state
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,
                "Error: " + e.getMessage(),
                Toast.LENGTH_SHORT).show();
            // Revert the switch state
            adapter.notifyDataSetChanged();
        }
    }

    private void toggleAllApps() {
        // Toggle the state
        allAppsDisabled = !allAppsDisabled;

        String command = allAppsDisabled ? "pm disable-user --user 0 " : "pm enable ";
        int successCount = 0;
        int totalInstalled = 0;

        // Toggle all installed apps
        for (SocialAppAdapter.SocialAppItem item : appItems) {
            if (item.isInstalled) {
                totalInstalled++;
                try {
                    Shell.Result result = Shell.cmd(command + item.packageName).exec();
                    if (result.isSuccess()) {
                        item.isDisabled = allAppsDisabled;
                        successCount++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Update the UI
        adapter.notifyDataSetChanged();

        // Show feedback
        String message;
        if (successCount == totalInstalled) {
            message = allAppsDisabled ?
                "All social apps disabled (" + successCount + ")" :
                "All social apps enabled (" + successCount + ")";
        } else {
            message = allAppsDisabled ?
                "Disabled " + successCount + "/" + totalInstalled + " apps" :
                "Enabled " + successCount + "/" + totalInstalled + " apps";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

