package com.mdtayoburrahman.codetoimage.Activitys;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.mdtayoburrahman.codetoimage.databinding.ActivityBrowserBinding;

public class BrowserActivity extends BaseActivity {
    ActivityBrowserBinding binding;
    private FrameLayout adContainerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        topBar();
        binding.web.loadUrl("file:///android_asset/webassets/" + getIntent().getStringExtra("pageFileName"));
        //load ads data
    }

    private void topBar() {
        MaterialToolbar topAppBar = binding.materialToolbar;
        topAppBar.setTitle(getIntent().getStringExtra("pageTitle"));
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });

    }

    /*****--------------- others ----------------*/

}