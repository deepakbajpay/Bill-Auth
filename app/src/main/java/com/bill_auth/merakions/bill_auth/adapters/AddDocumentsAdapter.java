package com.bill_auth.merakions.bill_auth.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill_auth.merakions.bill_auth.R;

import java.util.List;

/**
 * Created by rajat on 7/3/2017.
 */

public class AddDocumentsAdapter extends RecyclerView.Adapter<AddDocumentsAdapter.MyViewHolder> {
    List<String> itemList;
    private Context context;
    private AddDocumentCallback callback;
    private Animation revealAnim, hideAnim;

    public AddDocumentsAdapter(Context context, List<String> itemList, AddDocumentCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
        revealAnim = AnimationUtils.loadAnimation(context, R.anim.view_reveal);
        hideAnim = AnimationUtils.loadAnimation(context, R.anim.view_hide);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_single_document_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == itemList.size()) {
            holder.clearDocumentIv.setVisibility(View.GONE);
            holder.editDocumentIv.setVisibility(View.GONE);
            holder.addDocumentTv.setText(context.getString(R.string.add_documents_from_storage));
            holder.documentExtIv.setImageResource(R.drawable.ic_add_document);
            return;
        }

        Uri uri;

        String extension = null;
        try {
            uri = Uri.parse(itemList.get(position));
            String[] filePathSplit = uri.getLastPathSegment().split("\\.");
            extension = filePathSplit[filePathSplit.length - 1];
            holder.addDocumentTv.setText(uri.getLastPathSegment());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.clearDocumentIv.setVisibility(View.VISIBLE);
        holder.editDocumentIv.setVisibility(View.VISIBLE);

        switch (extension) {
            case "pdf":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_pdf));
                break;
            case "csv":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_csv));
                break;
            case "txt":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_txt));
                break;
            case "zip":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_zip));
                break;
            case "xls":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_xls));
                break;
            case "doc":
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_doc));
                break;
            default:
                holder.documentExtIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_filetype_other));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size() + 1;
    }


    public interface AddDocumentCallback {
        void onAddDocumentAdapterClick(int id, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView addDocumentTv;
        EditText documentDescriptionEt;
        ImageView clearDocumentIv, documentExtIv, editDocumentIv;

        public MyViewHolder(final View itemView) {
            super(itemView);
            addDocumentTv = itemView.findViewById(R.id.add_document_tv);
            clearDocumentIv = itemView.findViewById(R.id.remove_document_iv);
            documentExtIv = itemView.findViewById(R.id.add_document_iv);
            editDocumentIv = itemView.findViewById(R.id.edit_document_iv);
            documentDescriptionEt = itemView.findViewById(R.id.document_description_et);

            addDocumentTv.setOnClickListener(this);
            clearDocumentIv.setOnClickListener(this);
            editDocumentIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.edit_document_iv) {
                if (documentDescriptionEt.getVisibility() == View.GONE) {
                    documentDescriptionEt.setVisibility(View.VISIBLE);
                    documentDescriptionEt.startAnimation(revealAnim);
                } else {
                    documentDescriptionEt.startAnimation(hideAnim);
                    hideAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            documentDescriptionEt.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }

                return;
            }
            callback.onAddDocumentAdapterClick(view.getId(), getLayoutPosition());
        }
    }
}
