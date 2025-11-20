package com.stfu.social;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;
import com.topjohnwu.superuser.Shell;
import java.util.Arrays;
import java.util.List;

public class Qtile extends TileService {

    private boolean Invoke = false;

    @Override
    public void onClick() {
        Invoke = !Invoke;
        AppsState();
        Tile tile = getQsTile();
        tile.setState(Invoke ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();

    }
    private void AppsState(){
        // Existing social apps
        final String WHATSAPP_PACKAGE = "com.whatsapp";
        final String SNAPCHAT_PACKAGE = "com.snapchat.android";
        final String INSTAGRAM_PACKAGE = "com.instagram.android";
        final String REDDIT_PACKAGE = "com.reddit.frontpage";
        final String TELEGRAM_PACKAGE = "org.telegram.messenger";
        final String FACEBOOK_PACKAGE = "com.facebook.katana";
        final String DISCORD_PACKAGE = "com.discord";

        // Additional social apps
        final String TWITTER_PACKAGE = "com.twitter.android";
        final String TIKTOK_PACKAGE = "com.zhiliaoapp.musically";
        final String MESSENGER_PACKAGE = "com.facebook.orca";
        final String THREADS_PACKAGE = "com.instagram.barcelona";
        final String PINTEREST_PACKAGE = "com.pinterest";
        final String TUMBLR_PACKAGE = "com.tumblr";

        List<String> appList = Arrays.asList(
                WHATSAPP_PACKAGE, SNAPCHAT_PACKAGE, INSTAGRAM_PACKAGE,
                REDDIT_PACKAGE, TELEGRAM_PACKAGE, DISCORD_PACKAGE, FACEBOOK_PACKAGE,
                TWITTER_PACKAGE, TIKTOK_PACKAGE, MESSENGER_PACKAGE, THREADS_PACKAGE,
                PINTEREST_PACKAGE, TUMBLR_PACKAGE
        );

        String command = Invoke ? "pm disable-user --user 0 " : "pm enable ";

        for (String packageName : appList) {
            try {
                Shell.cmd(command + packageName).exec();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Show toast notification
        String message = Invoke ? "Social apps disabled" : "Social apps enabled";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}