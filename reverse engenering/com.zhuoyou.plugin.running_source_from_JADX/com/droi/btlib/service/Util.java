package com.droi.btlib.service;

import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfile.ServiceListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.internal.telephony.ITelephony;
import com.droi.btlib.connection.MessageHeader;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.connection.NoDataException;
import com.droi.btlib.connection.NotificationMessageBody;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import org.xmlpull.v1.XmlPullParserException;

public class Util {
    public static final String ALARM_PUT_CALL_REMIND = "droi_call_remind";
    public static final String ALARM_PUT_DISCONNECT_REMIND = "droi_disconnect_remind";
    public static final String ALARM_PUT_SMS_REMIND = "droi_sms_remind";
    public static final String ANTILOST = "droi_anti_lost";
    public static final int APP_ICON_HEIGHT = 40;
    public static final int APP_ICON_WIDTH = 40;
    public static final String BINDING_DEVICE = "droi_binding_device";
    private static final int NOTIFICATION_CONTENT_TYPE = 10;
    private static final int NOTIFICATION_TITLE_TYPE = 9;
    public static final int NOTIFYMINIHEADERLENTH = 8;
    public static final int NOTIFYSYNCLENTH = 4;
    public static final String NULL_TEXT_NAME = "(unknown)";
    private static final String SAVE_BATTERY = "share_droi_save_battery";
    private static final String SAVE_CONNECT_STATE = "share_droi_save_connect_state";
    private static final String SAVE_DEVICE_TYPE = "share_droi_save_device_type";
    private static final String SAVE_HARDWARE = "share_droi_save_device_hardware";
    private static final String SAVE_MAC_ADDRESS = "share_droi_save_mac_address";
    private static final String SAVE_NAME = "share_droi_save_device_name";
    private static final String SAVE_VERSION = "share_droi_save_device_version";
    private static final String SHARE_DROI_BT = "share_droi_bt";
    private static final String TAG = "chenxin";
    public static final int TEXT_MAX_LENGH = 256;
    public static final String TEXT_POSTFIX = "...";
    public static final int TICKER_TEXT_MAX_LENGH = 128;
    public static final int TITLE_TEXT_MAX_LENGH = 128;
    private static GetConnectClassicCallback classicCallback = null;
    private static BluetoothAdapter mBluetoothAdapter = null;
    private static BluetoothHeadset mBluetoothHeadset;
    private static Context mContext;
    private static DeviceInfo mDevice;
    private static Editor mEditor;
    private static Handler mHandler = new Handler();
    private static ServiceListener mProfileListener = new C07372();
    private static SharedPreferences mShare;
    private static Runnable rGetDevice = new C07361();
    private static int sMessageId;

    public interface GetConnectClassicCallback {
        void fail();

        void success(BluetoothDevice bluetoothDevice);
    }

    static class C07361 implements Runnable {
        C07361() {
        }

        public void run() {
            if (Util.classicCallback != null) {
                Util.classicCallback.fail();
            }
            if (Util.mBluetoothAdapter != null) {
                Util.mBluetoothAdapter.closeProfileProxy(1, Util.mBluetoothHeadset);
            }
        }
    }

    static class C07372 implements ServiceListener {
        C07372() {
        }

        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Log.i(Util.TAG, "onServiceConnected");
            if (profile == 1) {
                Log.i(Util.TAG, "onServiceConnected get");
                Util.mHandler.removeCallbacks(Util.rGetDevice);
                Util.mBluetoothHeadset = (BluetoothHeadset) proxy;
                if (!(Util.mBluetoothHeadset == null || Util.classicCallback == null || Util.mDevice == null)) {
                    for (BluetoothDevice device : Util.mBluetoothHeadset.getConnectedDevices()) {
                        if (Util.mDevice.getMacAddress().equals(device.getAddress())) {
                            Util.classicCallback.success(device);
                            return;
                        }
                    }
                }
                Util.classicCallback.fail();
                Util.mBluetoothAdapter.closeProfileProxy(1, Util.mBluetoothHeadset);
            }
        }

