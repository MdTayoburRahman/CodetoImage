package com.mdtayoburrahman.codetoimage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mdtayoburrahman.codetoimage.Listeners.OnSavedImageItemClickListener;
import com.mdtayoburrahman.codetoimage.Model.ImageDataModel;
import com.mdtayoburrahman.codetoimage.R;

import java.util.List;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    List<ImageDataModel> imagepathlist;
    Context context;
    OnSavedImageItemClickListener listener;

    public ImageAdapter(List<ImageDataModel> imagepathlist, Context context, OnSavedImageItemClickListener listener) {
        this.imagepathlist = imagepathlist;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item_raw, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context)
                .load(imagepathlist.get(position).getImagePath())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            listener.OnSavedImageClick(imagepathlist.get(position).getImagePath());
        });

    }

    @Override
    public int getItemCount() {

        return (imagepathlist != null) ? imagepathlist.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.galleryImageView);
        }
    }



}
