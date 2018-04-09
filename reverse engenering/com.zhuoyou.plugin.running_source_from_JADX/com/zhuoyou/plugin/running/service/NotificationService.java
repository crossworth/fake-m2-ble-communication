package com.zhuoyou.plugin.running.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils.SimpleStringSplitter;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.SPUtils.SharePrefrenceChange;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NotificationService extends AccessibilityService {
    private static final String TAG = "NotificationService";
    private List<String> infoList;
    private BtDevice mBtDevice = null;
    private ConnectCallback mConnectCallback = new C19151();

    class C19151 implements ConnectCallback {
        C19151() {
        }

        public void success(BtDevice device) {
            Log.i("chenxinx", "mConnectCallback success");
            NotificationService.this.mBtDevice = device;
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

    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == 64) {
            Notification mNotification = (Notification) event.getParcelableData();
            if (mNotification != null) {
                Log.i("chenxinx", "get noti:" + event.getPackageName() + " content:" + mNotification.tickerText);
                if (this.infoList != null && this.infoList.size() != 0 && this.mBtDevice != null && this.mBtDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
                    for (String info : this.infoList) {
                        if (event.getPackageName().equals(info.split("\\|")[0])) {
                            Log.i("chenxinx", "notify appName:" + getApplicationName(event.getPackageName().toString()));
                            this.mBtDevice.sendNotifaction(getApplicationName(event.getPackageName().toString()), event.getPackageName().toString(), mNotification);
                        }
                    }
                }
            }
        }
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
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public void onInterrupt() {
    }

    public void onCreate() {
        super.onCreate();
        this.mBtDevice = BtManagerService.getConnectDevice(this.mConnectCallback);
        EventBus.getDefault().register(this);
        Log.i("chenxinx", "oncreate");
        this.infoList = SPUtils.getList("droi_share_push_list");
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart");
    }

    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        String service = mContext.getPackageName() + "/" + NotificationService.class.getCanonicalName();
        try {
            accessibilityEnabled = Secure.getInt(mContext.getApplicationContext().getContentResolver(), "accessibility_enabled");
        } catch (SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        SimpleStringSplitter mStringColonSplitter = new SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Secure.getString(mContext.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    if (mStringColonSplitter.next().equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }
        Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        BtManagerService.removeConnectCallback(this.mConnectCallback);
    }

    @Subscribe
    public void onEventBackground(SharePrefrenceChange event) {
        Log.i(TAG, "onEventBackground");
        this.infoList = SPUtils.getList("droi_share_push_list");
    }
}
