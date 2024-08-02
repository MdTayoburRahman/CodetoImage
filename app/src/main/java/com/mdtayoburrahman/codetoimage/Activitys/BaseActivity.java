package com.mdtayoburrahman.codetoimage.Activitys;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Force the use of a non-dark mode theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        if (newBase != null) {
            Configuration override = new Configuration(newBase.getResources().getConfiguration());
            override.fontScale = 1.0f; // Set font scale to 1.0 (no scaling)
            Context context = newBase.createConfigurationContext(override);
            super.attachBaseContext(context);
        } else {
            super.attachBaseContext(newBase);
        }
    }
}