        public void onServiceDisconnected(int profile) {
            Log.i(Util.TAG, "onServiceDisconnected");
            if (profile == 1) {
                Util.mBluetoothHeadset = null;
            }
        }
    }

    public static Context getContext() {
        return mContext;
    }

    protected static void init(Context context) {
        mContext = context;
        mShare = context.getSharedPreferences(SHARE_DROI_BT, 0);
        mEditor = mShare.edit();
    }

    protected static void saveType(int type) {
        saveInt(SAVE_DEVICE_TYPE, type);
    }

    protected static int getType() {
        return getInt(SAVE_DEVICE_TYPE, 2);
    }

    protected static void saveBattery(int value) {
        saveInt(SAVE_BATTERY, value);
    }

    protected static int getBattery() {
        return getInt(SAVE_BATTERY, -1);
    }

    public static void saveName(String value) {
        saveString(SAVE_NAME, value);
    }

    public static String getName() {
        return getString(SAVE_NAME, "");
    }

    protected static void saveVersion(String value) {
        saveString(SAVE_VERSION, value);
    }

    public static String getVersion() {
        return getString(SAVE_VERSION, null);
    }

    protected static void saveHardWare(String value) {
        saveString(SAVE_HARDWARE, value);
    }

    protected static String getHardWare() {
        return getString(SAVE_HARDWARE, null);
    }

    protected static void saveMacAddress(String value) {
        saveString(SAVE_MAC_ADDRESS, value);
    }

    protected static String getMacAddress() {
        return getString(SAVE_MAC_ADDRESS, null);
    }

    protected static void saveAntiLost(int value) {
        saveInt(ANTILOST, value);
    }

    protected static int getAntiLost() {
        return getInt(ANTILOST, 0);
    }

    protected static void saveInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    protected static int getInt(String key, int defalut) {
        return mShare.getInt(key, defalut);
    }

    protected static void saveString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    private static String getString(String key, String defalut) {
        return mShare.getString(key, defalut);
    }

    protected static String transformLongTime2StringFormat(long timestamp) {
        String time = "";
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date((timestamp - ((long) ((getUtcTimeZone(System.currentTimeMillis()) - 28800000) / 1000))) * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static int getUtcTime(long localTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(localTime);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getUtcTimeZone(long localTime) {
        TimeZone tz = TimeZone.getDefault();
        int tzs = tz.getRawOffset();
        if (tz.inDaylightTime(new Date(localTime))) {
            return tzs + tz.getDSTSavings();
        }
        return tzs;
    }

    protected static byte[] long2Byte(long src) {
        return new byte[]{(byte) ((int) (src >> 24)), (byte) ((int) (src >> 16)), (byte) ((int) (src >> 8)), (byte) ((int) (src >> 0))};
    }

    protected static SleepInfo dealSleepBytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int i;
        boolean isEmptyMsg = false;
        if (bytes.length == 40 && bytes[0] == (byte) -1 && bytes[39] == (byte) -1) {
            isEmptyMsg = true;
            for (i = 0; i < 38; i++) {
                for (byte check : bytes) {
                    if (check != (byte) 0) {
                        isEmptyMsg = false;
                        break;
                    }
                }
            }
        }
        if (isEmptyMsg) {
            return null;
        }
        int curr_index = bytes[0] & 255;
        int totle_number = bytes[1] & 255;
        int type = bytes[2] & 255;
        long endTime = ((long) (((((bytes[7] & 255) << 24) | ((bytes[8] & 255) << 16)) | ((bytes[9] & 255) << 8)) | (bytes[10] & 255))) + 1262275200;
        long startTime = transformUTCTime2LongFormat(((long) (((((bytes[3] & 255) << 24) | ((bytes[4] & 255) << 16)) | ((bytes[5] & 255) << 8)) | (bytes[6] & 255))) + 1262275200);
        endTime = transformUTCTime2LongFormat(endTime);
        StringBuilder sb = new StringBuilder("");
        for (i = 0; i < 29; i++) {
            sb.append(bytes[i + 11] & 255);
            sb.append("|");
        }
        SleepInfo info = new SleepInfo();
        info.setType(type);
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        info.setSleepDetail(sb.toString());
        return info;
    }

    protected static long transformUTCTime2LongFormat(long timestamp) {
        String sSime = "";
        long time = 0;
        try {
            time = Long.valueOf(new SimpleDateFormat(Tools.DEFAULT_FORMAT_TIME).format(new Date((timestamp - ((long) ((getUtcTimeZone(System.currentTimeMillis()) - 28800000) / 1000))) * 1000))).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String parseType2String(int type) {
        switch (type) {
            case 1:
                return "CLASSIC";
            case 2:
                return "BLE";
            case 3:
                return "BLE and CLASSIC";
            default:
                return "UNKNOW";
        }
    }

    protected static void getBTstart(Context context) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothHeadset = null;
        Log.i(TAG, "getBTProxy in");
        if (mBluetoothAdapter.getProfileConnectionState(1) == 2 || mBluetoothAdapter.getProfileConnectionState(1) == 1) {
            Log.i(TAG, "getBTProxy in get profile");
            mBluetoothAdapter.getProfileProxy(context, mProfileListener, 1);
        }
    }

    protected static void getBTProxy(DeviceInfo info, GetConnectClassicCallback callback) {
        classicCallback = callback;
        mDevice = info;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.i(TAG, "getBTProxy in");
        if (mBluetoothAdapter.getProfileConnectionState(1) == 2 || mBluetoothAdapter.getProfileConnectionState(1) == 1) {
            Log.i(TAG, "getBTProxy in get profile");
            mBluetoothAdapter.getProfileProxy(mContext, mProfileListener, 1);
            mHandler.postDelayed(rGetDevice, 1000);
        } else if (callback != null) {
            callback.fail();
        }
    }

    protected static void emptySave() {
        mEditor.clear();
        mEditor.commit();
    }

    protected static void unpairDevice(BluetoothDevice device) {
        try {
            device.getClass().getMethod("removeBond", (Class[]) null).invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int genMessageId() {
        Log.i(TAG, "genMessageId(), messageId=" + sMessageId);
        int i = sMessageId;
        sMessageId = i + 1;
        return i;
    }

    public static Date getAlarmTime(Context context) {
        Date date = null;
        String nextAlarm = System.getString(context.getContentResolver(), "next_alarm_formatted");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE HH:mm", Locale.getDefault());
        try {
            if (!nextAlarm.isEmpty()) {
                date = sdf.parse(nextAlarm);
            }
        } catch (Exception e) {
            try {
                sdf.applyLocalizedPattern("EE HH:mm");
                date = sdf.parse(nextAlarm);
            } catch (Exception e2) {
            }
        }
        return date;
    }

    public static void resetBtService(BluetoothAdapter adapter) {
        try {
            adapter.getClass().getMethod("factoryReset", (Class[]) null).invoke(adapter, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatRemoteDate(String old_date) {
        return (((old_date.substring(0, 4) + "-") + old_date.substring(4, 6)) + "-") + old_date.substring(6, 8);
    }

    public static String dealSleepDateAdd(String str, int add) {
        SimpleDateFormat sdf = new SimpleDateFormat(Tools.DEFAULT_FORMAT_DATE);
        try {
            Date date = sdf.parse(str);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(6, add);
            str = sdf.format(rightNow.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    protected static void setCallRemind(boolean open) {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        mEditor = mShare.edit();
        mEditor.putBoolean(ALARM_PUT_CALL_REMIND, open);
        mEditor.commit();
    }

    public static boolean getCallRemind() {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        return mShare.getBoolean(ALARM_PUT_CALL_REMIND, true);
    }

    protected static void setSMSRemind(boolean open) {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        mEditor = mShare.edit();
        mEditor.putBoolean(ALARM_PUT_SMS_REMIND, open);
        mEditor.commit();
    }

    protected static boolean getSMSRemind() {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        return mShare.getBoolean(ALARM_PUT_SMS_REMIND, true);
    }

    protected static void setDisconnectRemind(boolean open) {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        mEditor = mShare.edit();
        mEditor.putBoolean(ALARM_PUT_DISCONNECT_REMIND, open);
        mEditor.commit();
    }

    protected static boolean getDisconnectRemind() {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        return mShare.getBoolean(ALARM_PUT_DISCONNECT_REMIND, false);
    }

    protected static void setIsBindingDevice(boolean isBinding) {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        mEditor = mShare.edit();
        mEditor.putBoolean(BINDING_DEVICE, isBinding);
        mEditor.commit();
    }

    protected static boolean getIsBindingDevice() {
        mShare = mContext.getSharedPreferences(SHARE_DROI_BT, 0);
        return mShare.getBoolean(BINDING_DEVICE, true);
    }

    public static String getContactName(Context context, String phoneNum) {
        if (phoneNum == null) {
            return null;
        }
        if (phoneNum.equals("")) {
            return null;
        }
        String contactName = phoneNum;
        try {
            Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(contactName));
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"display_name"}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
            Log.i(TAG, "getContactName(), contactName=" + contactName);
            return contactName;
        } catch (Exception e) {
            Log.i(TAG, "getContactName Exception");
            return null;
        }
    }

    public static char[] boolsToChar(boolean[] bools) {
        int i;
        ArrayList<Integer> temp = boolsToIntArray(bools);
        String ints = new String();
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            ints = ints + ((Integer) it.next());
        }
        Log.i("yuanzi", "ints:" + ints);
        int size = temp.size() / 16;
        char[] chars = new char[(temp.size() % 16 == 0 ? size : size + 1)];
        for (int j = 0; j < size; j++) {
            int charNum = 0;
            for (i = 0; i < 16; i++) {
                charNum += ((Integer) temp.get((((j + 1) * 16) - 1) - i)).intValue() << i;
            }
            chars[j] = (char) (charNum + 96);
        }
        if (temp.size() % 16 != 0) {
            charNum = 0;
            for (i = 0; i < temp.size() % 16; i++) {
                charNum += ((Integer) temp.get(((temp.size() / 16) * 16) + i)).intValue() << (15 - i);
            }
            chars[temp.size() / 16] = (char) (charNum + 96);
        }
        return chars;
    }

    public static ArrayList<Integer> boolsToIntArray(boolean[] bools) {
        int i;
        ArrayList<Integer> intArr = new ArrayList();
        int size = bools.length / 15;
        for (i = 0; i < size; i++) {
            for (int j = 0; j < 16; j++) {
                if (j == 0) {
                    intArr.add(Integer.valueOf(0));
                } else {
                    intArr.add(Integer.valueOf(bools[((i * 15) + j) + -1] ? 1 : 0));
                }
            }
        }
        if (bools.length % 15 != 0) {
            intArr.add(Integer.valueOf(0));
            for (i = 0; i < bools.length % 15; i++) {
                int i2;
                if (bools[((bools.length / 15) * 15) + i]) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                intArr.add(Integer.valueOf(i2));
            }
        }
        return intArr;
    }

    public static byte[] genBytesFromObject(MessageObj dataObj) {
        Log.i(TAG, "genBytesFromObject(), dataObj=" + dataObj);
        if (dataObj == null) {
            return null;
        }
        byte[] bArr = null;
        try {
            return dataObj.genXmlBuff();
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
            return bArr;
        } catch (IllegalStateException e12) {
            e12.printStackTrace();
            return bArr;
        } catch (IOException e13) {
            e13.printStackTrace();
            return bArr;
        } catch (XmlPullParserException e14) {
            e14.printStackTrace();
            return bArr;
        } catch (NoDataException e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public static String getDate(String day, int prev_index) {
        SimpleDateFormat formatter = new SimpleDateFormat(Tools.BIRTH_FORMAT);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(formatter.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(c.get(1), c.get(2), c.get(5) - prev_index);
        return formatter.format(c.getTime());
    }

    private static String[] getNotificationText(Notification mNotification) {
        try {
            RemoteViews remoteViews = mNotification.contentView;
            Class<? extends RemoteViews> remoteViewsClass = remoteViews.getClass();
            HashMap<Integer, String> text = new HashMap();
            Field actionField = null;
            for (Field outerField : remoteViewsClass.getDeclaredFields()) {
                if (outerField.getName().equals("mActions")) {
                    actionField = outerField;
                    break;
                }
            }
            if (actionField == null) {
                return null;
            }
            actionField.setAccessible(true);
            int viewId = 0;
            Iterator it = ((ArrayList) actionField.get(remoteViews)).iterator();
            while (it.hasNext()) {
                Object action = it.next();
                Object value = null;
                Integer type = null;
                for (Field field : action.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.getName().equals("value")) {
                        value = field.get(action);
                    } else if (field.getName().equals("type")) {
                        type = Integer.valueOf(field.getInt(action));
                    } else if (field.getName().equals("methodName") && ((String) field.get(action)).equals("setProgress")) {
                        return null;
                    }
                }
                if (type != null && ((type.intValue() == 9 || type.intValue() == 10) && value != null)) {
                    viewId++;
                    text.put(Integer.valueOf(viewId), value.toString());
                    if (viewId == 2) {
                        break;
                    }
                }
            }
            return (String[]) text.values().toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ApplicationInfo getAppInfo(Context context, CharSequence packageName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(packageName.toString(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    private static String getAppName(Context context, ApplicationInfo appInfo) {
        if (context == null || appInfo == null) {
            return NULL_TEXT_NAME;
        }
        return context.getPackageManager().getApplicationLabel(appInfo).toString();
    }

    private static Bitmap getMessageIcon(Context context, ApplicationInfo appInfo) {
        return createIcon(context, appInfo, false);
    }

    private static Bitmap createIcon(Context context, ApplicationInfo appInfo, boolean isAppIcon) {
        if (context == null || appInfo == null) {
            return null;
        }
        Bitmap icon;
        Drawable drawable = context.getPackageManager().getApplicationIcon(appInfo);
        if (isAppIcon) {
            icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        } else {
            icon = createWhiteBitmap();
        }
        Canvas canvas = new Canvas(icon);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return icon;
    }

    private static Bitmap createWhiteBitmap() {
        Bitmap whiteBitmap = Bitmap.createBitmap(40, 40, Config.RGB_565);
        int[] pixels = new int[1600];
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                int index = (y * 40) + x;
                int b = (pixels[index] & 255) | 255;
                pixels[index] = ((-16777216 | ((((pixels[index] >> 16) & 255) | 255) << 16)) | ((((pixels[index] >> 8) & 255) | 255) << 8)) | b;
            }
        }
        whiteBitmap.setPixels(pixels, 0, 40, 0, 0, 40, 40);
        return whiteBitmap;
    }

    protected static NotificationMessageBody createNotificationBody(Context context, String pkgName, Notification threadNotfication) {
        int timestamp;
        ApplicationInfo appinfo = getAppInfo(context, pkgName);
        String appName = getAppName(context, appinfo);
        Bitmap sendIcon = getMessageIcon(context, appinfo);
        if (System.currentTimeMillis() - threadNotfication.when > 3600000) {
            timestamp = getUtcTime(System.currentTimeMillis());
        } else {
            timestamp = getUtcTime(threadNotfication.when);
        }
        String title = "";
        String content = "";
        String[] textList = getNotificationText(threadNotfication);
        if (textList != null) {
            if (textList.length > 0 && textList[0] != null) {
                title = textList[0];
            }
            if (textList.length > 1 && textList[1] != null) {
                content = textList[1];
            }
            if (title.length() > 128) {
                title = title.substring(0, 128) + TEXT_POSTFIX;
            }
            if (content.length() > 256) {
                content = content.substring(0, 256) + TEXT_POSTFIX;
            }
        }
        String tickerText = "";
        if (threadNotfication.tickerText != null && content.length() == 0) {
            tickerText = threadNotfication.tickerText.toString();
        }
        if (tickerText.length() > 128) {
            tickerText = tickerText.substring(0, 128) + TEXT_POSTFIX;
        }
        if (tickerText.length() > 0) {
            tickerText = "[".concat(tickerText).concat("]");
        }
        String appID = threadNotfication.number + "";
        NotificationMessageBody body = new NotificationMessageBody();
        body.setSender(appName);
        body.setAppID(appID);
        body.setTitle(title);
        body.setContent(content);
        body.setTickerText(tickerText);
        body.setTimestamp(timestamp);
        body.setIcon(sendIcon);
        return body;
    }

    protected static MessageHeader createNotificationHeader() {
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType("text");
        header.setMsgId(genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        return header;
    }

    protected static void endPhone() {
        Log.i("zhuqichao", "endcall");
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService("phone");
        try {
            Method m = Class.forName(tm.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            m.setAccessible(true);
            ((ITelephony) m.invoke(tm, new Object[0])).endCall();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("zhuqichao", e.toString());
        }
    }
}
