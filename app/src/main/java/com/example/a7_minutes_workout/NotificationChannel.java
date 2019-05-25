package com.example.a7_minutes_workout;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannel extends Application {
    public static final String CHANNEL_1_ID = "channel";
    @Override
    public void onCreate() {
        super.onCreate();
        creatNotificationChannel();
    }

    private void creatNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
