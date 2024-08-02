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

import android.app.Activity;
import android.content.IntentSender;

import androidx.annotation.NonNull;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class UpdateChecker {

    public static final int UPDATE_REQUEST_CODE = 123;

    public static void checkForUpdates(@NonNull Activity activity) {
        // Create an instance of the AppUpdateManager
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(activity);
        // Check for updates
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // If an update is available and allowed, start the update flow
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            activity,
                            UPDATE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
