package com.bill_auth.merakions.bill_auth.utils;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bajpay on 23-Mar-17.
 */

public class NotificationHandler {
    public static void pushNotificationInFirebase(final String topic, final String message, final String activityToOpen, final String recieverKey) {

        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(tp);
            }
//
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "https://fcm.googleapis.com/fcm/send";
                    String result = "";
                    String clientkey = "AAAAMuO8a6k:APA91bFTD_Y2CsOh0LaLrCcIbwFzNJM0hZiTFCGX_V1y-GOQX16oWmpqaDAhNUxMtVhOlPVRACa2moNhq24DZ6LCDBKM1U681Z_oBRpFhG11X7hp26NaiJwNTFKaJDrQXXxEpVNGnf0r";
                    HttpURLConnection httpcon = null;
                    try {
                        //Connect
                        httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
                        httpcon.setDoOutput(true);
                        httpcon.setDoInput(true);
                        httpcon.setRequestProperty("Content-Type", "application/json");
                        httpcon.setRequestProperty("Authorization", clientkey);
                        httpcon.setRequestMethod("POST");
                        httpcon.connect();
                        //Write


                        JSONObject object = new JSONObject();
                        try {
                            object.put("to", "/topics/" + recieverKey);
                        } catch (Exception e) {
                        }
                        JSONObject data = new JSONObject();
                        try {
                            data.put("message", message);
                            data.put(Constants.TAG_ACTIVITY_NAME, activityToOpen);
                        } catch (Exception e) {
                        }
/*
                        try
                        {
                            data.put("extmess",messageevent);
                        }catch (Exception e){}*/

                        try {
                            object.put("data", data);
                            object.put("activity_name", activityToOpen);
                        } catch (Exception e) {
                        }

                        OutputStream os = httpcon.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        //            writer.write(getPostDataString(postDataParams));
                        writer.write(object.toString());
                        writer.flush();
                        writer.close();
                        os.close();

                        //Read
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));

                        String line = null;
                        StringBuilder sb = new StringBuilder();

                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        br.close();
                        result = sb.toString();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        try {
                            int statusCode = httpcon.getResponseCode();
                            Log.e("", "" + statusCode);
                        } catch (Exception eee) {
                            eee.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
