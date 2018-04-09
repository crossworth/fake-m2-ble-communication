package com.zhuoyou.plugin.bluetooth.data;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfile.ServiceListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.Settings.System;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.connection.BtProfileReceiver;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.running.RunningApp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import p031u.aly.au;

public class Util {
    public static final int APP_ICON_HEIGHT = 40;
    public static final int APP_ICON_WIDTH = 40;
    private static final String LOG_TAG = "Util";
    public static final int NOTIFYMINIHEADERLENTH = 8;
    public static final int NOTIFYSYNCLENTH = 4;
    public static final String NULL_TEXT_NAME = "(unknown)";
    private static final String SP_DEVICE_FILENAME = "BLE_Device";
    private static final String SP_DEVICE_KEY = "ble_address";
    private static final String SP_DEVICE_TYPE = "ble_device_type";
    private static final String SP_LATEST_DEVICE_FILENAME = "BLE_LATEST_Device";
    private static final String SP_LATEST_DEVICE_KEY = "ble_latest_address";
    public static final int TEXT_MAX_LENGH = 256;
    public static final String TEXT_POSTFIX = "...";
    public static final int TICKER_TEXT_MAX_LENGH = 128;
    public static final int TITLE_TEXT_MAX_LENGH = 128;
    public static final String[] bleDevices = new String[]{"Unik 1", "Unik 2", "LEO", "A7", "T-Band", "Rumor-1", "Rumor-2", "M2"};
    public static final String[] filterNames = new String[]{"EAMEY P1", "EAMEY P3", "Primo 5", "Primo 5C", "Primo 1", "Primo 3", "TJ01", "Meegoo A10", "GEMINI1", "Megoo2", "S3", "Unik 3", "LUNA 3", "ABT-100"};
    private static BluetoothHeadset mBluetoothHeadset;
    private static ServiceListener mProfileListener = new C12011();
    private static int sMessageId = 256;

