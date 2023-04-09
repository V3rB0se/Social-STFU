package com.stfu.social;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.topjohnwu.superuser.Shell;

public class MainActivity extends AppCompatActivity {


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