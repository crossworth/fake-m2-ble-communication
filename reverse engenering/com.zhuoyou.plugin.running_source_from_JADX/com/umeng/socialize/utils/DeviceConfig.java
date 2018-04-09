package com.umeng.socialize.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DeviceConfig {
    protected static final String LOG_TAG = "DeviceConfig";
    private static final String MOBILE_NETWORK = "2G/3G";
    protected static final String UNKNOW = "Unknown";
    private static final String WIFI = "Wi-Fi";
    public static Context context;

    public static boolean isAppInstalled(String str, Context context) {
        boolean z = false;
        if (context != null) {
            synchronized (context) {
                try {
                    context.getPackageManager().getPackageInfo(str, 1);
                    z = true;
                } catch (NameNotFoundException e) {
                }
            }
        }
        return z;
    }

    public static String getAppVersion(String str, Context context) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context.getPackageManager().checkPermission(str, context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    public static String getDeviceId(Context context) {
        String deviceId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            Log.m4558w(LOG_TAG, "No IMEI.");
        }
        String str = "";
        try {
            if (checkPermission(context, Permissions.PERMISSION_PHONE)) {
                deviceId = telephonyManager.getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
                Log.m4558w(LOG_TAG, "No IMEI.");
                deviceId = getMac(context);
                if (TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
                Log.m4558w(LOG_TAG, "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");
                deviceId = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
                Log.m4558w(LOG_TAG, "getDeviceId: Secure.ANDROID_ID: " + deviceId);
                if (TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
                return getDeviceSN();
            }
        } catch (Exception e) {
            Log.m4559w(LOG_TAG, "No IMEI.", e);
        }
        deviceId = str;
        if (TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        Log.m4558w(LOG_TAG, "No IMEI.");
        deviceId = getMac(context);
        if (TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        Log.m4558w(LOG_TAG, "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");
        deviceId = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        Log.m4558w(LOG_TAG, "getDeviceId: Secure.ANDROID_ID: " + deviceId);
        if (TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        return getDeviceSN();
    }

    public static String getDeviceSN() {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = new String[]{"Unknown", "Unknown"};
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            strArr[0] = "Unknown";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "Unknown";
            return strArr;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo == null || networkInfo.getState() != State.CONNECTED) {
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            if (networkInfo2 == null || networkInfo2.getState() != State.CONNECTED) {
                return strArr;
            }
            strArr[0] = MOBILE_NETWORK;
            strArr[1] = networkInfo2.getSubtypeName();
            return strArr;
        }
        strArr[0] = WIFI;
        return strArr;
    }

    public static boolean isOnline(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") && isOnline(context)) {
            return true;
        }
        return false;
    }

    public static boolean isSdCardWrittenable() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static String getAndroidID(Context context) {
        return Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
    }

    public static String getOsVersion() {
        return VERSION.RELEASE;
    }

    public static String getMac(Context context) {
        Exception e;
        String str = "";
        String macShell;
        try {
            macShell = getMacShell();
            try {
                if (TextUtils.isEmpty(macShell)) {
                    Log.m4558w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
                }
                if (TextUtils.isEmpty(macShell)) {
                    return SocializeSpUtils.getMac(context);
                }
                SocializeSpUtils.putMac(context, macShell);
                return macShell;
            } catch (Exception e2) {
                e = e2;
                Log.m4558w(LOG_TAG, "Could not get mac address." + e.toString());
                return macShell;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            macShell = str;
            e = exception;
            Log.m4558w(LOG_TAG, "Could not get mac address." + e.toString());
            return macShell;
        }
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    private static String getMacShell() {
        int i = 0;
        String[] strArr = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
        while (i < strArr.length) {
            try {
                String reaMac = reaMac(strArr[i]);
                if (reaMac != null) {
                    return reaMac;
                }
                i++;
            } catch (Exception e) {
                Log.m4550e(LOG_TAG, "open file  Failed", e);
            }
        }
        return null;
    }

    private static String reaMac(String str) throws FileNotFoundException {
        BufferedReader bufferedReader;
        Exception e;
        Throwable th;
        String str2 = null;
        Reader fileReader = new FileReader(str);
        if (fileReader != null) {
            try {
                bufferedReader = new BufferedReader(fileReader, 1024);
                try {
                    str2 = bufferedReader.readLine();
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                    try {
                        Log.m4550e(LOG_TAG, "Could not read from file " + str, e);
                        if (fileReader != null) {
                            try {
                                fileReader.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2222) {
                                e2222.printStackTrace();
                            }
                        }
                        return str2;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileReader != null) {
                            try {
                                fileReader.close();
                            } catch (IOException e22222) {
                                e22222.printStackTrace();
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e222222) {
                                e222222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                bufferedReader = str2;
                Log.m4550e(LOG_TAG, "Could not read from file " + str, e);
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                return str2;
            } catch (Throwable th3) {
                bufferedReader = str2;
                th = th3;
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        }
        return str2;
    }
}
