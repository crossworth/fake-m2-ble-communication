package com.zhuoyi.system.statistics.listener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.CommConstants;
import java.util.Map;

public class ZyStatisticsSDK {
    public static final String TAG = "ZyStatisticsSDK";

    public static void init(Context context) {
        init(context, null, null);
    }

    public static void init(Context context, String channelId, String appKey) {
        if (TextUtils.isEmpty(channelId)) {
            channelId = TerminalInfoUtil.getChannelId(context);
        } else {
            PromDBUtils.getInstance(context).insertCfg("zy_channel_id", channelId);
        }
        if (TextUtils.isEmpty(appKey)) {
            appKey = TerminalInfoUtil.getAppKey(context);
        } else {
            PromDBUtils.getInstance(context).insertCfg(CommConstants.LOTUSSED_ZY_METADATA_KEY, appKey);
        }
        if (TextUtils.isEmpty(channelId) || TextUtils.isEmpty(appKey)) {
            Log.e(TAG, "Statistics init fail");
        }
    }

    public static void onCreate(Context context) {
    }

    public static void onDestroy(Context context) {
    }

    public static void onResume(Context context) {
    }

    public static void onPause(Context context) {
    }

    public static void onEvent(String eventID) {
    }

    public static void onEvent(String eventID, long count) {
    }

    public static void onEvent(String eventID, String label) {
    }

    public static void onEvent(String eventID, String label, long count) {
    }

    public static void onEvent(String eventID, Map<String, String> map) {
    }

    public static void onEvent(String eventID, Map<String, String> map, long count) {
    }

    public static void onEvent(String eventID, boolean immediately) {
    }

    public static void onEvent(String eventID, long count, boolean immediately) {
    }

    public static void onEvent(String eventID, String label, boolean immediately) {
    }

    public static void onEvent(String eventID, String label, long count, boolean immediately) {
    }

    public static void onEvent(String eventID, Map<String, String> map, boolean immediately) {
    }

    public static void onEvent(String eventID, Map<String, String> map, long count, boolean immediately) {
    }

    public static void onEventDuration(String eventID, long duration) {
    }

    public static void onEventDuration(String eventID, String label, long duration) {
    }

    public static void onEventDuration(String eventID, Map<String, String> map, long duration) {
    }

    public static void onEventDuration(String eventID, long duration, boolean immediately) {
    }

    public static void onEventDuration(String eventID, String label, long duration, boolean immediately) {
    }

    public static void onEventDuration(String eventID, Map<String, String> map, long duration, boolean immediately) {
    }
}
