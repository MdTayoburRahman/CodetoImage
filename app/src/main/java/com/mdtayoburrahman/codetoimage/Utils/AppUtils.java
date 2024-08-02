package com.mdtayoburrahman.codetoimage.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.google.android.material.snackbar.Snackbar;
import com.mdtayoburrahman.codetoimage.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AppUtils {

    /**
     * ***NOTE*****
     * <p>
     * ePub lib - https://github.com/HamedTaherpour/ht-epub-reader-android
     * pdf Viewer - https://github.com/barteksc/AndroidPdfViewer
     */

    public static final String TAG = "APP_UTIL_TAG";


    public static String getDisplaySize(Activity activity) {
        double x = 0, y = 0;
        int mWidthPixels, mHeightPixels;
        try {
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            x = Math.pow(mWidthPixels / dm.xdpi, 2);
            y = Math.pow(mHeightPixels / dm.ydpi, 2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.format(Locale.US, "%.2f", Math.sqrt(x + y));


    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * @param bitmap
     * @return String (from given bitmap)
     */
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d(TAG, "BitmapToString: " + temp);
        return temp;

    }

    /**
     * file size
     */
    public static String readableFileSize(long size) {
        if (size <= 0) return "0 Bytes";
        final String[] units = new String[]{"Bytes", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    /**
     * this will check internet connection
     */
    public static boolean checkInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void closeKeyboard(Activity context) {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = context.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {
            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }


    public static void shareImageBitmap(Context context, Bitmap bitmap) {
        // Create a temporary file
        File imageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.png");
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
            showNegativeSnackBar((Activity) context, "Failed to Share image");
            e.printStackTrace();
        }
        // Create a share intent
        Uri imageUri = Uri.fromFile(imageFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        // Start  the share dialog
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    /**
     * reduces the size of the image
     *
     * @param image
     * @param maxSize
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /**
     * this method will create uri to bitmap
     */
    public static Bitmap uriToBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            } else {
                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, uri);
                bitmap = ImageDecoder.decodeBitmap(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * this method will check empty bitmap
     */
    public static boolean checkEmptyBitmap(Bitmap bitmap) {
        Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        // myBitmap is empty/blank
        return bitmap.sameAs(emptyBitmap);
    }

    /**
     * this will send any url to browser
     */
    public static void SendUrlToBrowser(String url, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * this will open url in new custom tab
     */
    public static void OpenCustomTab(Context context, String url) {
        int colorInt = context.getColor(R.color.purple_500); //red
        CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                .setToolbarColor(colorInt)
                .build();

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setDefaultColorSchemeParams(defaultColors);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));

    }

    /**
     * @param context
     * @param message static method which will return a "GREEN" color Snake bar
     */
    public static void showPositiveSnackBar(Activity context, String message) {

        View parentLayout = context.findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                /*.setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })*/
                .setTextColor(context.getColor(R.color.white))
                .setBackgroundTint(context.getColor(R.color.green))
                .setActionTextColor(context.getColor(R.color.purple_200))
                .show();


    }


    /**
     * @param context
     * @param message static method which will return a red color Snake bar
     */
    public static void showNegativeSnackBar(Activity context, String message) {

        View parentLayout = context.findViewById(android.R.id.content);

        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                /*.setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })*/
                .setTextColor(context.getColor(R.color.white))
                .setBackgroundTint(context.getColor(R.color.red))
                .setActionTextColor(context.getColor(R.color.purple_200))
                .show();


    }

    /**
     * this will return current date as a string
     */
    public static String getCurrentDate() {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return dateFormat.format(date);
    }


    /**
     * this will return current date as a string
     */
    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        return df.format(c.getTime());
    }


    /**
     * this method is responsible for creating a layout view into Bitmap image
     *
     * @param view pass any layout view to this params
     */

    public static Bitmap createBitmapFromLayout(View view) {
        Bitmap returnedBitmap = null;
        returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.TRANSPARENT);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    /**
     * this method is responsible for
     * save image to memory for all kinds of android version app
     * ============================================
     *
     * @param context     is activity context
     * @param bitmap      is the bitmap Image which you want to save
     * @param imageHeight the height of the image
     * @param imageWidth  the width of the image
     * @param imageName   the name of the image
     * @param callback    this callback will return a boolean value
     *                    =============================================
     */
    public static void saveImageForAllAndroid(Context context, Bitmap bitmap,
                                              @NonNull String imageName,
                                              int imageHeight,
                                              int imageWidth, AppUtilsCallback callback) {
        OutputStream fos;
        String imagesDir = null;
        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

        try {
            // this is for Android10
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // this permission required  <uses-permission android:name="android.permission.ACCESS_MEDIA_LOCATION"/>
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName + timeStamp);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + context.getResources().getString(R.string.app_name));
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(imageUri);
            } else {
                ///sdcard/DCIM/NewEmoteMaker
                imagesDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM).toString() + File.separator + context.getResources().getString(R.string.app_name);
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
            //  pathname = new File(imagesDir).getAbsolutePath();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File(imagesDir)); //out is your file you saved/deleted/moved/copied
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);

        }

    }


    public static void copyTextToClipboard(Context context, String text, AppUtilsCallback callback) {
        try {
            ClipboardManager clipboard = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", text);
            clipboard.setPrimaryClip(clip);
            callback.onCallback(true);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onCallback(false);
        }
    }


    public static String loadJSONFromAsset(Context context, String jsonFileNameWithExtinction) {
        // this method will return json data from local json file
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileNameWithExtinction);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public interface AppUtilsCallback {
        void onCallback(boolean b);
    }


}