    static class C12011 implements ServiceListener {
        C12011() {
        }

        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == 1) {
                Util.mBluetoothHeadset = (BluetoothHeadset) proxy;
            }
        }

        public void onServiceDisconnected(int profile) {
            if (profile == 1) {
                Util.mBluetoothHeadset = null;
            }
        }
    }

    public static int genMessageId() {
        Log.i(LOG_TAG, "genMessageId(), messageId=" + sMessageId);
        int i = sMessageId;
        sMessageId = i + 1;
        return i;
    }

    public static boolean isScreenLocked(Context context) {
        Boolean isScreenLocked = Boolean.valueOf(((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode());
        Log.i(LOG_TAG, "isScreenOn(), isScreenOn=" + isScreenLocked);
        return isScreenLocked.booleanValue();
    }

    public static boolean isScreenOn(Context context) {
        Boolean isScreenOn = Boolean.valueOf(((PowerManager) context.getSystemService("power")).isScreenOn());
        Log.i(LOG_TAG, "isScreenOn(), isScreenOn=" + isScreenOn);
        return isScreenOn.booleanValue();
    }

    public static boolean isSystemApp(ApplicationInfo appInfo) {
        if ((appInfo.flags & 1) == 0 && (appInfo.flags & 128) == 0) {
            return false;
        }
        return true;
    }

    public static int getUtcTime(long localTime) {
        Log.i(LOG_TAG, "getUTCTime(), local time=" + localTime);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(localTime);
        int utcTime = (int) (cal.getTimeInMillis() / 1000);
        Log.i(LOG_TAG, "getUTCTime(), UTC time=" + utcTime);
        return utcTime;
    }

    public static int getUtcTimeZone(long localTime) {
        TimeZone tz = TimeZone.getDefault();
        int tzs = tz.getRawOffset();
        if (tz.inDaylightTime(new Date(localTime))) {
            tzs += tz.getDSTSavings();
        }
        Log.i(LOG_TAG, "getUtcTimeZone(), UTC time zone=" + tzs);
        return tzs;
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

    public static ApplicationInfo getAppInfo(Context context, CharSequence packageName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(packageName.toString(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "getAppInfo(), appInfo=" + appInfo);
        return appInfo;
    }

    public static String getAppName(Context context, ApplicationInfo appInfo) {
        String appName;
        if (context == null || appInfo == null) {
            appName = NULL_TEXT_NAME;
        } else {
            appName = context.getPackageManager().getApplicationLabel(appInfo).toString();
        }
        Log.i(LOG_TAG, "getAppName(), appName=" + appName);
        return appName;
    }

    public static Bitmap getAppIcon(Context context, ApplicationInfo appInfo) {
        Log.i(LOG_TAG, "getAppIcon()");
        return createIcon(context, appInfo, true);
    }

    public static Bitmap getMessageIcon(Context context, ApplicationInfo appInfo) {
        Log.i(LOG_TAG, "getMessageIcon()");
        return createIcon(context, appInfo, false);
    }

    private static Bitmap createIcon(Context context, ApplicationInfo appInfo, boolean isAppIcon) {
        Bitmap icon;
        if (context == null || appInfo == null) {
            icon = null;
        } else {
            Drawable drawable = context.getPackageManager().getApplicationIcon(appInfo);
            if (isAppIcon) {
                icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
            } else {
                icon = createWhiteBitmap();
            }
            Canvas canvas = new Canvas(icon);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        Log.i(LOG_TAG, "createIcon(), icon width=" + icon.getWidth());
        return icon;
    }

    private static Bitmap createWhiteBitmap() {
        Bitmap whiteBitmap = Bitmap.createBitmap(40, 40, Config.RGB_565);
        int[] pixels = new int[1600];
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                int index = (y * 40) + x;
                int b = (pixels[index] & 255) | 255;
                pixels[index] = ((ViewCompat.MEASURED_STATE_MASK | ((((pixels[index] >> 16) & 255) | 255) << 16)) | ((((pixels[index] >> 8) & 255) | 255) << 8)) | b;
            }
        }
        Log.i(LOG_TAG, "createWhiteBitmap(), pixels num=" + pixels.length);
        whiteBitmap.setPixels(pixels, 0, 40, 0, 0, 40, 40);
        return whiteBitmap;
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
            Cursor cursor = context.getContentResolver().query(uri, new String[]{au.f3578g}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
            Log.i(LOG_TAG, "getContactName(), contactName=" + contactName);
            return contactName;
        } catch (Exception e) {
            Log.i(LOG_TAG, "getContactName Exception");
            return null;
        }
    }

    public static String getKeyFromValue(CharSequence charSequence) {
        Iterator<?> it = AppList.getInstance().getAppList().entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (entry.getValue() != null && entry.getValue().equals(charSequence)) {
                return entry.getKey().toString();
            }
        }
        return null;
    }

    public static void setUnreadSmsToRead(Context ctx, int id) {
        Uri sms = Uri.parse(MapConstants.INBOX);
        Cursor c = ctx.getContentResolver().query(sms, null, "_id = " + id, null, null);
        if (!c.moveToFirst()) {
            Log.i("gchk", "没找到msg id = " + id);
        } else if (c.getInt(c.getColumnIndex(MapConstants.READ)) == 0) {
            ContentValues cv = new ContentValues();
            cv.put(MapConstants.READ, Integer.valueOf(1));
            Log.i("gchk", "msg id = " + id + "  成功设置为已读ret =" + ctx.getContentResolver().update(sms, cv, "_id = " + id, null));
        } else {
            Log.i("gchk", "msg id = " + id + "  在此之前已经被设置为已读");
        }
    }

    public static String getProductName(String name) {
        String productName = "";
        if (name == null) {
            return productName;
        }
        if (name.startsWith("EAMEY P1")) {
            return "EAMEY P1";
        }
        if (name.startsWith("EAMEY P3")) {
            return "EAMEY P3";
        }
        if (name.startsWith("Primo 5")) {
            return "Primo 5";
        }
        if (name.startsWith("Primo 5C")) {
            return "Primo 5C";
        }
        if (name.startsWith("Primo 1")) {
            return "Primo 1";
        }
        if (name.startsWith("Primo 3")) {
            return "Primo 3";
        }
        if (name.startsWith("TJ01")) {
            return "TJ01";
        }
        if (name.startsWith("Meegoo A10")) {
            return "Meegoo A10";
        }
        if (name.startsWith("GEMINI1")) {
            return "GEMINI1";
        }
        if (name.startsWith("Megoo2")) {
            return "Megoo2";
        }
        if (name.startsWith("Unik 3")) {
            return "Unik 3";
        }
        if (name.startsWith("LUNA3")) {
            return "LUNA 3";
        }
        if (name.startsWith("UNIK 3SE")) {
            return "UNIK 3SE";
        }
        if (name.startsWith("Unik 1")) {
            return "Unik 1";
        }
        if (name.startsWith("Unik 2")) {
            return "Unik 2";
        }
        if (name.startsWith("LEO")) {
            return "LEO";
        }
        if (name.startsWith("S3")) {
            return "S3";
        }
        if (name.startsWith("A7")) {
            return "A7";
        }
        if (name.startsWith("T-Band")) {
            return "T-Band";
        }
        if (name.startsWith("Rumor-1")) {
            return "Rumor-1";
        }
        if (name.startsWith("ABT-100")) {
            return "ABT-100";
        }
        if (name.startsWith("Rumor-2")) {
            return "Rumor-2";
        }
        if (name.equals("Primo 5")) {
            return "Primo 5";
        }
        if (name.equals("Primo 5C")) {
            return "Primo 5C";
        }
        if (name.equals("M2")) {
            return "M2";
        }
        return productName;
    }

    public static boolean isBleDevice(String deviceName) {
        for (String equals : bleDevices) {
            if (equals.equals(deviceName)) {
                return true;
            }
        }
        return false;
    }

    public static int getIconByDeviceName(String name, boolean flag) {
        String productName = getProductName(name);
        if (productName.equals("")) {
            return 0;
        }
        return getProductIcon(productName, flag);
    }

    public static int getSearchByDeviceName(String name, boolean flag) {
        String productName = getProductName(name);
        if (productName.equals("")) {
            return 0;
        }
        return getSearchIcon(productName, flag);
    }

    public static int getSearchIcon(String name, boolean flag) {
        if (name == null) {
            return 0;
        }
        if (name.startsWith("LEO")) {
            return R.drawable.search_leo;
        }
        if (name.startsWith("Unik 1")) {
            return R.drawable.search_luna1;
        }
        if (name.startsWith("LUNA3")) {
            return R.drawable.search_luna3;
        }
        if (name.startsWith("UNIK 3SE")) {
            return R.drawable.search_luna3;
        }
        if (name.startsWith("Unik 2")) {
            return R.drawable.search_leo;
        }
        if (name.startsWith("A7")) {
            return R.drawable.search_leo;
        }
        if (name.startsWith("T-Band")) {
            return R.drawable.search_leo;
        }
        if (name.startsWith("Rumor-1")) {
            return R.drawable.lunaa1_connect;
        }
        if (name.startsWith("Rumor-2")) {
            return R.drawable.lunaa1_connect;
        }
        if (name.startsWith("Primo 5")) {
            return R.drawable.search_luna1;
        }
        if (name.startsWith("Primo 5C")) {
            return R.drawable.search_luna1;
        }
        if (name.startsWith("M2")) {
            return R.drawable.mi2_connect;
        }
        return 0;
    }

    public static int getProductIcon(String name, boolean flag) {
        if (flag) {
            if (name == null) {
                return R.drawable.p1_connect;
            }
            if (name.startsWith("EAMEY P1")) {
                return R.drawable.p1_connect;
            }
            if (name.startsWith("EAMEY P3")) {
                return R.drawable.p3_connect;
            }
            if (name.startsWith("Primo 5")) {
                return R.drawable.mars5_connect;
            }
            if (name.startsWith("Primo 5C")) {
                return R.drawable.mars5_connect;
            }
            if (name.startsWith("ABT-100")) {
                return R.drawable.p3_connect;
            }
            if (name.startsWith("Primo 1")) {
                return R.drawable.p1_connect;
            }
            if (name.startsWith("Primo 3")) {
                return R.drawable.p3_connect;
            }
            if (name.startsWith("TJ01")) {
                return R.drawable.t1_connect;
            }
            if (name.startsWith("Meegoo A10")) {
                return R.drawable.a1_connect;
            }
            if (name.startsWith("Megoo2")) {
                return R.drawable.m2_connect;
            }
            if (name.startsWith("LUNA3")) {
                return R.drawable.luna3_connect;
            }
            if (name.startsWith("UNIK 3SE")) {
                return R.drawable.luna3_connect;
            }
            if (name.startsWith("Unik 3")) {
                return R.drawable.luna3_connect;
            }
            if (name.startsWith("Unik 1")) {
                return R.drawable.luna1_connect;
            }
            if (name.startsWith("Unik 2")) {
                return R.drawable.leo_connect;
            }
            if (name.startsWith("LEO")) {
                return R.drawable.leo_connect;
            }
            if (name.startsWith("S3")) {
                return R.drawable.s3_connect;
            }
            if (name.startsWith("A7")) {
                return R.drawable.a7_connect;
            }
            if (name.startsWith("T-Band")) {
                return R.drawable.luna5_connect;
            }
            if (name.startsWith("Rumor-1")) {
                return R.drawable.rumor1_connect;
            }
            if (name.startsWith("Rumor-2")) {
                return R.drawable.lunaa1_connect;
            }
            if (name.startsWith("Primo 5")) {
                return R.drawable.mars5_connect;
            }
            if (name.startsWith("Primo 5C")) {
                return R.drawable.mars5_connect;
            }
            if (name.startsWith("M2")) {
                return R.drawable.mi2_connect;
            }
            return R.drawable.p1_connect;
        } else if (name == null) {
            return R.drawable.p1_disconnect;
        } else {
            if (name.startsWith("EAMEY P1")) {
                return R.drawable.p1_disconnect;
            }
            if (name.startsWith("EAMEY P3")) {
                return R.drawable.p3_disconnect;
            }
            if (name.startsWith("Primo 5")) {
                return R.drawable.mars5_disconnect;
            }
            if (name.startsWith("Primo 5C")) {
                return R.drawable.mars5_disconnect;
            }
            if (name.startsWith("Primo 1")) {
                return R.drawable.p1_disconnect;
            }
            if (name.startsWith("Primo 3")) {
                return R.drawable.p3_disconnect;
            }
            if (name.startsWith("ABT-100")) {
                return R.drawable.p3_disconnect;
            }
            if (name.startsWith("TJ01")) {
                return R.drawable.t1_disconnect;
            }
            if (name.startsWith("Meegoo A10")) {
                return R.drawable.a1_disconnect;
            }
            if (name.startsWith("Megoo2")) {
                return R.drawable.m2_disconnect;
            }
            if (name.startsWith("Unik 3")) {
                return R.drawable.luna3_disconnect;
            }
            if (name.startsWith("LUNA3")) {
                return R.drawable.luna3_disconnect;
            }
            if (name.startsWith("UNIK 3SE")) {
                return R.drawable.luna3_disconnect;
            }
            if (name.startsWith("Unik 1")) {
                return R.drawable.luna1_disconnect;
            }
            if (name.startsWith("Unik 2")) {
                return R.drawable.leo_disconnect;
            }
            if (name.startsWith("LEO")) {
                return R.drawable.leo_disconnect;
            }
            if (name.startsWith("S3")) {
                return R.drawable.s3_disconnect;
            }
            if (name.startsWith("A7")) {
                return R.drawable.a7_disconnect;
            }
            if (name.startsWith("T-Band")) {
                return R.drawable.luna5_disconnect;
            }
            if (name.startsWith("Rumor-1")) {
                return R.drawable.lunaa1_disconnect;
            }
            if (name.startsWith("Rumor-2")) {
                return R.drawable.lunaa1_disconnect;
            }
            if (name.startsWith("Primo 5")) {
                return R.drawable.mars5_disconnect;
            }
            if (name.startsWith("Primo 5C")) {
                return R.drawable.mars5_disconnect;
            }
            if (name.startsWith("M2")) {
                return R.drawable.mi2_disconnect;
            }
            return R.drawable.p1_disconnect;
        }
    }

    public static boolean getLatestDeviceType(Context sContext) {
        return sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).getBoolean(SP_DEVICE_TYPE, false);
    }

    public static void setLatestDeviceType(Context sContext, boolean deviceType) {
        Editor et = sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).edit();
        et.putBoolean(SP_DEVICE_TYPE, deviceType);
        Log.i(LOG_TAG, "deviceType:" + deviceType);
        et.commit();
    }

    public static String getLatestConnectDeviceAddress(Context sContext) {
        return sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).getString(SP_DEVICE_KEY, "");
    }

    public static void updateLatestConnectDeviceAddress(Context sContext, String address) {
        Editor et = sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).edit();
        et.putString(SP_DEVICE_KEY, address);
        Log.i(LOG_TAG, "BLEaddress:" + address);
        et.commit();
    }

    public static String getLLatsetConnectDeviceAddress(Context sContext) {
        return sContext.getSharedPreferences(SP_LATEST_DEVICE_FILENAME, 0).getString(SP_LATEST_DEVICE_KEY, "");
    }

    public static void updateLLatestConnectDeviceAddress(Context sContext, String address) {
        Editor et = sContext.getSharedPreferences(SP_LATEST_DEVICE_FILENAME, 0).edit();
        et.putString(SP_LATEST_DEVICE_KEY, address);
        et.commit();
    }

    public static List<BluetoothDevice> getBondDevice() {
        List<BluetoothDevice> bondDevices = new ArrayList();
        Object[] listDevice = BluetoothAdapter.getDefaultAdapter().getBondedDevices().toArray();
        for (BluetoothDevice device : listDevice) {
            for (String equals : filterNames) {
                if (equals.equals(device.getName())) {
                    bondDevices.add(device);
                    break;
                }
            }
        }
        return bondDevices;
    }

    public static BluetoothDevice getConnectDevice() {
        BluetoothDevice connectDevice = null;
        Log.i("lsj", "BluetoothService.getInstance().isConnected()==" + BluetoothService.getInstance().isConnected());
        if (BluetoothService.getInstance().isConnected()) {
            BluetoothDevice device = BtProfileReceiver.getRemoteDevice();
            Log.i("lsj", "device==" + device);
            if (device != null) {
                connectDevice = device;
            }
        } else {
            connectDevice = null;
        }
        Log.i("lsj", "connectDevice =" + connectDevice);
        return connectDevice;
    }

    public static void connect(BluetoothDevice device) {
        if (BluetoothService.getInstance() != null) {
            BluetoothService.getInstance().connectToRemoteDevice(device);
        }
    }

    public static void removeBond(BluetoothDevice device) {
        try {
            BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(device, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bondDevice(BluetoothDevice device) {
        try {
            BluetoothDevice.class.getMethod("createBond", new Class[0]).invoke(device, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handUnlink(Context sContext, boolean isHand) {
        Editor et = sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).edit();
        et.putBoolean("isHandUnlink", isHand);
        et.commit();
    }

    public static boolean isHandUnlink(Context sContext) {
        return sContext.getSharedPreferences(SP_DEVICE_FILENAME, 0).getBoolean("isHandUnlink", true);
    }

    private static BluetoothDevice getBTProxy(Context context) {
        BluetoothDevice remoteDevice = null;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.getProfileConnectionState(1) == 2 || mBluetoothAdapter.getProfileConnectionState(1) == 1) {
            mBluetoothAdapter.getProfileProxy(context, mProfileListener, 1);
        }
        if (mBluetoothHeadset != null) {
            List<BluetoothDevice> deviceList = mBluetoothHeadset.getConnectedDevices();
            if (deviceList != null && deviceList.size() > 0) {
                Log.i("caixinxin", "deviceList = " + deviceList);
                BluetoothDevice device = (BluetoothDevice) deviceList.get(0);
                String name = device.getName();
                if (name != null) {
                    for (String equals : filterNames) {
                        if (equals.equals(name)) {
                            remoteDevice = device;
                            break;
                        }
                    }
                }
            }
        }
        mBluetoothAdapter.closeProfileProxy(1, mBluetoothHeadset);
        return remoteDevice;
    }

    public static void autoConnect(Context context) {
        BluetoothDevice currentDevice = getBTProxy(context);
        if (currentDevice != null && !BluetoothService.getInstance().isConnected()) {
            connect(currentDevice);
        }
    }

    public static int dip2pixel(Context paramContext, float paramFloat) {
        return (int) TypedValue.applyDimension(1, paramFloat, paramContext.getResources().getDisplayMetrics());
    }

    public static void setDeviceName(String deviceName) {
        Editor et = RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_DEVICE_FILENAME, 0).edit();
        et.putString("device_Name", deviceName);
        et.commit();
    }

    public static String getDeviceName() {
        return RunningApp.getInstance().getApplicationContext().getSharedPreferences(SP_DEVICE_FILENAME, 0).getString("device_Name", "");
    }
}
