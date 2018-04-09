package com.umeng.analytics.social;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0919a;
import com.umeng.analytics.social.UMPlatformData.GENDER;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

/* compiled from: UMUtils */
public abstract class C0943f {
    private static Map<String, String> f3212a;

    protected static String[] m3161a(Context context, String str, UMPlatformData... uMPlatformDataArr) throws C0938a {
        if (uMPlatformDataArr == null || uMPlatformDataArr.length == 0) {
            throw new C0938a("platform data is null");
        }
        Object appkey = AnalyticsConfig.getAppkey(context);
        if (TextUtils.isEmpty(appkey)) {
            throw new C0938a("can`t get appkey.");
        }
        List arrayList = new ArrayList();
        String str2 = "http://log.umsns.com/share/api/" + appkey + "/";
        if (f3212a == null || f3212a.isEmpty()) {
            f3212a = C0943f.m3163b(context);
        }
        if (!(f3212a == null || f3212a.isEmpty())) {
            for (Entry entry : f3212a.entrySet()) {
                arrayList.add(((String) entry.getKey()) + "=" + ((String) entry.getValue()));
            }
        }
        arrayList.add("date=" + String.valueOf(System.currentTimeMillis()));
        arrayList.add("channel=" + C0942e.f3194e);
        if (!TextUtils.isEmpty(str)) {
            arrayList.add("topic=" + str);
        }
        arrayList.addAll(C0943f.m3158a(uMPlatformDataArr));
        String b = C0943f.m3162b(uMPlatformDataArr);
        if (b == null) {
            b = "null";
        }
        String str3 = str2 + "?" + C0943f.m3157a(arrayList);
        while (str3.contains("%2C+")) {
            str3 = str3.replace("%2C+", "&");
        }
        while (str3.contains("%3D")) {
            str3 = str3.replace("%3D", "=");
        }
        while (str3.contains("%5B")) {
            str3 = str3.replace("%5B", "");
        }
        while (str3.contains("%5D")) {
            str3 = str3.replace("%5D", "");
        }
        C0939b.m3141c(C0919a.f3107d, "URL:" + str3);
        C0939b.m3141c(C0919a.f3107d, "BODY:" + b);
        return new String[]{str3, b};
    }

    private static String m3157a(List<String> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(URLEncoder.encode(list.toString()).getBytes());
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> m3158a(UMPlatformData... uMPlatformDataArr) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        for (UMPlatformData uMPlatformData : uMPlatformDataArr) {
            stringBuilder.append(uMPlatformData.getMeida().toString());
            stringBuilder.append(',');
            stringBuilder2.append(uMPlatformData.getUsid());
            stringBuilder2.append(',');
            stringBuilder3.append(uMPlatformData.getWeiboId());
            stringBuilder3.append(',');
        }
        if (uMPlatformDataArr.length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
        }
        List<String> arrayList = new ArrayList();
        arrayList.add("platform=" + stringBuilder.toString());
        arrayList.add("usid=" + stringBuilder2.toString());
        if (stringBuilder3.length() > 0) {
            arrayList.add("weiboid=" + stringBuilder3.toString());
        }
        return arrayList;
    }

    private static String m3162b(UMPlatformData... uMPlatformDataArr) {
        JSONObject jSONObject = new JSONObject();
        for (UMPlatformData uMPlatformData : uMPlatformDataArr) {
            Object obj;
            GENDER gender = uMPlatformData.getGender();
            CharSequence name = uMPlatformData.getName();
            if (gender == null) {
                try {
                    if (TextUtils.isEmpty(name)) {
                    }
                } catch (Throwable e) {
                    throw new C0938a("build body exception", e);
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            String str = SocializeProtocolConstants.PROTOCOL_KEY_GENDER;
            if (gender == null) {
                obj = "";
            } else {
                obj = String.valueOf(gender.value);
            }
            jSONObject2.put(str, obj);
            jSONObject2.put("name", name == null ? "" : String.valueOf(name));
            jSONObject.put(uMPlatformData.getMeida().toString(), jSONObject2);
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject.toString();
    }

    private static Map<String, String> m3163b(Context context) throws C0938a {
        Map<String, String> hashMap = new HashMap();
        Map a = C0943f.m3159a(context);
        if (a == null || a.isEmpty()) {
            throw new C0938a("can`t get device id.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (Entry entry : a.entrySet()) {
            if (!TextUtils.isEmpty((CharSequence) entry.getValue())) {
                stringBuilder2.append((String) entry.getKey()).append(SeparatorConstants.SEPARATOR_ADS_ID);
                stringBuilder.append((String) entry.getValue()).append(SeparatorConstants.SEPARATOR_ADS_ID);
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            hashMap.put("deviceid", stringBuilder.toString());
        }
        if (stringBuilder2.length() > 0) {
            stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            hashMap.put("idtype", stringBuilder2.toString());
        }
        return hashMap;
    }

    public static Map<String, String> m3159a(Context context) {
        CharSequence deviceId;
        CharSequence c;
        CharSequence string;
        Map<String, String> hashMap = new HashMap();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            C0939b.m3145e(C0919a.f3107d, "No IMEI.");
        }
        try {
            if (C0943f.m3160a(context, "android.permission.READ_PHONE_STATE")) {
                deviceId = telephonyManager.getDeviceId();
                c = C0943f.m3164c(context);
                string = Secure.getString(context.getContentResolver(), "android_id");
                if (!TextUtils.isEmpty(c)) {
                    hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, c);
                }
                if (!TextUtils.isEmpty(deviceId)) {
                    hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, deviceId);
                }
                if (!TextUtils.isEmpty(string)) {
                    hashMap.put("android_id", string);
                }
                return hashMap;
            }
        } catch (Exception e) {
            C0939b.m3146e(C0919a.f3107d, "No IMEI.", e);
        }
        deviceId = null;
        c = C0943f.m3164c(context);
        string = Secure.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(c)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, c);
        }
        if (TextUtils.isEmpty(deviceId)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, deviceId);
        }
        if (TextUtils.isEmpty(string)) {
            hashMap.put("android_id", string);
        }
        return hashMap;
    }

    private static boolean m3160a(Context context, String str) {
        if (context.getPackageManager().checkPermission(str, context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    private static String m3164c(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (C0943f.m3160a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            C0939b.m3145e(C0919a.f3107d, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return "";
        } catch (Exception e) {
            C0939b.m3145e(C0919a.f3107d, "Could not get mac address." + e.toString());
        }
    }
}
