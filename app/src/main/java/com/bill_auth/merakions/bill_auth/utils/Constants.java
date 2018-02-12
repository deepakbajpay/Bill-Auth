package com.bill_auth.merakions.bill_auth.utils;

import android.os.Environment;

/**
 * Created by bajpa on 10-Feb-18.
 */

public class Constants {

    public static final String APP_NAME = "Bill-Auth";

    public static final String WHOLE_SALER_PREF_TAG = "whole_saler_preferences";
    public static final String NGO_PREF_NAME = "ngo_preferences";

    public static final String PREF_USER_TYPE = "user_type";

    // User Shared preferences tags

    public static final String USER_PREF_UID = "uid";
    public static final String USER_PREF_USER_NAME = "name";
    public static final String USER_PREF_ADDRESS = "address";
    public static final String USER_PREF_MOBILE = "mobile";
    public static final String USER_PREF_EMAIL = "email";


    public static final String CHILD_WHOLE_SALER = "wholesaler";
    public static final String CHILD_SHOP_KEEPER = "shop_keeper";
    public static final String CHILD_BILLS = "bills";

    public static final String USER_TYPE_TAG = "user_type";
    public static final String WHOLE_SALER = "wholesaller";
    public static final String SHOP_KEEPER = "shop_keeper";

    public static final String UPLOAD_DIRECTORY_NAME = "Uploads";
    public static final String UPLOAD_DIRECTORY_PATH = Environment.getExternalStoragePublicDirectory(Constants.APP_NAME) + "/" + Constants.UPLOAD_DIRECTORY_NAME;

    public static final int CAMERA_PERMISSION_CODE = 1001;
    public static final int DOCUMENT_PIC_PERMISSION_CODE = 1002;
    public static final int CODE_CLICK_IMAGE = 1003;
    public static final String U_ID_TAG = "u_id";


    public static int STORAGE_PERMISSION_CODE = 1004;
    public static int MAX_UPLOADABLE_SIZE_MB = 5;
}
