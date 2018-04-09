package com.zhuoyou.plugin.running.service;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.SPUtils.SharePrefrenceChange;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NotificationServiceNew extends NotificationListenerService {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String[] TickerFilter = new String[]{"开始下载", "正在下载"};
    private List<String> infoList = new ArrayList();
    private BtDevice mBtDevice = null;
    private ConnectCallback mConnectCallback = new C19161();

    class C19161 implements ConnectCallback {
        C19161() {
        }

        public void success(BtDevice device) {
            Log.i("chenxinx", "mConnectCallback success");
            NotificationServiceNew.this.mBtDevice = device;
        }

        public void fail(int state) {
        }

        public void disconnect(BtDevice device) {
        }

        public void connecting(BtDevice device) {
        }

        public void battery(int battery) {
        }
    }

    public void onNotificationPosted(StatusBarNotification sbn) {
        boolean z = true;
        Log.i("chenxinx", "onNotificationPosted:" + sbn);
        Notification mNotification = sbn.getNotification();
        if (mNotification == null || TextUtils.isEmpty(mNotification.tickerText)) {
            Log.i("chenxinx", "mNotification null");
            return;
        }
        String[] strArr = TickerFilter;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            if (!mNotification.tickerText.toString().contains(strArr[i])) {
                i++;
            } else {
                return;
            }
        }
        if (this.mBtDevice == null) {
            this.mBtDevice = BtManagerService.getConnectDevice(this.mConnectCallback);
        }
        try {
            boolean z2;
            String str = "chenxinx";
            StringBuilder append = new StringBuilder().append("get noti:").append(sbn.getPackageName()).append(" content:").append(mNotification.tickerText).append(" ").append(" ");
            if (this.infoList.size() > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            StringBuilder append2 = append.append(z2).append(" ");
            if (this.mBtDevice.getConnectState() != CONNECT_STATE.CONNECTED) {
                z = false;
            }
            Log.i(str, append2.append(z).toString());
        } catch (Exception e) {
            Log.i("chenxinx", "onNotificationPosted Exception");
        }
        if (this.infoList.size() > 0 && this.mBtDevice != null && this.mBtDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
            for (String info : this.infoList) {
                String[] str2 = info.split("\\|");
                Log.i("chenxinx", "str[0]:" + str2[0]);
                if (sbn.getPackageName().equals(str2[0])) {
                    Log.i("chenxinx", "notify appName:" + getApplicationName(sbn.getPackageName()));
                    this.mBtDevice.sendNotifaction(getApplicationName(sbn.getPackageName()), sbn.getPackageName(), mNotification);
                }
            }
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("chenxinx", "onStartCommand");
        this.infoList = SPUtils.getList("droi_share_push_list");
        return 1;
    }

    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    public String getApplicationName(String packageName) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return packageManager.getApplicationLabel(applicationInfo).toString();
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        Log.i("chenxinx", "isAccessibilitySettingsOn");
        if (!NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.getPackageName())) {
            return false;
        }
        Log.i("chenxinx", "isAccessibilitySettingsOn get on");
        return true;
    }

    public void onCreate() {
        super.onCreate();
        this.mBtDevice = BtManagerService.getConnectDevice(this.mConnectCallback);
        EventBus.getDefault().register(this);
        Log.i("chenxinx", "NotificationServiceNew oncreate");
        this.infoList = SPUtils.getList("droi_share_push_list");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("chenxinx", "NotificationServiceNew onDestroy");
        EventBus.getDefault().unregister(this);
        BtManagerService.removeConnectCallback(this.mConnectCallback);
    }

    @Subscribe
    public void onEventBackground(SharePrefrenceChange event) {
        this.infoList = SPUtils.getList("droi_share_push_list");
    }

    public static void toggleNotificationListenerService(Context context) {
        Log.i("chenxinx", "toggleNotificationListenerService");
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, NotificationServiceNew.class), 2, 1);
        pm.setComponentEnabledSetting(new ComponentName(context, NotificationServiceNew.class), 1, 1);
    }
}
