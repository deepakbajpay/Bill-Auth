package com.bill_auth.merakions.bill_auth.services;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bill_auth.merakions.bill_auth.R;
import com.bill_auth.merakions.bill_auth.ShopKeeperMainActivity;
import com.bill_auth.merakions.bill_auth.WholeMainactivity;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().get("message") != null) {
            sendNotification(remoteMessage.getData(), "");
        }

    }

    private void sendNotification(Map<String, String> messageBody, String messageB) {
        String message, activityName;
        message = messageBody.get("message");
        activityName = messageBody.get(Constants.TAG_ACTIVITY_NAME);
        Intent intent;
        if (Constants.TAG_WHOLE_SALER_ACTIVITY.equals(activityName)) {
            intent = new Intent(this, ShopKeeperMainActivity.class);
        } else
            intent = new Intent(this, WholeMainactivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(myNotificationChannel,
                    "EzeGst Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(getApplicationContext(), myNotificationChannel)
                    .setSmallIcon(R.drawable.ic_notif_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setColor(getColor(R.color.button2))
                    .setContentIntent(pendingIntent)
                    .setStyle(new Notification.BigTextStyle().bigText(body)).build();
            notificationManager.notify(0, notification);
        }*/

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
