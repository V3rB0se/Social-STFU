package com.stfu.social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.topjohnwu.superuser.Shell;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String WHATSAPP_PACKAGE = "com.whatsapp";
    private static final String SNAPCHAT_PACKAGE = "com.snapchat.android";
    private static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private static final String REDDIT_PACKAGE = "com.reddit.frontpage";
    private static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private static final String DISCORD_PACKAGE = "com.discord";

    MaterialSwitch Instagram, Snapchat, Whatsapp, Reddit, Telegram, Facebook, Discord;
    Button button;

    static {
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(10)
        );
    }

    private final List<AppInfo> appInfoList = Arrays.asList(
            new AppInfo(WHATSAPP_PACKAGE, R.id.whatsapp_switch, R.id.whatsapp_desc),
            new AppInfo(SNAPCHAT_PACKAGE, R.id.snapchat_switch, R.id.snapchat_desc),
            new AppInfo(INSTAGRAM_PACKAGE, R.id.instagram_switch, R.id.instagram_desc),
            new AppInfo(REDDIT_PACKAGE, R.id.reddit_switch, R.id.reddit_desc),
            new AppInfo(TELEGRAM_PACKAGE, R.id.telegram_switch, R.id.telegram_desc),
            new AppInfo(FACEBOOK_PACKAGE, R.id.facebook_switch, R.id.facebook_desc),
            new AppInfo(DISCORD_PACKAGE, R.id.discord_switch, R.id.discord_desc)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.Exit);

        checkAndSetupApps(this);
        setupSwitchListeners();
        button.setOnClickListener(view -> finish());
    }


    @SuppressLint("SetTextI18n")
    private void checkAndSetupApps(Context context) {
        PackageManager pm = context.getPackageManager();

        for (AppInfo appInfo : appInfoList) {
            boolean isAppInstalled = isAppInstalled(pm, appInfo.packageName);
            MaterialSwitch appSwitch = findViewById(appInfo.switchId);
            appSwitch.setEnabled(isAppInstalled);
            checkStatus(appInfo.packageName, appSwitch);
            ChangeStateMessage(appInfo.descId, isAppInstalled, appSwitch);
        }
    }


    private boolean isAppInstalled(PackageManager pm, String packageName) {
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private void setupSwitchListeners() {
        for (AppInfo appInfo : appInfoList) {
            MaterialSwitch appSwitch = findViewById(appInfo.switchId);
            appSwitch.setOnCheckedChangeListener((switchView, isChecked) -> toggleApp(appInfo.packageName, appInfo.descId, isChecked, switchView));
        }
    }


    @SuppressLint("SetTextI18n")
    public void toggleApp(String packageName, int id, boolean isChecked, View view) {
        TextView tv = findViewById(id);
        String action = isChecked ? "disable" : "enable";

        Shell.cmd("pm " + action + " " + packageName).exec();
        view.performHapticFeedback(HapticFeedbackConstants.REJECT);

        tv.setText("Application is " + (isChecked ? "Disabled" : "Enabled"));
        Toast.makeText(this, packageName + " " + (isChecked ? "Disabled" : "Enabled"), Toast.LENGTH_LONG).show();
    }


    public void checkStatus(String packageName, MaterialSwitch switcher) {
        Shell.Result result = Shell.cmd("pm list packages -d").exec();
        String output = TextUtils.join("\n", result.getOut());
        boolean isDisabled = output.contains(packageName);
        switcher.setChecked(isDisabled);
    }


    @SuppressLint("SetTextI18n")
    public void ChangeStateMessage(int tid, Boolean isAppInstalled, MaterialSwitch switcher) {
        TextView tv = findViewById(tid);

        if (!isAppInstalled) {
            tv.setText("Application Not Found");
        } else {
            tv.setText(switcher.isChecked() ? "Application is Disabled" : "Application is Enabled");
        }
    }


    public static class AppInfo {
        String packageName;
        int switchId;
        int descId;

        public AppInfo(String packageName, int switchId, int descId) {
            this.packageName = packageName;
            this.switchId = switchId;
            this.descId = descId;
        }
    }
}
