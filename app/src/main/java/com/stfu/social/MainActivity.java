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
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(10)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Instagram = findViewById(R.id.instagram_switch);
        Snapchat = findViewById(R.id.snapchat_switch);
        Whatsapp = findViewById(R.id.whatsapp_switch);
        Reddit = findViewById(R.id.reddit_switch);
        Telegram = findViewById(R.id.telegram_switch);
        Discord = findViewById(R.id.discord_switch);
        Facebook = findViewById(R.id.facebook_switch);

        button = findViewById(R.id.Exit);
        checkApps(this,  Instagram,
                Snapchat,
                Reddit,
                Telegram,
                Whatsapp,
                Discord,
                Facebook);


        button.setOnClickListener((view -> finish())
        );

        Reddit.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(REDDIT_PACKAGE, R.id.reddit_desc, isChecked, Reddit));

        Telegram.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(TELEGRAM_PACKAGE, R.id.telegram_desc, isChecked, Telegram));

        Instagram.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(INSTAGRAM_PACKAGE, R.id.instagram_desc, isChecked, Instagram));

        Snapchat.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(SNAPCHAT_PACKAGE, R.id.snapchat_desc, isChecked, Snapchat));

        Whatsapp.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(WHATSAPP_PACKAGE, R.id.whatsapp_desc, isChecked, Whatsapp));

        Facebook.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(FACEBOOK_PACKAGE, R.id.facebook_desc, isChecked, Facebook));

        Discord.setOnCheckedChangeListener((materialSwitch, isChecked) -> toggleApp(DISCORD_PACKAGE, R.id.discord_desc, isChecked, Discord));

    }


    @SuppressLint("SetTextI18n")
    public void toggleApp(String packageName, int id, boolean isChecked, View view) {
        TextView tv = findViewById(id);

        if (isChecked) {
            Shell.cmd("pm disable " + packageName).exec();
            view.performHapticFeedback(HapticFeedbackConstants.REJECT);
            tv.setText("Application is Disabled");
            Toast.makeText(this, packageName + " Disabled", Toast.LENGTH_LONG).show();
        } else {
            Shell.cmd("pm enable " + packageName).exec();
            view.performHapticFeedback(HapticFeedbackConstants.REJECT);
            Toast.makeText(this, packageName + " Enabled", Toast.LENGTH_LONG).show();
            tv.setText("Application is Enabled");
        }
    }

    public void checkStatus(String appName, MaterialSwitch switcher){
        Shell.Result result = Shell.cmd("pm list packages -d").exec();
        String output = TextUtils.join("\n", result.getOut());
        boolean isDisabled = output.contains(appName);
        switcher.setChecked(isDisabled);

    }
    @SuppressLint("SetTextI18n")
    public void checkApps(Context context, MaterialSwitch Instagram, MaterialSwitch Snapchat, MaterialSwitch Reddit, MaterialSwitch Telegram, MaterialSwitch Whatsapp, MaterialSwitch Discord, MaterialSwitch Facebook){

        String[] appPackageNames = {INSTAGRAM_PACKAGE, WHATSAPP_PACKAGE, DISCORD_PACKAGE, REDDIT_PACKAGE, TELEGRAM_PACKAGE, SNAPCHAT_PACKAGE, FACEBOOK_PACKAGE};

        for (String packageName : appPackageNames) {
            PackageManager pm = context.getPackageManager();
            boolean isAppInstalled;

            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                isAppInstalled = true;
            } catch (PackageManager.NameNotFoundException e) {
                isAppInstalled = false;
            }

            switch (packageName) {
                case INSTAGRAM_PACKAGE:
                    checkStatus(INSTAGRAM_PACKAGE, Instagram);
                    Instagram.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.instagram_desc, isAppInstalled, Instagram);

                    break;
                case TELEGRAM_PACKAGE:
                    checkStatus(TELEGRAM_PACKAGE, Telegram);
                    Telegram.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.telegram_desc, isAppInstalled, Telegram);

                    break;
                case REDDIT_PACKAGE:
                    checkStatus(REDDIT_PACKAGE, Reddit);
                    Reddit.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.reddit_desc, isAppInstalled, Reddit);


                    break;
                case WHATSAPP_PACKAGE:
                    checkStatus(WHATSAPP_PACKAGE, Whatsapp);
                    Whatsapp.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.whatsapp_desc, isAppInstalled, Whatsapp);

                    break;
                case SNAPCHAT_PACKAGE:
                    checkStatus(SNAPCHAT_PACKAGE, Snapchat);
                    Snapchat.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.snapchat_desc, isAppInstalled, Snapchat);

                    break;
                case DISCORD_PACKAGE:
                    checkStatus(DISCORD_PACKAGE, Discord);
                    Discord.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.discord_desc, isAppInstalled, Discord);

                    break;
                case FACEBOOK_PACKAGE:
                    checkStatus(FACEBOOK_PACKAGE, Facebook);
                    Facebook.setEnabled(isAppInstalled);
                    ChangeStateMessage(R.id.facebook_desc, isAppInstalled, Facebook);
                    break;
            }
        }

    }


    @SuppressLint("SetTextI18n")
    public void ChangeStateMessage(int tid, Boolean isAppInstalled, MaterialSwitch switcher){

        TextView tv = findViewById(tid);

        if(!isAppInstalled){
            tv.setText("Application Not Found");
        }
        if(switcher.isChecked()){
            tv.setText("Application is Disabled");
        }
        if(!switcher.isChecked() && isAppInstalled){
            tv.setText("Application is Enabled");
        }
    }

}