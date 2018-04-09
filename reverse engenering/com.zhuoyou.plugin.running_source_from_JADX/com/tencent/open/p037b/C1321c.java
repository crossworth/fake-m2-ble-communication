package com.tencent.open.p037b;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Global;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Locale;

/* compiled from: ProGuard */
public class C1321c {
    static String f4135a = null;
    static String f4136b = null;
    static String f4137c = null;
    private static String f4138d;
    private static String f4139e = null;

    public static String m3889a() {
        try {
            Context context = Global.getContext();
            if (context == null) {
                return "";
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return "";
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                return "";
            }
            return connectionInfo.getMacAddress();
        } catch (Throwable e) {
            C1314f.m3868b("openSDK_LOG.MobileInfoUtil", "getLocalMacAddress>>>", e);
            return "";
        }
    }

    public static String m3890a(Context context) {
        if (!TextUtils.isEmpty(f4138d)) {
            return f4138d;
        }
        if (context == null) {
            return "";
        }
        f4138d = "";
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            int width = windowManager.getDefaultDisplay().getWidth();
            f4138d = width + "x" + windowManager.getDefaultDisplay().getHeight();
        }
        return f4138d;
    }

    public static String m3891b() {
        return Locale.getDefault().getLanguage();
    }

    public static String m3892b(Context context) {
        if (f4135a != null && f4135a.length() > 0) {
            return f4135a;
        }
        if (context == null) {
            return "";
        }
        try {
            f4135a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            return f4135a;
        } catch (Exception e) {
            return "";
        }
    }

    public static String m3893c(Context context) {
        if (f4136b != null && f4136b.length() > 0) {
            return f4136b;
        }
        if (context == null) {
            return "";
        }
        try {
            f4136b = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            return f4136b;
        } catch (Exception e) {
            return "";
        }
    }

    public static String m3894d(Context context) {
        if (f4137c != null && f4137c.length() > 0) {
            return f4137c;
        }
        if (context == null) {
            return "";
        }
        try {
            f4137c = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            return f4137c;
        } catch (Exception e) {
            return "";
        }
    }

    public static String m3895e(Context context) {
        try {
            if (f4139e == null) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("imei=").append(C1321c.m3892b(context)).append('&');
                stringBuilder.append("model=").append(Build.MODEL).append('&');
                stringBuilder.append("os=").append(VERSION.RELEASE).append('&');
                stringBuilder.append("apilevel=").append(VERSION.SDK_INT).append('&');
                String b = C1319a.m3885b(context);
                if (b == null) {
                    b = "";
                }
                stringBuilder.append("network=").append(b).append('&');
                stringBuilder.append("sdcard=").append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0).append('&');
                stringBuilder.append("display=").append(displayMetrics.widthPixels).append('*').append(displayMetrics.heightPixels).append('&');
                stringBuilder.append("manu=").append(Build.MANUFACTURER).append("&");
                stringBuilder.append("wifi=").append(C1319a.m3888e(context));
                f4139e = stringBuilder.toString();
            }
            return f4139e;
        } catch (Exception e) {
            return null;
        }
    }
}
