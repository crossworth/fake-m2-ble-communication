package com.droi.sdk.selfupdate;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.C1044q.C1043a;

public class NotificationClickReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DroiLog.m2871i("BroadcastReceiver", "onReceive");
        DroiUpdateResponse droiUpdateResponse = (DroiUpdateResponse) intent.getExtras().getSerializable("response");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
        C1043a a = C1044q.m3245a(context);
        if (droiUpdateResponse.getUpdateType() != 3) {
            notificationManager.notify("update", 0, a.m3244d("").m3236a());
        }
        DroiUpdate.downloadApp(context, droiUpdateResponse, new C1040n(this, context, notificationManager, droiUpdateResponse));
    }
}
