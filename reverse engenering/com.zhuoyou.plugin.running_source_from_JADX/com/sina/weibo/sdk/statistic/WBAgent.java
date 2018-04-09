package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import java.util.Map;

public class WBAgent {
    public static final String TAG = "WBAgent";

    public static void openActivityDurationTrack(boolean open) {
        StatisticConfig.ACTIVITY_DURATION_OPEN = open;
    }

    public static void setSessionContinueMillis(long interval) {
        StatisticConfig.kContinueSessionMillis = interval;
    }

    public static void setAppKey(String appkey) {
        StatisticConfig.setAppkey(appkey);
    }

    public static void setChannel(String channel) {
        StatisticConfig.setChannel(channel);
    }

    public static void setUploadInterval(long interval) throws Exception {
        StatisticConfig.setUploadInterval(interval);
    }

    public static void setForceUploadInterval(long interval) {
        StatisticConfig.setForceUploadInterval(interval);
    }

    public static void setNeedGzip(boolean needGizp) {
        StatisticConfig.setNeedGizp(needGizp);
    }

    public static void onPageStart(String pageName) {
        if (!TextUtils.isEmpty(pageName)) {
            WBAgentHandler.getInstance().onPageStart(pageName);
        }
    }

    public static void onPageEnd(String pageName) {
        if (!TextUtils.isEmpty(pageName)) {
            WBAgentHandler.getInstance().onPageEnd(pageName);
        }
    }

    public static void onResume(Context context) {
        if (context == null) {
            LogUtil.m3308e(TAG, "unexpected null context in onResume");
        } else {
            WBAgentHandler.getInstance().onResume(context);
        }
    }

    public static void onPause(Context context) {
        if (context == null) {
            LogUtil.m3308e(TAG, "unexpected null context in onResume");
        } else {
            WBAgentHandler.getInstance().onPause(context);
        }
    }

    public static void onEvent(Object obj, String eventId) {
        onEvent(obj, eventId, null);
    }

    public static void onEvent(Object obj, String eventId, Map<String, String> extend) {
        if (obj == null) {
            LogUtil.m3308e(TAG, "unexpected null page or activity in onEvent");
        } else if (eventId == null) {
            LogUtil.m3308e(TAG, "unexpected null eventId in onEvent");
        } else {
            if (obj instanceof Context) {
                obj = obj.getClass().getName();
            }
            WBAgentHandler.getInstance().onEvent((String) obj, eventId, extend);
        }
    }

    public static void uploadAppLogs(Context context) {
        if (context == null) {
            LogUtil.m3308e(TAG, "unexpected null context in uploadAppLogs");
        } else {
            WBAgentHandler.getInstance().uploadAppLogs(context);
        }
    }

    public static void onStop(Context context) {
        if (context == null) {
            LogUtil.m3308e(TAG, "unexpected null context in onStop");
        } else {
            WBAgentHandler.getInstance().onStop(context);
        }
    }

    public static void onKillProcess() {
        WBAgentHandler.getInstance().onKillProcess();
    }

    public static void setDebugMode(boolean isLogEnable, boolean isGzip) {
        LogUtil.sIsLogEnable = isLogEnable;
        StatisticConfig.setNeedGizp(isGzip);
    }
}
