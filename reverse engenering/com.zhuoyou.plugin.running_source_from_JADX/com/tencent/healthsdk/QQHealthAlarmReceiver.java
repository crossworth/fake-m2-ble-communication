package com.tencent.healthsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class QQHealthAlarmReceiver extends BroadcastReceiver {
    static final String f3691a = "QQHealthAlarmReceiver";

    public void onReceive(Context context, Intent intent) {
        QQHealthManager instance = QQHealthManager.getInstance();
        if (instance.f3694b == null || instance.f3694b.get() == null) {
            QQHealthManager.m3474b(f3691a, "Health callback is null, so cancel all alarm!");
            instance.m3476b(context);
            return;
        }
        C1188a.m3480a(context, (QQHealthCallback) instance.f3694b.get());
    }
}
