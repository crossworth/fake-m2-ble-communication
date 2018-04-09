package com.zhuoyi.system.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.util.TimerManager;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.ZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.CommConstants;

public class PhoneStatReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent();
        if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
            intent1.setAction(CommConstants.RECEIVER_ACTION_MEDIA_MOUNTED);
            context.sendBroadcast(intent1);
        } else if (intent.getAction().equals("android.intent.action.MEDIA_UNMOUNTED")) {
            intent1.setAction(CommConstants.RECEIVER_ACTION_MEDIA_UNMOUNTED);
            context.sendBroadcast(intent1);
        } else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            intent1.setAction(CommConstants.RECEIVER_ACTION_NET_CHANGED);
            context.sendBroadcast(intent1);
            Intent serviceIntent = new Intent(context, ZyService.class);
            serviceIntent.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, ZyServiceFactory.HANDLE_AUTO_DOWN_APK_SERVICE.getServiceId());
            context.startService(serviceIntent);
            serviceIntent = new Intent(context, ZyService.class);
            serviceIntent.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, ZyServiceFactory.SILENT_ANCTION_SERVICE.getServiceId());
            serviceIntent.putExtra("autoDownloadFlag", true);
            context.startService(serviceIntent);
            Logger.m3373e("PhoneStatReceiver", "Cur net type = " + NetworkUtils.getNetworkType(context));
            if (NetworkUtils.getNetworkType(context) != (byte) 0) {
                Logger.m3373e("PhoneStatReceiver", "Show push because net change!");
                showPushNotifyTimer(context);
            }
        }
    }

    public static void showPushNotifyTimer(Context context) {
        Bundle b = new Bundle();
        b.putInt(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, -2);
        b.putInt(BundleConstants.BUNDLE_NOTIFICATION_FROM, 3);
        TimerManager.getInstance(context).startTimerByTime(System.currentTimeMillis() + 5000, ZyServiceFactory.SHOW_PUSH_NOTIFY_SERVICE.getServiceId(), b);
    }
}
