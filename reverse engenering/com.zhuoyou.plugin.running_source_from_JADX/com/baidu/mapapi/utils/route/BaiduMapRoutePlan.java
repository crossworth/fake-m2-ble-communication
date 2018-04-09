package com.baidu.mapapi.utils.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.baidu.mapapi.navi.IllegalNaviArgumentException;
import com.baidu.mapapi.utils.C0586a;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.IllegalPoiSearchArgumentException;
import com.baidu.mapapi.utils.route.RouteParaOption.EBusStrategyType;

public class BaiduMapRoutePlan {
    private static boolean f1860a = true;

    private static void m1829a(RouteParaOption routeParaOption, Context context, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.map.baidu.com/direction?");
        stringBuilder.append("origin=");
        if (routeParaOption.f1862a != null && routeParaOption.f1864c != null && !routeParaOption.f1864c.equals("")) {
            stringBuilder.append("latlng:");
            stringBuilder.append(routeParaOption.f1862a.latitude);
            stringBuilder.append(",");
            stringBuilder.append(routeParaOption.f1862a.longitude);
            stringBuilder.append("|");
            stringBuilder.append("name:");
            stringBuilder.append(routeParaOption.f1864c);
        } else if (routeParaOption.f1862a != null) {
            stringBuilder.append(routeParaOption.f1862a.latitude);
            stringBuilder.append(",");
            stringBuilder.append(routeParaOption.f1862a.longitude);
        } else {
            stringBuilder.append(routeParaOption.f1864c);
        }
        stringBuilder.append("&destination=");
        if (routeParaOption.f1863b != null && routeParaOption.f1865d != null && !routeParaOption.f1865d.equals("")) {
            stringBuilder.append("latlng:");
            stringBuilder.append(routeParaOption.f1863b.latitude);
            stringBuilder.append(",");
            stringBuilder.append(routeParaOption.f1863b.longitude);
            stringBuilder.append("|");
            stringBuilder.append("name:");
            stringBuilder.append(routeParaOption.f1865d);
        } else if (routeParaOption.f1863b != null) {
            stringBuilder.append(routeParaOption.f1863b.latitude);
            stringBuilder.append(",");
            stringBuilder.append(routeParaOption.f1863b.longitude);
        } else {
            stringBuilder.append(routeParaOption.f1865d);
        }
        String str = "";
        switch (i) {
            case 0:
                str = "driving";
                break;
            case 1:
                str = "transit";
                break;
            case 2:
                str = "walking";
                break;
        }
        stringBuilder.append("&mode=");
        stringBuilder.append(str);
        stringBuilder.append("&region=");
        if (routeParaOption.getCityName() == null || routeParaOption.getCityName().equals("")) {
            stringBuilder.append("全国");
        } else {
            stringBuilder.append(routeParaOption.getCityName());
        }
        stringBuilder.append("&output=html");
        stringBuilder.append("&src=");
        stringBuilder.append(context.getPackageName());
        Uri parse = Uri.parse(stringBuilder.toString());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setFlags(268435456);
        intent.setData(parse);
        context.startActivity(intent);
    }

    public static void finish(Context context) {
        if (context != null) {
            C0586a.m1785a(context);
        }
    }

    public static boolean openBaiduMapDrivingRoute(RouteParaOption routeParaOption, Context context) {
        if (routeParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (routeParaOption.f1863b == null && routeParaOption.f1862a == null && routeParaOption.f1865d == null && routeParaOption.f1864c == null) {
            throw new IllegalNaviArgumentException("startPoint and endPoint and endName and startName not all null.");
        } else if (routeParaOption.f1864c == null && routeParaOption.f1862a == null) {
            throw new IllegalNaviArgumentException("startPoint and startName not all null.");
        } else if (routeParaOption.f1865d == null && routeParaOption.f1863b == null) {
            throw new IllegalNaviArgumentException("endPoint and endName not all null.");
        } else if (((routeParaOption.f1864c == null || routeParaOption.f1864c.equals("")) && routeParaOption.f1862a == null) || ((routeParaOption.f1865d == null || routeParaOption.f1865d.equals("")) && routeParaOption.f1863b == null)) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi startName or endName can not be empty string while pt is null");
            return false;
        } else {
            if (routeParaOption.f1867f == null) {
                routeParaOption.f1867f = EBusStrategyType.bus_recommend_way;
            }
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1860a) {
                    m1829a(routeParaOption, context, 0);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return C0586a.m1791a(routeParaOption, context, 0);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (f1860a) {
                    m1829a(routeParaOption, context, 0);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static boolean openBaiduMapTransitRoute(RouteParaOption routeParaOption, Context context) {
        if (routeParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (routeParaOption.f1863b == null && routeParaOption.f1862a == null && routeParaOption.f1865d == null && routeParaOption.f1864c == null) {
            throw new IllegalNaviArgumentException("startPoint and endPoint and endName and startName not all null.");
        } else if (routeParaOption.f1864c == null && routeParaOption.f1862a == null) {
            throw new IllegalNaviArgumentException("startPoint and startName not all null.");
        } else if (routeParaOption.f1865d == null && routeParaOption.f1863b == null) {
            throw new IllegalNaviArgumentException("endPoint and endName not all null.");
        } else if (((routeParaOption.f1864c == null || routeParaOption.f1864c.equals("")) && routeParaOption.f1862a == null) || ((routeParaOption.f1865d == null || routeParaOption.f1865d.equals("")) && routeParaOption.f1863b == null)) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi startName or endName can not be empty string while pt is null");
            return false;
        } else {
            if (routeParaOption.f1867f == null) {
                routeParaOption.f1867f = EBusStrategyType.bus_recommend_way;
            }
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1860a) {
                    m1829a(routeParaOption, context, 1);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return C0586a.m1791a(routeParaOption, context, 1);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (f1860a) {
                    m1829a(routeParaOption, context, 1);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static boolean openBaiduMapWalkingRoute(RouteParaOption routeParaOption, Context context) {
        if (routeParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (routeParaOption.f1863b == null && routeParaOption.f1862a == null && routeParaOption.f1865d == null && routeParaOption.f1864c == null) {
            throw new IllegalNaviArgumentException("startPoint and endPoint and endName and startName not all null.");
        } else if (routeParaOption.f1864c == null && routeParaOption.f1862a == null) {
            throw new IllegalNaviArgumentException("startPoint and startName not all null.");
        } else if (routeParaOption.f1865d == null && routeParaOption.f1863b == null) {
            throw new IllegalNaviArgumentException("endPoint and endName not all null.");
        } else if (((routeParaOption.f1864c == null || routeParaOption.f1864c.equals("")) && routeParaOption.f1862a == null) || ((routeParaOption.f1865d == null || routeParaOption.f1865d.equals("")) && routeParaOption.f1863b == null)) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi startName or endName can not be empty string while pt is null");
            return false;
        } else {
            if (routeParaOption.f1867f == null) {
                routeParaOption.f1867f = EBusStrategyType.bus_recommend_way;
            }
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1860a) {
                    m1829a(routeParaOption, context, 2);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return C0586a.m1791a(routeParaOption, context, 2);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (f1860a) {
                    m1829a(routeParaOption, context, 2);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static void setSupportWebRoute(boolean z) {
        f1860a = z;
    }
}
