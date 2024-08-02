package com.mdtayoburrahman.codetoimage.Utils;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static com.mdtayoburrahman.codetoimage.Utils.AppUtils.showNegativeSnackBar;

public class ImageShareUtils {

    public static final String TAG = "ImageShareUtils";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Context context;

    public ImageShareUtils(Context context) {
        this.context = context;
    }

    public Bitmap createBitmapFromLayout(View view) {
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


    public void shareImageBitmap(Bitmap bitmap) {
        if (bitmap == null){
            Log.d(TAG, "shareImageBitmap: ");
            return;
        }
        executor.execute(() -> {
            try {
                // Save the image to the app's files directory
                File imageFile = new File(context.getFilesDir(), "temp_image.png");
                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception
                }

                // Use FileProvider to get a content URI
                Uri imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", imageFile);

                // Create and start the share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "shareImageBitmap: "+e.getMessage());
                // Handle exception on the main thread
                ((Activity) context).runOnUiThread(() -> {
                    AppUtils.showNegativeSnackBar((Activity) context, "Failed to Share image");
                });
            }
        });
    }

}