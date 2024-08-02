package com.mdtayoburrahman.codetoimage.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mdtayoburrahman.codetoimage.R;
import com.mdtayoburrahman.codetoimage.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends BaseActivity {

    ActivitySplashScreenBinding binding;
    Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        binding.frontIcon.startAnimation(myAnim);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startMainActivity();
            Log.d("Handler", "Running Handler");
        }, 2000);
    }

    private void startMainActivity() {
        Intent SplashIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(SplashIntent);
        finish();
    }
}