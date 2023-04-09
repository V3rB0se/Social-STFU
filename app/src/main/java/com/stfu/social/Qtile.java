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
        final String WHATSAPP_PACKAGE = "com.whatsapp";
        final String SNAPCHAT_PACKAGE = "com.snapchat.android";
        final String INSTAGRAM_PACKAGE = "com.instagram.android";
        final String REDDIT_PACKAGE = "com.reddit.frontpage";
        final String TELEGRAM_PACKAGE = "org.telegram.messenger";
        final String FACEBOOK_PACKAGE = "com.facebook.katana";
        final String DISCORD_PACKAGE = "com.discord";

        List<String> appList = Arrays.asList(WHATSAPP_PACKAGE, SNAPCHAT_PACKAGE, INSTAGRAM_PACKAGE, REDDIT_PACKAGE, TELEGRAM_PACKAGE, DISCORD_PACKAGE, FACEBOOK_PACKAGE);

        Tile tile = getQsTile();


        if (Invoke) {
            for (String packageName : appList) {
                Shell.cmd("pm disable " + packageName).exec();
            }
            Toast.makeText(getApplicationContext(), "Apps disabled", Toast.LENGTH_SHORT).show();
        } else {
            for (String packageName : appList) {
                Shell.cmd("pm enable " + packageName).exec();
            }
            Toast.makeText(getApplicationContext(), "Apps enabled", Toast.LENGTH_SHORT).show();
        }


    }




}