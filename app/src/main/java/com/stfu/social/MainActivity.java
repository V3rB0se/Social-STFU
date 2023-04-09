package com.stfu.social;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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

public class MainActivity extends AppCompatActivity {

    private final String WHATSAPP_PACKAGE = "com.whatsapp";
    private final String SNAPCHAT_PACKAGE = "com.snapchat.android";
    private final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private final String REDDIT_PACKAGE = "com.reddit.frontpage";
    private final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    private final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private final String DISCORD_PACKAGE = "com.discord";

    MaterialSwitch
            Instagram,
            Snapchat,
            Whatsapp,
            Reddit,
            Telegram,
            Facebook,
            Discord;
    Button button;

    static {
        // Set settings before the main shell can be created
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(10)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}