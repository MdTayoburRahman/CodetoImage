package com.mdtayoburrahman.codetoimage.Activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.mdtayoburrahman.codetoimage.Adapters.ImageAdapter;
import com.mdtayoburrahman.codetoimage.Listeners.OnSavedImageItemClickListener;
import com.mdtayoburrahman.codetoimage.Model.ImageDataModel;
import com.mdtayoburrahman.codetoimage.R;
import com.mdtayoburrahman.codetoimage.Utils.AppUtils;
import com.mdtayoburrahman.codetoimage.Utils.ImageSaveUtils;
import com.mdtayoburrahman.codetoimage.Utils.ImageShareUtils;
import com.mdtayoburrahman.codetoimage.databinding.ActivitySavedImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SavedImageActivity extends BaseActivity {

    private ActivitySavedImageBinding binding;
    private List<ImageDataModel> imageDataModelList;
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;
    private Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        topBar();
        myAnim = AnimationUtils.loadAnimation(this, R.anim.click_anim);
        imageDataModelList = new ArrayList<>();
        recyclerView = binding.ImageRecyclerView;
        checkPermissionAndLoadImage();


    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void topBar() {
        MaterialToolbar topAppBar = binding.materialToolbar;
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });

    }



    @SuppressLint("NotifyDataSetChanged")
    public void getAllShownImagesPath(Context context) {
        /**this mathod works to get all shapes from gallery**/
        imageDataModelList.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            //   ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;

            uri = MediaStore.Files.getContentUri("external");
            String selection = null;
            selection = MediaStore.Images.Media.RELATIVE_PATH + " like ? ";
            //String[] selectionArgs = new String[]{"%PictureQuotesMaker%"};
            String[] selectionArgs = new String[]{"%"+context.getResources().getString(R.string.app_name)+"%"};

            /**
             * must use
             * @selection and
             * @selectionArgs
             *
             * to find specific folder shapes **/
            cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            // this is for singal data
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                if (absolutePathOfImage.endsWith(".jpg") ||
                        absolutePathOfImage.endsWith(".png")) {
                    imageDataModelList.add(new ImageDataModel(absolutePathOfImage));
                }

                Log.d("KIBREA", "getAllShownImagesPath: allImgePath" + absolutePathOfImage);
            }

            if (imageDataModelList != null) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    binding.animationView.setVisibility(View.GONE);
                }
                //set the data to recycaler view
                recyclerView.setLayoutManager(new GridLayoutManager(SavedImageActivity.this, 4));
                imageAdapter = new ImageAdapter(imageDataModelList, SavedImageActivity.this, new OnSavedImageItemClickListener() {
                    @Override
                    public void OnSavedImageClick(String path) {
                        showImageDialog(path);
                    }
                });
                recyclerView.getRecycledViewPool().clear();
                imageAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(imageAdapter);
            }


        } else {

            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + context.getResources().getString(R.string.app_name);
            File file = new File(imagesDir);
            if (!file.exists()) {
                file.mkdir();
            }
            File[] imageFile = file.listFiles();

            if (imageFile != null) {
                ArrayList<File> myimageFile = findImage(file);
                for (int j = 0; j < myimageFile.size(); j++) {

                    imageDataModelList.add(new ImageDataModel(String.valueOf(myimageFile.get(j))));
                    if (j == myimageFile.size() - 1) {
                        // this will check for loop is finish
                        // then its run the task for Recycler view
                    }

                }
                if (imageDataModelList!=null) {
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                        binding.animationView.setVisibility(View.GONE);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(SavedImageActivity.this, 4));
                    recyclerView.setItemAnimator(null);
                    imageAdapter = new ImageAdapter(imageDataModelList, SavedImageActivity.this, new OnSavedImageItemClickListener() {
                        @Override
                        public void OnSavedImageClick(String path) {
                            showImageDialog(path);
                        }
                    });
                    imageAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(imageAdapter);

                }

            } else {
                Toast.makeText(SavedImageActivity.this, "Image not Found", Toast.LENGTH_SHORT).show();
            }


        }
        //show alert dialog if image not available
        if (imageDataModelList== null || imageDataModelList.size() ==0) {
            recyclerView.setVisibility(View.GONE);
            binding.animationView.setVisibility(View.VISIBLE);

        }


    }

    private void checkPermissionAndLoadImage(){
        ImageSaveUtils saveUtils = new ImageSaveUtils(SavedImageActivity.this);
        saveUtils.requestReadPermission(SavedImageActivity.this, new ImageSaveUtils.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                getAllShownImagesPath(SavedImageActivity.this);
            }

            @Override
            public void onPermissionDenied() {
                // Permission denied, handle accordingly
                AppUtils.showNegativeSnackBar(SavedImageActivity.this,"Unable to load Images, Permission Denied");

            }
        });
    }

    private void showImageDialog(String imagePath) {
        // Inflate the custom dialog view
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_image_view, null);

        // Find the ImageView and buttons in the dialog layout
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        Button btnShare = dialogView.findViewById(R.id.btn_share);
        Button btnClose = dialogView.findViewById(R.id.btn_close);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);

        // Load the image using Glide
        Glide.with(this)
                .load(imagePath)
                .into(imageView);

        // Create the custom AlertDialog
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        // Set click listeners for buttons
        btnShare.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            // Share the image logic here
            ImageShareUtils imageShareUtils = new ImageShareUtils(SavedImageActivity.this);
            imageShareUtils.shareImageFromPath(imagePath);
        });

        btnClose.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            // Close the dialog
            alertDialog.dismiss();
        });

        btnDelete.setOnClickListener(v -> {
            v.startAnimation(myAnim);
            // Delete the image
            File imgFile = new File(imagePath);
            if (imgFile.delete()) {
                Toast.makeText(SavedImageActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
                // Refresh the image list
                getAllShownImagesPath(SavedImageActivity.this);
            }
            alertDialog.dismiss();
        });

        // Show the dialog
        alertDialog.show();
    }

    public ArrayList<File> findImage(File file) {

        ArrayList<File> imageList = new ArrayList<>();
        File[] imageFile = file.listFiles();
        for (File singleimage : imageFile) {
            if (singleimage.isDirectory() && !singleimage.isHidden()) {
                imageList.addAll(findImage(singleimage));
            } else {
                if (singleimage.getName().endsWith(".jpg") ||
                        singleimage.getName().endsWith(".png")
                ) {
                    imageList.add(singleimage);
                }
            }
        }

        return imageList;
    }
}