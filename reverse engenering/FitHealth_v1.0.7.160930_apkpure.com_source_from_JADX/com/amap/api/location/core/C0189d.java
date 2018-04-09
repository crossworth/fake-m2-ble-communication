package com.amap.api.location.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.district.DistrictSearchQuery;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CoreUtil */
public class C0189d {
    static int f108a = 2048;
    static String f109b = null;
    private static SharedPreferences f110c = null;
    private static Editor f111d = null;
    private static Method f112e;

    public static boolean m108a(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return false;
            }
            State state = activeNetworkInfo.getState();
            if (state == null || state == State.DISCONNECTED || state == State.DISCONNECTING) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static AMapLocation m109b(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("last_know_location", 0);
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setProvider(LocationProviderProxy.AMapNetwork);
            double parseDouble = Double.parseDouble(sharedPreferences.getString("last_know_lat", "0.0"));
            double parseDouble2 = Double.parseDouble(sharedPreferences.getString("last_know_lng", "0.0"));
            aMapLocation.setLatitude(parseDouble);
            aMapLocation.setLongitude(parseDouble2);
            aMapLocation.setProvince(sharedPreferences.getString(DistrictSearchQuery.KEYWORDS_PROVINCE, ""));
            aMapLocation.setCity(sharedPreferences.getString(DistrictSearchQuery.KEYWORDS_CITY, ""));
            aMapLocation.setDistrict(sharedPreferences.getString(DistrictSearchQuery.KEYWORDS_DISTRICT, ""));
            aMapLocation.setCityCode(sharedPreferences.getString("cityCode", ""));
            aMapLocation.setAdCode(sharedPreferences.getString("adCode", ""));
            aMapLocation.setAccuracy(sharedPreferences.getFloat(DataBaseContants.ACCURACY, 0.0f));
            aMapLocation.setTime(sharedPreferences.getLong(LogColumns.TIME, 0));
            return aMapLocation;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static void m105a(Context context, AMapLocation aMapLocation) {
        try {
            if (f110c == null) {
                f110c = context.getSharedPreferences("last_know_location", 0);
            }
            if (f111d == null) {
                f111d = f110c.edit();
            }
            f111d.putString("last_know_lat", String.valueOf(aMapLocation.getLatitude()));
            f111d.putString("last_know_lng", String.valueOf(aMapLocation.getLongitude()));
            f111d.putString(DistrictSearchQuery.KEYWORDS_PROVINCE, aMapLocation.getProvince());
            f111d.putString(DistrictSearchQuery.KEYWORDS_CITY, aMapLocation.getCity());
            f111d.putString(DistrictSearchQuery.KEYWORDS_DISTRICT, aMapLocation.getDistrict());
            f111d.putString("cityCode", aMapLocation.getCityCode());
            f111d.putString("adCode", aMapLocation.getAdCode());
            f111d.putFloat(DataBaseContants.ACCURACY, aMapLocation.getAccuracy());
            f111d.putLong(LogColumns.TIME, aMapLocation.getTime());
            C0189d.m106a(f111d);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void m106a(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT >= 9) {
                try {
                    if (f112e == null) {
                        f112e = Editor.class.getDeclaredMethod("apply", new Class[0]);
                    }
                    f112e.invoke(editor, new Object[0]);
                    return;
                } catch (Throwable th) {
                    th.printStackTrace();
                    editor.commit();
                    return;
                }
            }
            editor.commit();
        }
    }

    public static String m103a() {
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            int length = valueOf.length();
            return valueOf.substring(0, length - 2) + "1" + valueOf.substring(length - 1);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String m104a(String str, String str2) {
        String str3 = null;
        try {
            if (f109b == null || f109b.length() == 0) {
                f109b = C0188c.m84a(null).m102i();
            }
            str3 = C0192g.m122a(f109b + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str3;
    }

    public static void m107a(String str) throws AMapLocException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status") && jSONObject.has("info")) {
                String string = jSONObject.getString("status");
                String string2 = jSONObject.getString("info");
                if (!string.equals("1") && string.equals("0")) {
                    if (string2.equals("INVALID_USER_KEY") || string2.equals("INSUFFICIENT_PRIVILEGES") || string2.equals("USERKEY_PLAT_NOMATCH") || string2.equals("INVALID_USER_SCODE")) {
                        throw new AMapLocException("key鉴权失败");
                    } else if (string2.equals("SERVICE_NOT_EXIST") || string2.equals("SERVICE_RESPONSE_ERROR") || string2.equals("OVER_QUOTA") || string2.equals("UNKNOWN_ERROR")) {
                        throw new AMapLocException("未知的错误");
                    } else if (string2.equals("INVALID_PARAMS")) {
                        throw new AMapLocException("无效的参数 - IllegalArgumentException");
                    }
                }
            }
        } catch (JSONException e) {
        }
    }
}
