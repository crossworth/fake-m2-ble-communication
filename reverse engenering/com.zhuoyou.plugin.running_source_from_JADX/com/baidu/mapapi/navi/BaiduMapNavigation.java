package com.baidu.mapapi.navi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.p014a.p015a.C0469a;
import com.baidu.mapapi.utils.C0586a;
import com.baidu.mapapi.utils.OpenClientUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaiduMapNavigation extends C0469a {
    private static boolean f1471a = true;

    private static String m1377a(Context context) {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (NameNotFoundException e) {
            }
        } catch (NameNotFoundException e2) {
            Object obj = applicationInfo;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    private static void m1378a(NaviParaOption naviParaOption, Context context) throws IllegalNaviArgumentException {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.f1472a == null || naviParaOption.f1474c == null) {
            throw new IllegalNaviArgumentException("you must set start and end point.");
        } else {
            GeoPoint ll2mc = CoordUtil.ll2mc(naviParaOption.f1472a);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(naviParaOption.f1474c);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://app.navi.baidu.com/mobile/#navi/naving/");
            stringBuilder.append("&sy=0");
            stringBuilder.append("&endp=");
            stringBuilder.append("&start=");
            stringBuilder.append("&startwd=");
            stringBuilder.append("&endwd=");
            stringBuilder.append("&fromprod=map_sdk");
            stringBuilder.append("&app_version=");
            stringBuilder.append("4_1_1");
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("type", "1");
            if (naviParaOption.f1473b == null || naviParaOption.f1473b.equals("")) {
                try {
                    jSONObject.put("keyword", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                jSONObject.put("keyword", naviParaOption.f1473b);
            }
            jSONObject.put("xy", String.valueOf(ll2mc.getLongitudeE6()) + "," + String.valueOf(ll2mc.getLatitudeE6()));
            jSONArray.put(jSONObject);
            jSONObject2.put("type", "1");
            if (naviParaOption.f1475d == null || naviParaOption.f1475d.equals("")) {
                jSONObject.put("keyword", "");
            } else {
                jSONObject.put("keyword", naviParaOption.f1475d);
            }
            jSONObject2.put("xy", String.valueOf(ll2mc2.getLongitudeE6()) + "," + String.valueOf(ll2mc2.getLatitudeE6()));
            jSONArray.put(jSONObject2);
            if (jSONArray.length() > 0) {
                stringBuilder.append("&positions=");
                stringBuilder.append(jSONArray.toString());
            }
            stringBuilder.append("&ctrl_type=");
            stringBuilder.append("&mrsl=");
            stringBuilder.append("/vt=map&state=entry");
            Uri parse = Uri.parse(stringBuilder.toString());
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setData(parse);
            context.startActivity(intent);
        }
    }

    public static void finish(Context context) {
        if (context != null) {
            C0586a.m1785a(context);
        }
    }

    public static boolean openBaiduMapBikeNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.f1474c == null || naviParaOption.f1472a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                return false;
            } else if (baiduMapVersion >= 869) {
                return C0586a.m1789a(naviParaOption, context, 8);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.6.6");
                return false;
            }
        }
    }

    public static boolean openBaiduMapNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.f1474c == null || naviParaOption.f1472a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1471a) {
                    m1378a(naviParaOption, context);
                    return true;
                }
                throw new BaiduMapAppNotSupportNaviException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 830) {
                return C0586a.m1789a(naviParaOption, context, 5);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.2");
                if (f1471a) {
                    m1378a(naviParaOption, context);
                    return true;
                }
                throw new BaiduMapAppNotSupportNaviException("Baidumap app version is too lowl.Version is greater than 8.2");
            }
        }
    }

    public static boolean openBaiduMapWalkNavi(NaviParaOption naviParaOption, Context context) {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.f1474c == null || naviParaOption.f1472a == null) {
            throw new IllegalNaviArgumentException("start point or end point can not be null.");
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                return false;
            } else if (baiduMapVersion >= 869) {
                return C0586a.m1789a(naviParaOption, context, 7);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.6.6");
                return false;
            }
        }
    }

    @Deprecated
    public static void openWebBaiduMapNavi(NaviParaOption naviParaOption, Context context) throws IllegalNaviArgumentException {
        if (naviParaOption == null || context == null) {
            throw new IllegalNaviArgumentException("para or context can not be null.");
        } else if (naviParaOption.f1472a != null && naviParaOption.f1474c != null) {
            GeoPoint ll2mc = CoordUtil.ll2mc(naviParaOption.f1472a);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(naviParaOption.f1474c);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://daohang.map.baidu.com/mobile/#navi/naving/start=");
            stringBuilder.append(ll2mc.getLongitudeE6());
            stringBuilder.append(",");
            stringBuilder.append(ll2mc.getLatitudeE6());
            stringBuilder.append("&endp=");
            stringBuilder.append(ll2mc2.getLongitudeE6());
            stringBuilder.append(",");
            stringBuilder.append(ll2mc2.getLatitudeE6());
            stringBuilder.append("&fromprod=");
            stringBuilder.append(m1377a(context));
            stringBuilder.append("/vt=map&state=entry");
            r0 = Uri.parse(stringBuilder.toString());
            r1 = new Intent();
            r1.setAction("android.intent.action.VIEW");
            r1.setFlags(268435456);
            r1.setData(r0);
            context.startActivity(r1);
        } else if (naviParaOption.f1473b == null || naviParaOption.f1473b.equals("") || naviParaOption.f1475d == null || naviParaOption.f1475d.equals("")) {
            throw new IllegalNaviArgumentException("you must set start and end point or set the start and end name.");
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("http://daohang.map.baidu.com/mobile/#search/search/qt=nav&sn=2$$$$$$");
            stringBuilder2.append(naviParaOption.f1473b);
            stringBuilder2.append("$$$$$$&en=2$$$$$$");
            stringBuilder2.append(naviParaOption.f1475d);
            stringBuilder2.append("$$$$$$&fromprod=");
            stringBuilder2.append(m1377a(context));
            r0 = Uri.parse(stringBuilder2.toString());
            r1 = new Intent();
            r1.setAction("android.intent.action.VIEW");
            r1.setData(r0);
            context.startActivity(r1);
        }
    }

    public static void setSupportWebNavi(boolean z) {
        f1471a = z;
    }
}
