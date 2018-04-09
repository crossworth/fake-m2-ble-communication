package com.zhuoyi.system.promotion.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.ZyService;
import com.zhuoyi.system.util.Logger;
import java.util.Calendar;
import java.util.Random;

public class TimerManager {
    private static final String TAG = "PromTimerManager";
    private static final int TIMER_COUNT = 6;
    private static final int TIMER_INTERVAL = 2;
    public static Context mContext;
    public static TimerManager mInstance;
    private AlarmManager alarmManager;
    private int curTimerIndex = 0;
    private Random f3487r = new Random();

    private TimerManager(Context c) {
        mContext = c;
        this.alarmManager = (AlarmManager) mContext.getSystemService("alarm");
    }

    public static TimerManager getInstance(Context c) {
        if (mInstance == null) {
            mInstance = new TimerManager(c);
        }
        return mInstance;
    }

    public void startAlermByServiceId(int serviceId) {
        startAlermByServiceId(serviceId, 0, false);
    }

    public void startAlermByServiceId(int serviceId, long interval) {
        startAlermByServiceId(serviceId, interval, false);
    }

    public void startAlermByServiceId(int serviceId, long interval, boolean immediately) {
        int i = 0;
        Logger.debug(TAG, "startAlermByServiceId and serviceid=" + serviceId);
        try {
            int startMunites = ZySDKConfig.getInstance().isDebugMode() ? 0 : getCurTimeStartTime();
            long millis = System.currentTimeMillis();
            if (!immediately) {
                int i2 = (startMunites * 60) * 1000;
                if (!ZySDKConfig.getInstance().isDebugMode()) {
                    i = 1;
                }
                millis += (long) ((i * this.f3487r.nextInt(30000)) + i2);
            }
            Logger.debug(TAG, "Alerm will start at " + TimeFormater.formatTime(millis));
            if (interval != 0) {
                this.alarmManager.setRepeating(0, millis, interval, getPendingIntent(serviceId, null));
            } else {
                this.alarmManager.set(0, millis, getPendingIntent(serviceId, null));
            }
        } catch (Exception e) {
            Logger.debug(TAG, "Alerm  start error.");
        }
    }

    public void stopAlermByServiceId(int serviceId) {
        this.alarmManager.cancel(getPendingIntent(serviceId, null));
    }

    public void startTimerByTime(long millis, int serviceId) {
        startTimerByTime(millis, serviceId, null);
    }

    public void startTimerByTime(long millis, int serviceId, Bundle b) {
        if (millis <= 0) {
            millis = (System.currentTimeMillis() + ((long) (((ZySDKConfig.getInstance().isDebugMode() ? 0 : 10) * 60) * 1000))) + ((long) this.f3487r.nextInt(60000));
        }
        Logger.debug(TAG, "startTimerByTime at " + TimeFormater.formatTime(millis) + " and serviceid=" + serviceId);
        this.alarmManager.set(0, millis, getPendingIntent(serviceId, b));
    }

    private PendingIntent getPendingIntent(int serviceId, Bundle b) {
        Intent pandingIntent = new Intent(mContext, ZyService.class);
        pandingIntent.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, serviceId);
        int id = serviceId;
        if (b != null) {
            id = b.getInt(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, serviceId);
            pandingIntent.putExtras(b);
        }
        return PendingIntent.getService(mContext, id, pandingIntent, 134217728);
    }

    private int getCurTimeStartTime() {
        if (this.curTimerIndex > 5) {
            this.curTimerIndex = 0;
        }
        int time = this.curTimerIndex * 2;
        this.curTimerIndex++;
        return time;
    }

    public void clearPushNotifyTimer(int... id) {
        if (id != null && id.length > 0) {
            for (int i : id) {
                this.alarmManager.cancel(getPendingIntent(i, null));
            }
        }
        for (PromAppInfo adInfo : PromDBUtils.getInstance(mContext).queryAllPushNotify()) {
            this.alarmManager.cancel(getPendingIntent(adInfo.getId(), null));
        }
    }

    public void startTimerByTime(String timeString, int serviceId) {
        startTimerByTime(timeString, serviceId, null);
    }

    public void startTimerByTime(String timeString, int serviceId, Bundle b) {
        String[] timeStrings = timeString.split(":");
        if (timeStrings.length > 1) {
            Calendar c = Calendar.getInstance();
            if (!TextUtils.isEmpty(timeStrings[0])) {
                c.set(11, Integer.valueOf(timeStrings[0]).intValue());
            }
            c.set(12, (Integer.valueOf(timeStrings[1]).intValue() + this.f3487r.nextInt(60)) % 60);
            startTimerByTime(c.getTimeInMillis(), serviceId, b);
        }
    }
}
