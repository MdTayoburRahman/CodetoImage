/*
 * Copyright (c) 2023. হিসেবী - দৈনিক আয় ব্যয় হিসাব ("Hishebi") is a powerful and intuitive mobile application designed to help you keep track of your daily expenses and income. With its user-friendly interface and comprehensive features, Hishebi makes it easy for you to manage your finances, save money, and achieve your financial goals.
 *
 *                                                                                                  This app is protected by copyright laws and international treaties. Unauthorized reproduction or distribution of this app, or any portion of it, may result in severe civil and criminal penalties, and will be prosecuted to the maximum extent possible under the law.
 *
 *                                                                                                  By downloading, installing, or using Hishebi, you agree to be bound by the terms and conditions of this copyright notice, as well as the app's End User License Agreement (EULA) and Privacy Policy. If you do not agree to these terms, you may not use Hishebi.
 *
 *                                                                                                  Thank you for choosing Hishebi. We hope that it will help you take control of your finances and improve your financial well-being.
 */

package com.mdtayoburrahman.codetoimage.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class UpdateChecker {

    public static final String TAG = "UpdateChecker";
    public static final int UPDATE_REQUEST_CODE = 123;

    public static void checkForUpdates(@NonNull Activity activity) {
        // Create an instance of the AppUpdateManager
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(activity);

        // Check for updates
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            try {
                // Get the current app version code
                PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                int currentVersionCode = packageInfo.versionCode;
                Log.d(TAG, "checkForUpdates: currentVersionCode =  "+currentVersionCode);
                Log.d(TAG, "checkForUpdates: remoteVersionCode =  "+appUpdateInfo.availableVersionCode());

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                        && appUpdateInfo.availableVersionCode() > currentVersionCode) {
                    // If an update is available, and the available version code is greater than the current version code, show the update dialog
                    showUpdateDialog(appUpdateManager, appUpdateInfo, activity);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(TAG, "checkForUpdates: "+e.getMessage());
                e.printStackTrace();
            }
        });
    }


    private static void showUpdateDialog(AppUpdateManager appUpdateManager, AppUpdateInfo appUpdateInfo,
                                         Activity activity) {
        new MaterialAlertDialogBuilder(activity)
                .setTitle("Update Available")
                .setMessage("A new version of the app is available. Would you like to update now?")
                .setPositiveButton("Update Now", (dialog, which) -> {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                activity,
                                UPDATE_REQUEST_CODE);
                    } catch (Exception e) {
                        Log.d(TAG, "showUpdateDialog: "+e.getMessage());
                        e.printStackTrace();

                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    public static void checkForReview(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CountPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int openCount = sharedPreferences.getInt("openCount", 0);
        openCount++;
        editor.putInt("openCount", openCount);
        editor.apply();
        if (openCount == 4) {
            // Call your method here
            // For example: triggerYourMethod();

            ReviewManager manager = ReviewManagerFactory.create(context);
            Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // We can get the ReviewInfo object
                    ReviewInfo reviewInfo = task.getResult();
                    Task<Void> flow = manager.launchReviewFlow((Activity) context, reviewInfo);
                    flow.addOnCompleteListener(task1 -> {
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                    });
                } else {
                    // There was some problem, continue regardless of the result.
                    // you can show your own rate dialog alert and redirect user to your app page
                    // on play store.
                }

            });
        }
        if (openCount > 5) {
            openCount = 5;
            editor.putInt("openCount", openCount);
            editor.apply();
        }

    }
}