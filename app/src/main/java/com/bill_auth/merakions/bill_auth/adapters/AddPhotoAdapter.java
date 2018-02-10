package com.bill_auth.merakions.bill_auth.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.R;

import java.util.List;

/**
 * Created by rajat on 7/3/2017.
 */

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.MyViewHolder> {

    List<String> selectedItemList;
    int viewPosition;
    private Context context;
    private PhotoAdapterClickCallback callback;

    public AddPhotoAdapter(Context context, List<String> selectedItemList, PhotoAdapterClickCallback callback) {
        this.context = context;
        this.selectedItemList = selectedItemList;
        this.callback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_single_add_photo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        viewPosition = position;

        if (position == selectedItemList.size()) {
            holder.selectedImage.setImageDrawable(null);
            holder.clearImage.setVisibility(View.GONE);
            holder.editImage.setVisibility(View.GONE);
            return;
        }

        Bitmap resized = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(selectedItemList.get(position)), 128, 128);
        holder.selectedImage.setImageBitmap(resized);
        holder.clearImage.setVisibility(View.VISIBLE);
        holder.editImage.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return selectedItemList.size() + 1;
    }

    public interface PhotoAdapterClickCallback {
        void onPhotoAdapterItemClick(int itemid, int position);

        void onImageClicked(int layoutPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView selectedImage, iconImage, clearImage, editImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            selectedImage = itemView.findViewById(R.id.upload_thumbnail_iv);
            iconImage = itemView.findViewById(R.id.add_photo_icon_iv);
            clearImage = itemView.findViewById(R.id.clear_image_thumbnail_iv);
            editImage = itemView.findViewById(R.id.edit_image_thumbnail_iv);

            selectedImage.setOnClickListener(this);
            clearImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.upload_thumbnail_iv:
                    if (getLayoutPosition() == selectedItemList.size()) {
                        callback.onPhotoAdapterItemClick(view.getId(), getLayoutPosition());
                    } else {
                        callback.onImageClicked(getLayoutPosition());
                    }
                    break;
                case R.id.clear_image_thumbnail_iv:
                    callback.onPhotoAdapterItemClick(view.getId(), getLayoutPosition());
                    break;
            }
        }
    }


    @SuppressLint("ParcelCreator")
    private class ErrorResultReceiver extends ResultReceiver {
        public ErrorResultReceiver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        protected void onReceiveResult(int resultCode,
                                       Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (context != null) {
                Toast
                        .makeText(context, "We had an error",
                                Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
