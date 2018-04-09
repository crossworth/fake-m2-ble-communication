package com.zhuoyou.plugin.bluetooth.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.zhuoyou.plugin.running.RunningApp;

public class PreferenceData {
    public static final String PREFERENCE_KEY_ACCESSIBILITY = "show_accessibility_menu_preference";
    public static final String PREFERENCE_KEY_ALWAYS_FORWARD = "always_forward_preference";
    public static final String PREFERENCE_KEY_APP_INFO = "app_info";
    public static final String PREFERENCE_KEY_CALL = "enable_call_service_preference";
    public static final String PREFERENCE_KEY_CURRENT_VERSION = "current_version_preference";
    public static final String PREFERENCE_KEY_NOTIFI = "enable_notifi_service_preference";
    public static final String PREFERENCE_KEY_SELECT_BLOCKS = "select_blocks_preference";
    public static final String PREFERENCE_KEY_SELECT_NOTIFICATIONS = "select_notifi_preference";
    public static final String PREFERENCE_KEY_SHOW_CONNECTION_STATUS = "show_connection_status_preference";
    public static final String PREFERENCE_KEY_SMS = "enable_sms_service_preference";
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private static final SharedPreferences sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);

    public static boolean isSmsServiceEnable() {
        return sSharedPreferences.getBoolean(PREFERENCE_KEY_SMS, true);
    }

    public static boolean isNotificationServiceEnable() {
        return sSharedPreferences.getBoolean(PREFERENCE_KEY_NOTIFI, true);
    }

    public static boolean isCallServiceEnable() {
        return sSharedPreferences.getBoolean(PREFERENCE_KEY_CALL, true);
    }

    public static boolean isShowConnectionStatus() {
        return sSharedPreferences.getBoolean(PREFERENCE_KEY_SHOW_CONNECTION_STATUS, true);
    }

    private static boolean isAlwaysForward() {
        return sSharedPreferences.getBoolean(PREFERENCE_KEY_ALWAYS_FORWARD, true);
    }

    public static boolean isNeedPush() {
        return isAlwaysForward() || Util.isScreenLocked(sContext);
    }
}
