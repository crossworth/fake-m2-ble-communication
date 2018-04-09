package com.zhuoyou.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.zhuoyou.plugin.cloud.AlarmUtils;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.running.Tools;

public class AutoSyncReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.zhuoyou.running.autosync.alarm")) {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo == null) {
                CloudSync.autoSyncType = 2;
                return;
            }
            CloudSync.autoSyncType = 1;
            CloudSync.startAutoSync(4);
            Tools.setAutoSyncTime(context, System.currentTimeMillis());
            AlarmUtils.setAutoSyncAlarm(context);
            int nType = networkInfo.getType();
            if (nType == 0) {
                CloudSync.startAutoSync(2);
            } else if (nType == 1) {
                CloudSync.startAutoSync(1);
            }
        }
    }
}
