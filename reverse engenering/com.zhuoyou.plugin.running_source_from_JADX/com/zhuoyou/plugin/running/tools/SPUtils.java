package com.zhuoyou.plugin.running.tools;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.mapapi.model.LatLng;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.sina.weibo.sdk.exception.WeiboAuthException;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.bean.AlarmData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SPUtils {
    public static final String M2_SWITCH_REMIND = "m2.switch.remind";
    private static final String SP_KEY_ALARM_STR = "sp_key_alarm_str";
    private static final String SP_KEY_FIRST_IN_APP = "sp_key_first_in_app";
    private static final String SP_KEY_IS_SYNC_DATA = "sp_key_is_synced_data";
    private static final String SP_KEY_LAST_LATLNG_LAT = "sp_key_last_latlng_lat";
    private static final String SP_KEY_LAST_LATLNG_LNG = "sp_key_last_latlng_lng";
    private static final String SP_KEY_ONCE_ALARM_TIME = "sp_key_once_alarm_time_";
    private static final String SP_KEY_PHONE_STEP_OPEN = "sp_key_phone_step_open";
    private static final String SP_KEY_QQ_ACCESS_TOKEN = "sp_key_qq_access_token";
    private static final String SP_KEY_QQ_EXPIRES_DATE = "sp_key_qq_expires_date";
    private static final String SP_KEY_QQ_EXPIRES_IN = "sp_key_qq_expires_in";
    private static final String SP_KEY_QQ_OPENID = "sp_key_qq_openid";
    private static final String SP_KEY_QQ_UPLOAD_TIME = "sp_key_qq_upload_time";
    private static final String SP_KEY_QQ_USER_NAME = "sp_key_qq_user_name";
    private static final String SP_KEY_SHOW_HEART_HINT = "sp_key_show_heart_hint";
    private static final String SP_KEY_SHOW_NOTIFY = "sp_key_show_notify";
    private static final String SP_KEY_SHOW_TIPS = "sp_key_show_tips";
    private static final String SP_KEY_TARGET_STEP = "sp_key_target_step";
    private static final String SP_KEY_UNIT_DIS = "sp_key_dis_unit";
    private static final String SP_KEY_UNIT_WEI = "sp_key_wei_unit";
    private static final String SP_KEY_VITAL_CAPACITY = "sp_key_vital_capacity";
    private static final String SP_KEY_VITAL_VALUE = "sp_key_vital_cap_value";
    private static final String SP_KEY_WATER_ALARM_OPEN = "sp_key_is_water_alarm_open";
    private static final String SP_KEY_WATER_NUMBER = "sp_key_water_drink_number";
    private static final String SP_KEY_WIFI_ONLY = "sp_key_wifi_only";
    protected static final String SP_NAME_APP = "sp_name_app";
    protected static final String SP_NAME_DEVICE = "sp_name_device";
    protected static final String SP_NAME_USER = "sp_name_user";
    private static SharedPreferences appShared;
    private static SharedPreferences deviceShared;
    private static SharedPreferences userShared;

    public static class SharePrefrenceChange {
    }

    public static SharedPreferences getUserShared() {
        if (userShared == null) {
            synchronized (SPUtils.class) {
                if (userShared == null) {
                    userShared = TheApp.getInstance().getSharedPreferences(SP_NAME_USER, 0);
                }
            }
        }
        return userShared;
    }

    public static SharedPreferences getAppShared() {
        if (appShared == null) {
            synchronized (SPUtils.class) {
                if (appShared == null) {
                    appShared = TheApp.getInstance().getSharedPreferences(SP_NAME_APP, 0);
                }
            }
        }
        return appShared;
    }

    public static SharedPreferences getDeviceShared() {
        if (deviceShared == null) {
            synchronized (SPUtils.class) {
                if (deviceShared == null) {
                    deviceShared = TheApp.getInstance().getSharedPreferences(SP_NAME_DEVICE, 0);
                }
            }
        }
        return deviceShared;
    }

    public static boolean clear(SharedPreferences sp) {
        Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }

    public static boolean clear(String name) {
        Editor editor = TheApp.getInstance().getSharedPreferences(name, 0).edit();
        editor.clear();
        return editor.commit();
    }

    public static int getTargetStep() {
        return getAppShared().getInt(SP_KEY_TARGET_STEP, 8000);
    }

    public static boolean setTargetStep(int value) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_TARGET_STEP, value);
        return editor.commit();
    }

    public static boolean isPhonePed() {
        return getAppShared().getBoolean(SP_KEY_PHONE_STEP_OPEN, false);
    }

    public static boolean setPhonePed(boolean open) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_PHONE_STEP_OPEN, open);
        return editor.commit();
    }

    public static boolean isShowNotify() {
        return getAppShared().getBoolean(SP_KEY_SHOW_NOTIFY, true);
    }

    public static boolean setShowNotify(boolean show) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_SHOW_NOTIFY, show);
        return editor.commit();
    }

    public static boolean isWifiOnly() {
        return getAppShared().getBoolean(SP_KEY_WIFI_ONLY, true);
    }

    public static boolean setWifiOnly(boolean only) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_WIFI_ONLY, only);
        return editor.commit();
    }

    public static int getUnitDis() {
        return getAppShared().getInt(SP_KEY_UNIT_DIS, 1);
    }

    public static boolean setUnitDis(int value) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_UNIT_DIS, value);
        return editor.commit();
    }

    public static int getUnitWei() {
        return getAppShared().getInt(SP_KEY_UNIT_WEI, 1);
    }

    public static boolean setUnitWei(int value) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_UNIT_WEI, value);
        return editor.commit();
    }

    public static String getFileUri(String fileid) {
        return getAppShared().getString(fileid, null);
    }

    public static boolean setFileUri(String fileid, Uri uri) {
        Editor editor = getAppShared().edit();
        editor.putString(fileid, uri.toString());
        return editor.commit();
    }

    public static boolean saveAlarm(AlarmData data) {
        data.setOpenDate(AlarmUtils.dateFormat.format(Calendar.getInstance().getTime()));
        Editor editor = getDeviceShared().edit();
        editor.putString(SP_KEY_ALARM_STR + data.getId(), data.toString());
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setAlarm(data.toBTcmd());
            Log.i("zhuqichao", "alarm data=" + data.toBTcmd());
        }
        return editor.commit();
    }

    public static AlarmData getAlarm(int id) {
        AlarmData data = getAlarmNoCheck(id);
        if (data.getOpenType() == AlarmData.OPEN_TYPE_ONCE && getOnceAlarmTime(id) < System.currentTimeMillis()) {
            data.setOpen(false);
            saveAlarm(data);
        }
        return data;
    }

    public static AlarmData getAlarmNoCheck(int id) {
        String alarmStr = getDeviceShared().getString(SP_KEY_ALARM_STR + id, "");
        if (TextUtils.isEmpty(alarmStr)) {
            return new AlarmData(id, false, AlarmData.OPEN_TYPE_ONCE, 0);
        }
        return AlarmUtils.dealAlarmString(alarmStr);
    }

    public static boolean saveOnceAlarmTime(int id, long time) {
        Editor editor = getDeviceShared().edit();
        editor.putLong(SP_KEY_ONCE_ALARM_TIME + id, time);
        return editor.commit();
    }

    public static long getOnceAlarmTime(int id) {
        return getDeviceShared().getLong(SP_KEY_ONCE_ALARM_TIME + id, System.currentTimeMillis());
    }

    public static boolean setShowHeartHint(boolean show) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_SHOW_HEART_HINT, show);
        return editor.commit();
    }

    public static boolean isShowHeartHint() {
        return getAppShared().getBoolean(SP_KEY_SHOW_HEART_HINT, true);
    }

    public static boolean setFirstInApp(boolean first) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_FIRST_IN_APP, first);
        return editor.commit();
    }

    public static boolean setShowedTips(boolean show) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_SHOW_TIPS, show);
        return editor.commit();
    }

    public static boolean isShowedTips() {
        return getAppShared().getBoolean(SP_KEY_SHOW_TIPS, false);
    }

    public static boolean isFirstInApp() {
        return getAppShared().getBoolean(SP_KEY_FIRST_IN_APP, true);
    }

    public static boolean setSyncedData(boolean synced) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_IS_SYNC_DATA, synced);
        return editor.commit();
    }

    public static boolean isSyncedData() {
        return getAppShared().getBoolean(SP_KEY_IS_SYNC_DATA, false);
    }

    public static int getWaterNumber() {
        return getAppShared().getInt(SP_KEY_WATER_NUMBER, 0);
    }

    public static boolean setWaterNumber(int num) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_WATER_NUMBER, num);
        return editor.commit();
    }

    public static boolean setWaterAlarmOpen(boolean open) {
        Editor editor = getAppShared().edit();
        editor.putBoolean(SP_KEY_WATER_ALARM_OPEN, open);
        return editor.commit();
    }

    public static boolean isWaterAlarmOpen() {
        return getAppShared().getBoolean(SP_KEY_WATER_ALARM_OPEN, false);
    }

    public static int getVitalValue() {
        return getAppShared().getInt(SP_KEY_VITAL_VALUE, 0);
    }

    public static boolean setVitalValue(int value) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_VITAL_VALUE, value);
        return editor.commit();
    }

    public static int getVitalCapacity() {
        return getAppShared().getInt(SP_KEY_VITAL_CAPACITY, 0);
    }

    public static void setVitalCapacity(int value) {
        Editor editor = getAppShared().edit();
        editor.putInt(SP_KEY_VITAL_CAPACITY, value);
        editor.apply();
    }

    public static LatLng getLastLatLng() {
        return new LatLng(Double.parseDouble(getAppShared().getString(SP_KEY_LAST_LATLNG_LAT, WeiboAuthException.DEFAULT_AUTH_ERROR_CODE)), Double.parseDouble(getAppShared().getString(SP_KEY_LAST_LATLNG_LNG, WeiboAuthException.DEFAULT_AUTH_ERROR_CODE)));
    }

    public static boolean setLastLatLng(LatLng latLng) {
        Editor editor = getAppShared().edit();
        editor.putString(SP_KEY_LAST_LATLNG_LAT, String.valueOf(latLng.latitude));
        editor.putString(SP_KEY_LAST_LATLNG_LNG, String.valueOf(latLng.longitude));
        return editor.commit();
    }

    public static String getQQOpenId(String userid) {
        return getUserShared().getString(SP_KEY_QQ_OPENID + userid, "");
    }

    public static boolean setQQOpenId(String openid, String userid) {
        Editor editor = getUserShared().edit();
        editor.putString(SP_KEY_QQ_OPENID + userid, openid);
        return editor.commit();
    }

    public static String getQQAccessToken(String userid) {
        return getUserShared().getString(SP_KEY_QQ_ACCESS_TOKEN + userid, "");
    }

    public static boolean setQQAccessToken(String token, String userid) {
        Editor editor = getUserShared().edit();
        editor.putString(SP_KEY_QQ_ACCESS_TOKEN + userid, token);
        return editor.commit();
    }

    public static long getQQExpiresIn(String userid) {
        return getUserShared().getLong(SP_KEY_QQ_EXPIRES_IN + userid, 0) - System.currentTimeMillis();
    }

    public static boolean setQQExpiresIn(String expiresin, String userid) {
        return setQQExpiresIn(Long.parseLong(expiresin), userid);
    }

    public static String getQQuserName(String userid) {
        return getUserShared().getString(SP_KEY_QQ_USER_NAME + userid, "");
    }

    public static boolean setQQUserName(String name, String userid) {
        Editor editor = getUserShared().edit();
        editor.putString(SP_KEY_QQ_USER_NAME + userid, name);
        return editor.commit();
    }

    public static boolean setQQExpiresIn(long expiresin, String userid) {
        Editor editor = getUserShared().edit();
        editor.putLong(SP_KEY_QQ_EXPIRES_IN + userid, System.currentTimeMillis() + (1000 * expiresin));
        return editor.commit();
    }

    public static boolean setQQExpiresDate(long expiresTime, String userid) {
        Editor editor = getUserShared().edit();
        editor.putLong(SP_KEY_QQ_EXPIRES_DATE + userid, expiresTime);
        return editor.commit();
    }

    public static long getQQExpiresDate(String userid) {
        return getUserShared().getLong(SP_KEY_QQ_EXPIRES_DATE + userid, 0);
    }

    public static long getUploadQQTime(String userid) {
        return getUserShared().getLong(SP_KEY_QQ_UPLOAD_TIME + userid, 0);
    }

    public static boolean setUploadQQTime(long time, String userid) {
        Editor editor = getUserShared().edit();
        editor.putLong(SP_KEY_QQ_UPLOAD_TIME + userid, time);
        return editor.commit();
    }

    public static boolean clearQQInfo(String userId) {
        Editor editor = getUserShared().edit();
        editor.remove(SP_KEY_QQ_OPENID + userId);
        editor.remove(SP_KEY_QQ_ACCESS_TOKEN + userId);
        editor.remove(SP_KEY_QQ_EXPIRES_IN + userId);
        editor.remove(SP_KEY_QQ_UPLOAD_TIME + userId);
        editor.remove(SP_KEY_QQ_EXPIRES_DATE + userId);
        editor.remove(SP_KEY_QQ_USER_NAME + userId);
        return editor.commit();
    }

    public static boolean saveList(String listName, List<String> list) {
        boolean result;
        Editor editor;
        if (list == null || list.size() == 0) {
            editor = getDeviceShared().edit();
            editor.putInt(listName, 0);
            result = editor.commit();
        } else {
            editor = getDeviceShared().edit();
            editor.putInt(listName, list.size());
            Log.i("yuanzz", "put list zise:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                editor.putString(listName + i, (String) list.get(i));
                Log.i("yuanzz", "put list name:" + ((String) list.get(i)));
            }
            result = editor.commit();
        }
        EventBus.getDefault().post(new SharePrefrenceChange());
        return result;
    }

    public static List<String> getList(String ListName) {
        ArrayList<String> list = new ArrayList();
        SharedPreferences share = getDeviceShared();
        int size = share.getInt(ListName, 0);
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                Log.i("yuanzz", "get list name:" + share.getString(ListName + i, ""));
                list.add(share.getString(ListName + i, ""));
            }
        }
        return list;
    }

    public static boolean getSportTargetReminderSwitch() {
        return getAppShared().getBoolean("sport_target_remind", false);
    }

    public static void setSportTargetReminderSwitch(boolean isRemind) {
        Editor et = getAppShared().edit();
        et.putBoolean("sport_target_remind", isRemind);
        et.commit();
    }

    public static boolean getDisturbanceModleSwitch() {
        return getAppShared().getBoolean("disturbance_modle", false);
    }

    public static void setDisturbanceModleSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("disturbance_modle", isOpen);
        et.commit();
    }

    public static boolean getSedentaryRemindSwitch() {
        return getAppShared().getBoolean("sedentary_remind_m2", false);
    }

    public static void setSedentaryRemindSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("sedentary_remind_m2", isOpen);
        et.commit();
    }

    public static boolean getLunchNotBreakSwitch() {
        return getAppShared().getBoolean("lunch_not_break", false);
    }

    public static void setLunchNotBreakSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("lunch_not_break", isOpen);
        et.commit();
    }

    public static String getDisturbanceModleTime() {
        return getAppShared().getString("disturbance_mode_time", "");
    }

    public static void setDisturbanceModleTime(String time) {
        Editor et = getAppShared().edit();
        et.putString("disturbance_mode_time", time);
        et.commit();
    }

    public static String getSedentaryRemindTime() {
        return getAppShared().getString("sedentary_remind_time", "");
    }

    public static void setSedentaryRemindTime(String time) {
        Editor et = getAppShared().edit();
        et.putString("sedentary_remind_time", time);
        et.commit();
    }

    public static String getHeightWeightData() {
        return getAppShared().getString("height_weight_data", "3|175|0750|||||");
    }

    public static void setHeightWeightData(String time) {
        Editor et = getAppShared().edit();
        et.putString("height_weight_data", time);
        et.commit();
    }

    public static String getM2UIDisplay() {
        return getAppShared().getString("m2_ui_display", "");
    }

    public static void setM2UIDisplay(String open) {
        Editor et = getAppShared().edit();
        et.putString("m2_ui_display", open);
        et.commit();
    }

    public static boolean getM2MileageSwitch() {
        return getAppShared().getBoolean("m2_mileage_switch", true);
    }

    public static void setM2MileageSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("m2_mileage_switch", isOpen);
        et.commit();
    }

    public static boolean getM2CaloriesSwitch() {
        return getAppShared().getBoolean("m2_calories_switch", true);
    }

    public static void setM2CaloriesSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("m2_calories_switch", isOpen);
        et.commit();
    }

    public static boolean getM2ElectricitySwitch() {
        return getAppShared().getBoolean("m2_electricity_switch", true);
    }

    public static void setM2ElectricitySwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("m2_electricity_switch", isOpen);
        et.commit();
    }

    public static boolean getM2WristBrightScreenSwitch() {
        return getAppShared().getBoolean("m2_wrist_birght_screen", true);
    }

    public static void setM2WristBrightScreenSwitch(boolean isOpen) {
        Editor et = getAppShared().edit();
        et.putBoolean("m2_wrist_birght_screen", isOpen);
        et.commit();
    }
}
