package com.mdtayoburrahman.codetoimage.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;

import com.amrdeveloper.codeview.CodeView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mdtayoburrahman.codetoimage.R;
import com.mdtayoburrahman.codetoimage.Utils.AppUtils;
import com.mdtayoburrahman.codetoimage.Utils.CodeViewSetupUtils;
import com.mdtayoburrahman.codetoimage.Utils.ImageSaveUtils;
import com.mdtayoburrahman.codetoimage.Utils.ImageShareUtils;
import com.mdtayoburrahman.codetoimage.Utils.UpdateChecker;
import com.mdtayoburrahman.codetoimage.databinding.ActivityMainBinding;
import com.mdtayoburrahman.codetoimage.databinding.ExitAlertBinding;

import java.util.Calendar;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Animation myAnim;
    private  CodeView codeViews;
    private CodeViewSetupUtils codeViewSetupUtils;
    private AlertDialog alertDialog;
    private AlertDialog exitDialog;
    private AutoCompleteTextView dropdownMenu;
    private String selectedItem ="Java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myAnim = AnimationUtils.loadAnimation(this, R.anim.click_anim);
        codeViewSetupUtils = new CodeViewSetupUtils(MainActivity.this);
        dropdownMenu = findViewById(R.id.dropdown_menu);
        codeViews = binding.codeView;
        codeViews.setMovementMethod(new ScrollingMovementMethod());
        codeViewSetupUtils.codeViewSetupForJava(codeViews); // initialize data

        topAppBar();
        ButtonTask();
        setupLanguageList();
        setupTextSize();
        backPressed();
        UpdateChecker.checkForUpdates(MainActivity.this);
        UpdateChecker.checkForReview(MainActivity.this);
    }

    private void topAppBar() {

        binding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.saved_item){
                animateMenuItemAndIntent(item);
                return true;
            }
            if (item.getItemId() == R.id.about) {
                showAboutAlert(MainActivity.this);
                return true;
            }
            if (item.getItemId() == R.id.privacy) {
                startActivity(new Intent(MainActivity.this, BrowserActivity.class)
                        .putExtra("pageFileName", "privacy.html")
                        .putExtra("pageTitle", "Privacy Policy")
                );
            }
            if (item.getItemId() == R.id.terms_condition) {
                startActivity(new Intent(MainActivity.this, BrowserActivity.class)
                        .putExtra("pageFileName", "terms.html")
                        .putExtra("pageTitle", "Terms & Conditions"));

            }
            if (item.getItemId() == R.id.faq) {
                startActivity(new Intent(MainActivity.this, BrowserActivity.class)
                        .putExtra("pageFileName", "faq.html")
                        .putExtra("pageTitle", "FAQ"));
            }
            return false;
        });
    }

    private void setupLanguageList(){
        List<String> languages = List.of("Java", "Python", "JavaScript", "C#", "HTML", "Ruby", "Swift", "PHP", "SQL", "Kotlin", "Go", "Rust", "CSS","JSON","XML","YAML");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, languages);
        dropdownMenu.setAdapter(adapter);
        dropdownMenu.setOnItemClickListener((parent, view, position, id) -> {
            selectedItem = adapter.getItem(position);
           switch (selectedItem){
               case "Java":{
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForJava(codeViews);
                   break;
               }
               case "Python":{
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForPython(codeViews);
                   break;
               }
               case "JavaScript":{
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForJavaScript(codeViews);
                   break;
               }
               case "C#":{
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupCSharp(codeViews);
                   break;
               }
               case "HTML": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForHtml(codeViews);
                   break;
               }
               case "Ruby":{
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForRuby(codeViews);
                   break;
               }
               case "Swift": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupSwift(codeViews);
                   break;
               }
               case "PHP": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupPHP(codeViews);
                   break;
               }
               case "SQL": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupSQL(codeViews);
                   break;
               }
               case "Kotlin": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupKotlin(codeViews);
                   break;
               }
               case "Go": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupGo(codeViews);
                   break;
               }
               case "Rust": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupRust(codeViews);
                   break;
               }
               case "CSS": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupCSS(codeViews);
                   break;
               }case "JSON": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupJSON(codeViews);
                   break;
               }
               case "XML": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupXML(codeViews);
                   break;
               }
               case "YAML": {
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupYAML(codeViews);
                   break;
               }
               default:
                   codeViews.resetHighlighter();
                   codeViews.resetSyntaxPatternList();
                   codeViewSetupUtils.codeViewSetupForJava(codeViews);
                   break;

           }
        });



    }

    private void setupTextSize(){
        binding.textSizeSlider.addOnChangeListener((slider, value, fromUser) -> {
            // Do something with the slider value
            Log.d("Slider", "Value: " + value);
            codeViews.setTextSize(value);
        });

    }

    private void ButtonTask() {
        binding.changeBgColorButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            changeBgColor();
        });

        binding.changeEditorBgColorButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            changeEditorBgColor();
        });

        binding.pastTextButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            pasteFromClipboard(codeViews);
        });

        binding.saveImageButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            ImageSaveUtils saveUtils = new ImageSaveUtils(MainActivity.this);
            saveUtils.requestWritePermission(MainActivity.this, new ImageSaveUtils.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    // Permission granted, proceed with saving the image
                    ImageShareUtils imageShareUtils = new ImageShareUtils(MainActivity.this);
                    Bitmap bitmap = imageShareUtils.createBitmapFromLayout(binding.ImageOutputLayout);
                    saveUtils.saveImageForAllAndroid(bitmap, "CodeToImage", binding.ImageOutputLayout.getHeight(), binding.ImageOutputLayout.getWidth(), b -> {
                        if (b){
                            AppUtils.showPositiveSnackBar(MainActivity.this,"Image Saved Successfully");
                        }else {
                            AppUtils.showNegativeSnackBar(MainActivity.this,"Unable Save image");
                        }
                    });

                }

                @Override
                public void onPermissionDenied() {
                    // Permission denied, handle accordingly
                    AppUtils.showNegativeSnackBar(MainActivity.this,"Unable Save image.Permission Denied");
                }
            });

        });

        binding.restButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            codeViews.setTextSize(10);
            binding.textSizeSlider.setValue(0);
            codeViews.resetHighlighter();
            codeViews.resetSyntaxPatternList();
            codeViewSetupUtils.codeViewSetupForJava(codeViews);
            setupLanguageList();
            binding.editorLayer.setBackgroundColor(getColor(R.color.orange));
        });

        binding.resetPattern.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            codeViews.resetHighlighter();
            codeViews.resetSyntaxPatternList();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                codeViews.resetPivot();
            }
            codeViews.clearMatches();
            switch (selectedItem){
                case "Java":{
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForJava(codeViews);
                    break;
                }
                case "Python":{
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForPython(codeViews);
                    break;
                }
                case "JavaScript":{
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForJavaScript(codeViews);
                    break;
                }
                case "C#":{
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupCSharp(codeViews);
                    break;
                }
                case "HTML": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForHtml(codeViews);
                    break;
                }
                case "Ruby":{
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForRuby(codeViews);
                    break;
                }
                case "Swift": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupSwift(codeViews);
                    break;
                }
                case "PHP": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupPHP(codeViews);
                    break;
                }
                case "SQL": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupSQL(codeViews);
                    break;
                }
                case "Kotlin": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupKotlin(codeViews);
                    break;
                }
                case "Go": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupGo(codeViews);
                    break;
                }
                case "Rust": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupRust(codeViews);
                    break;
                }
                case "CSS": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupCSS(codeViews);
                    break;
                }case "JSON": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupJSON(codeViews);
                    break;
                }
                case "XML": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupXML(codeViews);
                    break;
                }
                case "YAML": {
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupYAML(codeViews);
                    break;
                }
                default:
                    codeViews.resetHighlighter();
                    codeViews.resetSyntaxPatternList();
                    codeViewSetupUtils.codeViewSetupForJava(codeViews);
                    break;

            }


        });

        binding.clearText.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            codeViews.setText("");
            codeViews.setHint("Enter your Code Here");
        });

        binding.shareButton.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            ImageShareUtils imageShareUtils = new ImageShareUtils(MainActivity.this);
            Bitmap bitmap = imageShareUtils.createBitmapFromLayout(binding.ImageOutputLayout);
            if (bitmap!=null){
                imageShareUtils.shareImageFromBitmap(bitmap);
            }
        });
    }

    private void changeBgColor(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, getColor(R.color.orange), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Log.d(TAG, "onCancel: no color piked");

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                binding.ImageOutputLayout.setBackgroundColor(color);
            }
        });
        ambilWarnaDialog.show();
    }

    private void changeEditorBgColor(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, getColor(R.color.orange), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Log.d(TAG, "onCancel: no color piked");

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                binding.editorLayer.setBackgroundColor(color);
            }
        });
        ambilWarnaDialog.show();
    }

    private void pasteFromClipboard(CodeView codeView){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip())
        {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);
            String pastedText = item.getText().toString();
            codeView.setText(pastedText);
        } else {
            Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("SetTextI18n")
    public void showAboutAlert(Context context) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.about_app_layout, null);
        TextView appVersionText = view.findViewById(R.id.app_version);

        Button button = view.findViewById(R.id.closeBTN);
        Button emailButton = view.findViewById(R.id.sendEmailBTN);


        try {
            PackageInfo pInfo = null;
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String copyright = year + " ©Md Tayobur Rahman - All rights reserved";
            String website = "\n http://mdtayoburrahman.com \n tayoburrahman119@gmail.com";
            appVersionText.setText(copyright + website + "\n\n App Version - " + version);

        } catch (Exception e) {
            e.printStackTrace();
        }
        emailButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "tayoburrahman119@gmail.com", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding Code To Image App -");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        });


        button.setOnClickListener(view1 -> {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        });


        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();

    }

    private void backPressed() {
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                try {
                    showExitAlert();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void showExitAlert() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        ExitAlertBinding alertBinding = ExitAlertBinding.inflate(inflater);


        alertBinding.quitApp.setOnClickListener(view -> {
            if (exitDialog != null) {
                exitDialog.dismiss();
                finishAffinity();
            }

        });
        alertBinding.close.setOnClickListener(view -> {
            if (exitDialog != null) {
                exitDialog.dismiss();
            }
        });

        builder.setView(alertBinding.getRoot());
        builder.setCancelable(true);
        exitDialog = builder.create();
        exitDialog.show();
    }

    private void animateMenuItemAndIntent(MenuItem item) {
        // Find the View of the MenuItem
        View menuItemView = findViewById(item.getItemId());

        if (menuItemView != null) {
            // Animate the menu item
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(menuItemView, "scaleX", 1.0f, 1.5f);
            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(menuItemView, "scaleY", 1.0f, 1.5f);
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(menuItemView, "scaleX", 1.5f, 1.0f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(menuItemView, "scaleY", 1.5f, 1.0f);

            scaleUpX.setDuration(150);
            scaleUpY.setDuration(150);
            scaleDownX.setDuration(150);
            scaleDownY.setDuration(150);

            scaleUpX.start();
            scaleUpY.start();
            scaleUpX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scaleDownX.start();
                    scaleDownY.start();
                }
            });

            // Navigate to the next activity after the animation
            scaleDownY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(new Intent(MainActivity.this, SavedImageActivity.class));
                }
            });
        }
    }
}