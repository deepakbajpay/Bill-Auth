package com.bill_auth.merakions.bill_auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.adapters.AddDocumentsAdapter;
import com.bill_auth.merakions.bill_auth.adapters.AddPhotoAdapter;
import com.bill_auth.merakions.bill_auth.beanclasses.BillItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.FileUtil;
import com.bill_auth.merakions.bill_auth.utils.NotificationHandler;
import com.bill_auth.merakions.bill_auth.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, AddPhotoAdapter.PhotoAdapterClickCallback, AddDocumentsAdapter.AddDocumentCallback {

    AddPhotoAdapter addPhotoAdapter;
    AddDocumentsAdapter addDocumentsAdapter;
    DecimalFormat df = new DecimalFormat("0.00");
    Map<String, BillItem> downloadUrls;
    private RecyclerView uploadPhotosRv, uploadDocumentsRv;
    private Button uploadButton;
    private ArrayList<String> imageList;
    private ArrayList<String> selectedFiles;
    private Uri photoURI;
    private float totalFileSize;
    private TextView photoAmountTv;
    private TextView documentAmountTv;
    private TextView uploadAllMessagesTv;
    private StorageReference mStorageRef;
    private String recieverUid;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        recieverUid = getIntent().getStringExtra(Constants.U_ID_TAG);

        imageList = new ArrayList<>();
        selectedFiles = new ArrayList<>();
        downloadUrls = new HashMap<>();
        recieverUid = Utilities.getUid(this);

        uploadPhotosRv = findViewById(R.id.upload_photos_rv);
        uploadDocumentsRv = findViewById(R.id.upload_documents_rv);
        uploadButton = findViewById(R.id.upload_documents_button);
        photoAmountTv = findViewById(R.id.add_photos_amount_tv);
        documentAmountTv = findViewById(R.id.document_number_tv);

        LinearLayoutManager addPhotoManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        uploadPhotosRv.setLayoutManager(addPhotoManager);
        LinearLayoutManager addDocumentManager = new LinearLayoutManager(this);
        addDocumentManager.setReverseLayout(true);
        addDocumentManager.setStackFromEnd(true);

        uploadDocumentsRv.setLayoutManager(addDocumentManager);
        addPhotoAdapter = new AddPhotoAdapter(this, imageList, this);
        uploadPhotosRv.setAdapter(addPhotoAdapter);
        uploadAllMessagesTv = findViewById(R.id.upload_documents_all_messages);
        uploadAllMessagesTv.setText(getString(R.string.max_upload_limit));
        addDocumentsAdapter = new AddDocumentsAdapter(this, selectedFiles, this);
        uploadDocumentsRv.setAdapter(addDocumentsAdapter);

        uploadButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_documents_button:
                ArrayList<String> list = new ArrayList<>();
                list.addAll(imageList);
                list.addAll(selectedFiles);
                i = 0;
                uploadFromUri(list);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "We can't proceed without Storage permission", Toast.LENGTH_SHORT).show();
                return;
            }
            if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "We can't proceed without Camera permission", Toast.LENGTH_SHORT).show();
                return;
            }
            showDialogPicture();
        }
        if (requestCode == Constants.DOCUMENT_PIC_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                picDocument();
            } else
                Toast.makeText(this, "Please give storage permission to perform this action", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Now you can proceed to download", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Can't download file without write permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FilePickerConst.REQUEST_CODE_DOC && data != null) {
                addToSelectedFiles(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
            } else if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && data != null) {
                addToImageListFromGallery(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
            }
            if (requestCode == Constants.CODE_CLICK_IMAGE) {
                Uri uri = photoURI;
                if (uri != null) {
                    String compressedImagePath = FileUtil.compressImage(this, uri);
                    if (compressedImagePath != null)
                        addToImageList(compressedImagePath);
                    else
                        Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onPhotoAdapterItemClick(int itemid, int position) {
        if (itemid == R.id.upload_thumbnail_iv)
            if (position == imageList.size()) {
                getStoragePermission(Constants.CAMERA_PERMISSION_CODE);
                return;
            }
        if (itemid == R.id.clear_image_thumbnail_iv) {
            clearImage(position);
        }
    }

    @Override
    public void onImageClicked(int layoutPosition) {

    }

    @Override
    public void onAddDocumentAdapterClick(int id, int position) {
        if (id == R.id.remove_document_iv) {
            decreaseFromFileSize(selectedFiles.get(position));
            selectedFiles.remove(position);
            documentAmountTv.setText(getString(R.string.photo_number_format, selectedFiles.size()));
            if (imageList.size() + selectedFiles.size() == 0) {
                if (uploadButton.isEnabled()) {
                    uploadButton.setEnabled(false);
                    uploadButton.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder_color));
                }
            }
            addDocumentsAdapter.notifyDataSetChanged();
        }
        if (id == R.id.add_document_tv) {
            View v = uploadDocumentsRv.getChildAt(position);
            TextView textView = v.findViewById(R.id.add_document_tv);
            if (textView.getText().equals(getString(R.string.add_documents_from_storage))) {
                if (!Utilities.createUploadDir()) {
                    Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                    return;
                }
                picDocument();
            }
        }
        if (id == R.id.edit_document_iv) {

        }
    }

    private void clearImage(int position) {
        decreaseFromFileSize(imageList.get(position));

        imageList.remove(position);


        if (totalFileSize == 0) {
            if (uploadButton.isEnabled()) {
                uploadButton.setEnabled(false);
                uploadButton.setBackgroundColor(ContextCompat.getColor(this, R.color.placeholder_color));
            }
        }
        addPhotoAdapter.notifyDataSetChanged();
    }

    public void picDocument() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.DOCUMENT_PIC_PERMISSION_CODE);
                return;
            }
        }

        FilePickerBuilder.getInstance().setMaxCount(5)
                .pickFile(this);
    }

    public void showDialogPicture() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    takePicture();
                } else if (items[item].equals("Choose from Gallery")) {
                    openGallery();
                }
            }
        });
        builder.show();
    }

    public void getStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, requestCode);
                return;
            }
        }
        showDialogPicture();
    }

    public void takePicture() {
        if (!Utilities.createUploadDir()) {
            Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = Utilities.createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                try {
                    if (Build.VERSION.SDK_INT < 24)
                        photoURI = Uri.fromFile(photoFile);
                    else
                        photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".com.bill_auth.merakions.bill_auth.provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    this.startActivityForResult(takePictureIntent, Constants.CODE_CLICK_IMAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to create image Path", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openGallery() {
        if (!Utilities.createUploadDir()) {
            Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
            return;
        }
        FilePickerBuilder.getInstance().setMaxCount(5)
                .enableCameraSupport(false)
                .pickPhoto(this);
    }

    public void addToImageList(String imgPath) {
        if (!uploadButton.isEnabled()) {
            uploadButton.setEnabled(true);
        }
        String actualName = Uri.parse(imgPath).getLastPathSegment();
        imageList.add(imgPath);
        uploadPhotosRv.smoothScrollToPosition(imageList.size());
        addPhotoAdapter.notifyDataSetChanged();
    }

    public void addToSelectedFiles(ArrayList<String> files) {
        boolean duplicateFile = false;
        for (int i = 0; i < files.size(); i++) {
            String filePath = files.get(i);
            if (!selectedFiles.contains(filePath)) {
                addToFileSize(filePath);
                if (totalFileSize > Constants.MAX_UPLOADABLE_SIZE_MB) {
                    decreaseFromFileSize(filePath);
                    Toast.makeText(this, "Sum of file sizes should not exceed 5MB", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!uploadButton.isEnabled()) {
                    uploadButton.setEnabled(true);

                }

                selectedFiles.add(filePath);
                documentAmountTv.setText(getString(R.string.photo_number_format, selectedFiles.size()));
                String actualName = Uri.parse(filePath).getLastPathSegment();
                String[] parts = actualName.split("\\.");
                String extension = parts[parts.length - 1];
            } else
                duplicateFile = true;
        }
        if (duplicateFile) {
            Toast.makeText(this, "Can not add same file twice", Toast.LENGTH_SHORT).show();
        }
        addDocumentsAdapter.notifyDataSetChanged();
    }

    public void addToImageListFromGallery(ArrayList<String> selectedImages) {
        boolean duplicateSelection = false;
        for (int i = 0; i < selectedImages.size(); i++) {
            String imgPath = FileUtil.compressImage(this, selectedImages.get(i));
            if (!imageList.contains(imgPath)) {
                addToFileSize(imgPath);
                if (totalFileSize > Constants.MAX_UPLOADABLE_SIZE_MB) {
                    decreaseFromFileSize(imgPath);
                    Toast.makeText(this, "Sum of file sizes should not exceed 5MB", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!uploadButton.isEnabled()) {
                    uploadButton.setEnabled(true);
                }
                imageList.add(imgPath);
                Uri uri = Uri.parse(imgPath);
                uploadPhotosRv.smoothScrollToPosition(imageList.size());
                photoAmountTv.setText(getString(R.string.photo_number_format, imageList.size()));
                String actualName = uri.getLastPathSegment();
                String[] parts = imgPath.split("\\.");
                String extension = parts[parts.length - 1];
                addPhotoAdapter.notifyDataSetChanged();
            } else
                duplicateSelection = true;
        }
        if (duplicateSelection)
            Toast.makeText(this, "Can not add same file twice", Toast.LENGTH_LONG).show();
    }

    public void addToFileSize(String filePath) {
        float size = new File(filePath).length();
        totalFileSize += size / (1024 * 1024);
        updateFileSizeText(totalFileSize);
    }

    public void decreaseFromFileSize(String filePath) {
        float size = new File(filePath).length();
        totalFileSize -= size / (1024 * 1024);
        updateFileSizeText(totalFileSize);
    }

    public void updateFileSizeText(float number) {
        uploadAllMessagesTv.setText(getString(R.string.max_upload_limit_formatted, df.format(number)));
    }


    private void uploadFromUri(final ArrayList<String> fileUris) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        try {


            Uri fileUri;
            if (Build.VERSION.SDK_INT < 24)
                fileUri = Uri.fromFile(new File(fileUris.get(i)));
            else
                fileUri = FileProvider.getUriForFile(this,
                        this.getApplicationContext().getPackageName() + ".com.bill_auth.merakions.bill_auth.provider", new File(fileUris.get(i)));

            final StorageReference photoRef = mStorageRef.child("bills").child(Utilities.getUid(this))
                    .child(fileUri.getLastPathSegment());

            photoRef.putFile(fileUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Upload succeeded
                            Log.d(null, "uploadFromUri:onSuccess");
                            // Get the public download URL
                            Uri mDownloadUrl = taskSnapshot.getDownloadUrl();

                            BillItem billItem = new BillItem();
                            billItem.setBillUrl(mDownloadUrl.toString());
                            billItem.setTimestamp(getDate());
                            billItem.setVerify(false);
                            billItem.setSenderUid(Utilities.getUid(UploadActivity.this));
                            billItem.setReceiverUid(recieverUid);
                            downloadUrls.put(getDate()+"",billItem);

                            if (i < fileUris.size() - 1) {
                                i++;
                                uploadFromUri(fileUris);
                            } else {
                                mapFileNamesAndReciever(recieverUid);
                            }

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Upload failed

                            Log.w(null, "uploadFromUri:onFailure", exception);
                            Toast.makeText(UploadActivity.this, "" + exception.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapFileNamesAndReciever(final String recieverUid) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.CHILD_BILLS).child(Utilities.getUid(this)).child(recieverUid);
        System.out.println("UploadActivity.mapFileNamesAndReciever " + downloadUrls);
        dbRef.setValue(downloadUrls).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UploadActivity.this, "Files Uploaded Successfuly", Toast.LENGTH_SHORT).show();
                imageList.clear();
                selectedFiles.clear();
                addDocumentsAdapter.notifyDataSetChanged();
                addPhotoAdapter.notifyDataSetChanged();
                downloadUrls.clear();
                totalFileSize = 0;
                updateFileSizeText(totalFileSize);
                NotificationHandler.pushNotificationInFirebase(Utilities.getUid(UploadActivity.this)
                        , "You have new bills", Constants.TAG_SHOP_KEEPER_ACTIVITY, recieverUid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("mapFiles", e.getLocalizedMessage());
                Toast.makeText(UploadActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public long getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

}
