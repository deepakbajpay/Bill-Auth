package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bill_auth.merakions.bill_auth.adapters.AddDocumentsAdapter;
import com.bill_auth.merakions.bill_auth.adapters.AddPhotoAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, AddPhotoAdapter.PhotoAdapterClickCallback, AddDocumentsAdapter.AddDocumentCallback {

    private RecyclerView uploadPhotosRv,uploadDocumentsRv;
    private Button uploadButton;
    AddPhotoAdapter addPhotoAdapter;
    AddDocumentsAdapter addDocumentsAdapter;
    private ArrayList<String> imageList;
    private ArrayList<String> selectedFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        imageList = new ArrayList<>();
        selectedFiles =new ArrayList<>();

        uploadPhotosRv = findViewById(R.id.upload_photos_rv);
        uploadDocumentsRv = findViewById(R.id.upload_documents_rv);
        uploadButton = findViewById(R.id.upload_documents_button);

        LinearLayoutManager addPhotoManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        uploadPhotosRv.setLayoutManager(addPhotoManager);
        LinearLayoutManager addDocumentManager = new LinearLayoutManager(this);
        addDocumentManager.setReverseLayout(true);
        addDocumentManager.setStackFromEnd(true);
        uploadDocumentsRv.setLayoutManager(addDocumentManager);
        addPhotoAdapter = new AddPhotoAdapter(this, imageList, this);
        uploadPhotosRv.setAdapter(addPhotoAdapter);

        addDocumentsAdapter = new AddDocumentsAdapter(this, selectedFiles, this);
        uploadDocumentsRv.setAdapter(addDocumentsAdapter);

        uploadButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upload_documents_button:

                break;
        }
    }

    @Override
    public void onPhotoAdapterItemClick(int itemid, int position) {

    }

    @Override
    public void onImageClicked(int layoutPosition) {

    }

    @Override
    public void onAddDocumentAdapterClick(int id, int position) {

    }
}
