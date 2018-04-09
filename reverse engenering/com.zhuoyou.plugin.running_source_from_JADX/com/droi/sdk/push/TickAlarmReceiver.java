package com.droi.sdk.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;

public class TickAlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (C1015j.m3156a(context)) {
            C1012g.m3141c("receive tick alarm signal");
            try {
                Intent intent2 = new Intent("com.droi.sdk.push.action.START");
                intent2.putExtra("CMD", "TICK");
                intent2.setPackage(context.getPackageName());
                context.startService(intent2);
            } catch (Throwable th) {
                C1012g.m3137a(new Exception(th));
            }
        }
    }
}
