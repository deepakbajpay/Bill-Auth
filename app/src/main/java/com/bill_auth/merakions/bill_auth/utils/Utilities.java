package com.bill_auth.merakions.bill_auth.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bill_auth.merakions.bill_auth.R;
import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;

/**
 * Created by bajpa on 10-Feb-18.
 */

public class Utilities {


    public static void saveUserDetails(Context context, UserItem userItem){
        SharedPreferences.Editor mPref = context.getSharedPreferences(Constants.WHOLE_SALER_PREF_TAG, Context.MODE_PRIVATE).edit();

        mPref.putString(Constants.USER_PREF_UID, userItem.getuId());
        mPref.putString(Constants.USER_PREF_USER_NAME, userItem.getName());
        mPref.putString(Constants.USER_PREF_EMAIL, userItem.getEmail());
        mPref.putString(Constants.USER_PREF_MOBILE, userItem.getMobile());
        mPref.putString(Constants.USER_PREF_ADDRESS, userItem.getAddress());
        mPref.putString(Constants.PREF_USER_TYPE, Constants.WHOLE_SALER);
        mPref.apply();
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
}
