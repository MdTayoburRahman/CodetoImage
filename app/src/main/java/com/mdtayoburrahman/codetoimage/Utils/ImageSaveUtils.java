package com.mdtayoburrahman.codetoimage.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.mdtayoburrahman.codetoimage.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.permissionx.guolindev.PermissionX;

public class ImageSaveUtils {
    public static final String TAG = "ImageSaveUtils";

    private Context context;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ImageSaveUtils(Context context) {
        this.context = context;
    }

    /**
     * this method is responsible for
     * save image to memory for all kinds of android version app
     * ============================================
     *
     *
     * @param bitmap      is the bitmap Image which you want to save
     * @param imageHeight the height of the image
     * @param imageWidth  the width of the image
     * @param imageName   the name of the image
     * @param callback    this callback will return a boolean value
     *                    =============================================
     */
    public void saveImageForAllAndroid(Bitmap bitmap, @NonNull String imageName, int imageHeight, int imageWidth, ResultCallback callback) {
        executor.submit(() -> {
            OutputStream fos;
            String imagesDir = null;
            String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android 10 and above
                    ContentResolver resolver = context.getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName + timeStamp);
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + context.getResources().getString(R.string.app_name));
                    Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    fos = resolver.openOutputStream(imageUri);
                } else {
                    // Below Android 10
                    imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + context.getResources().getString(R.string.app_name);
                    File file = new File(imagesDir);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File image = new File(imagesDir, imageName + timeStamp + ".png");
                    fos = new FileOutputStream(image);
                }

                Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, true);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                callback.onCallback(true);
            } catch (Exception e) {
                callback.onCallback(false);
                e.printStackTrace();
            }

            if (imagesDir != null) {
                // Notify the media scanner about the new file
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(new File(imagesDir));
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
            }
        });
    }



    public void requestWritePermission(Activity activity, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // For Android 9 (Pie) and below, request WRITE_EXTERNAL_STORAGE permission
            PermissionX.init((FragmentActivity) activity)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .onExplainRequestReason((scope, deniedList) -> {
                        String message = "Permission to access your storage is required to save images.";
                        scope.showRequestReasonDialog(deniedList, message, "OK", "Cancel");
                    })
                    .onForwardToSettings((scope, deniedList) -> {
                        String message = "You need to allow necessary permissions in Settings manually";
                        scope.showForwardToSettingsDialog(deniedList, message, "OK", "Cancel");
                    })
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            callback.onPermissionGranted();
                        } else {
                            callback.onPermissionDenied();
                        }
                    });
        } else {
            // For Android 10 and above, no need to request WRITE_EXTERNAL_STORAGE
            callback.onPermissionGranted();
        }
    }

    public interface PermissionCallback {
        void onPermissionGranted();
        void onPermissionDenied();
    }



    public interface ResultCallback {
        void onCallback(boolean b);
    }
}
