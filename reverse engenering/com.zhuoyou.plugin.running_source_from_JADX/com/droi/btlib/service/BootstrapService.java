package com.droi.btlib.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat.Builder;
import com.droi.btlib.C0687R;
import com.droi.btlib.connection.MessageObj;

public class BootstrapService extends Service {
    public void onCreate() {
        super.onCreate();
        startForeground(this);
        stopSelf();
    }

    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public static void startForeground(Service context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
        Builder builder = new Builder(context);
        builder.setContentTitle("Droi Bluetooth Service").setContentText("Thanks for using Droi.").setWhen(System.currentTimeMillis()).setPriority(-2).setSmallIcon(C0687R.drawable.logo).setAutoCancel(true);
        context.startForeground(87, builder.build());
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
