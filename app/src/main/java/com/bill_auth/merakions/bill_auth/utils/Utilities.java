package com.bill_auth.merakions.bill_auth.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bill_auth.merakions.bill_auth.R;
import com.bill_auth.merakions.bill_auth.UserSelectActivity;
import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bajpa on 10-Feb-18.
 */

public class Utilities {


    public static void saveUserDetails(Context context, UserItem userItem) {
        SharedPreferences.Editor mPref = context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE).edit();

        mPref.putString(Constants.USER_PREF_UID, userItem.getuId());
        mPref.putString(Constants.USER_PREF_USER_NAME, userItem.getName());
        mPref.putString(Constants.USER_PREF_EMAIL, userItem.getEmail());
        mPref.putString(Constants.USER_PREF_MOBILE, userItem.getMobile());
        mPref.putString(Constants.USER_PREF_ADDRESS, userItem.getAddress());
        mPref.apply();
    }

    public static UserItem getSavedUserDetails(Context context) {
        SharedPreferences mPref = context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE);
        UserItem userItem = new UserItem();
        userItem.setuId(mPref.getString(Constants.USER_PREF_UID, ""));
        userItem.setName(mPref.getString(Constants.USER_PREF_USER_NAME, ""));
        userItem.setAddress(mPref.getString(Constants.USER_PREF_ADDRESS, ""));
        userItem.setEmail(mPref.getString(Constants.USER_PREF_EMAIL, ""));
        userItem.setMobile(mPref.getString(Constants.USER_PREF_MOBILE, ""));
        return userItem;
    }

    public static String getUid(Context context) {
        SharedPreferences mPref = context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE);
        return mPref.getString(Constants.USER_PREF_UID, "");
    }

    public static String getUserType(Context context) {
        SharedPreferences mPref = context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE);
        return mPref.getString(Constants.PREF_USER_TYPE, "");
    }

    public static void setUserType(Context context, String userType){
        context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE).edit()
                .putString(Constants.PREF_USER_TYPE,userType).apply();
    }

    public static void clearSharedPrefs(Context context){
        context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE).edit().clear().apply();

    }

    public static void showAlert(Context context, String title, String message, String positiveText, String negativeText) {

        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_alert))
                .show();
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Constants.APP_NAME), Constants.UPLOAD_DIRECTORY_NAME);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        System.out.println("Utilities.createImageFile " + image.getAbsolutePath());
        return image;
    }


    public static boolean createUploadDir() {
        File directory = new File(Environment.getExternalStorageDirectory(), Constants.APP_NAME);
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (Exception e) {
                e.getCause();
                return false;
            }
        }
        File uploadDir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.APP_NAME, Constants.UPLOAD_DIRECTORY_NAME);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdir()) {
                return false;
            }
        }
        return true;

    }

    public static void logout(Context context) {
        Utilities.clearSharedPrefs(context);
        Intent intent = new Intent(context, UserSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static boolean openFileIfExists(Context context, String fileName) {
        File cachedFile = new File(Constants.DOWNLOAD_REPORTS_DIRECTORY_PATH, fileName);
        if (cachedFile.exists()) {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri fileUri = Uri.fromFile(cachedFile);
            intent.setDataAndType(fileUri, getMimeType(context, fileUri));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "No application found that can open this file", Toast.LENGTH_LONG).show();
            }
            return true;
        } else {
            return false;
        }
    }


    public static String getMimeType(Context context, Uri uri) {
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

}
