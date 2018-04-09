package com.baidu.mapapi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.mapapi.common.AppTools;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.poi.DispathcPoiData;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.mapapi.utils.route.RouteParaOption.EBusStrategyType;
import com.baidu.p001a.p002a.p003a.C0293a;
import com.baidu.p001a.p002a.p003a.C0296b;
import com.baidu.platform.comapi.p017b.C0602a;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0586a {
    public static int f1826a = -1;
    static ServiceConnection f1827b = new C0588c();
    private static final String f1828c = C0586a.class.getName();
    private static C0296b f1829d;
    private static C0293a f1830e;
    private static int f1831f;
    private static String f1832g = null;
    private static String f1833h = null;
    private static String f1834i = null;
    private static List<DispathcPoiData> f1835j = new ArrayList();
    private static LatLng f1836k = null;
    private static LatLng f1837l = null;
    private static String f1838m = null;
    private static String f1839n = null;
    private static EBusStrategyType f1840o;
    private static String f1841p = null;
    private static String f1842q = null;
    private static LatLng f1843r = null;
    private static int f1844s = 0;
    private static boolean f1845t = false;
    private static boolean f1846u = false;
    private static Thread f1847v;

    public static String m1783a() {
        return AppTools.getBaiduMapToken();
    }

    public static void m1784a(int i, Context context) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                C0586a.m1803c(context, i);
                return;
            case 3:
                C0586a.m1802c(context);
                return;
            case 4:
                C0586a.m1805d(context);
                return;
            case 5:
                C0586a.m1807e(context);
                return;
            case 7:
                C0586a.m1808f(context);
                return;
            case 8:
                C0586a.m1810g(context);
                return;
            default:
                return;
        }
    }

    public static void m1785a(Context context) {
        if (f1846u) {
            context.unbindService(f1827b);
            f1846u = false;
        }
    }

    private static void m1786a(List<DispathcPoiData> list, Context context) {
        f1832g = context.getPackageName();
        f1833h = C0586a.m1795b(context);
        f1834i = "";
        if (f1835j != null) {
            f1835j.clear();
        }
        for (DispathcPoiData add : list) {
            f1835j.add(add);
        }
    }

    public static boolean m1787a(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                return C0586a.m1811g();
            case 3:
                return C0586a.m1812h();
            case 4:
                return C0586a.m1817m();
            case 5:
                return C0586a.m1814j();
            case 6:
                return C0586a.m1813i();
            case 7:
                return C0586a.m1815k();
            case 8:
                return C0586a.m1816l();
            default:
                return false;
        }
    }

    public static boolean m1788a(Context context, int i) {
        try {
            if (C0602a.m1863a(context)) {
                f1845t = false;
                switch (i) {
                    case 0:
                        f1826a = 0;
                        break;
                    case 1:
                        f1826a = 1;
                        break;
                    case 2:
                        f1826a = 2;
                        break;
                    case 3:
                        f1826a = 3;
                        break;
                    case 4:
                        f1826a = 4;
                        break;
                    case 5:
                        f1826a = 5;
                        break;
                    case 6:
                        f1826a = 6;
                        break;
                    case 7:
                        f1826a = 7;
                        break;
                    case 8:
                        f1826a = 8;
                        break;
                }
                if (f1829d == null || !f1846u) {
                    C0586a.m1796b(context, i);
                    return true;
                } else if (f1830e != null) {
                    f1845t = true;
                    return C0586a.m1787a(i);
                } else {
                    f1829d.mo1736a(new C0587b(i));
                    return true;
                }
            }
            Log.d(f1828c, "package sign verify failed");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean m1789a(NaviParaOption naviParaOption, Context context, int i) {
        C0586a.m1797b(naviParaOption, context, i);
        return C0586a.m1788a(context, i);
    }

    public static boolean m1790a(PoiParaOption poiParaOption, Context context, int i) {
        C0586a.m1798b(poiParaOption, context, i);
        return C0586a.m1788a(context, i);
    }

    public static boolean m1791a(RouteParaOption routeParaOption, Context context, int i) {
        C0586a.m1799b(routeParaOption, context, i);
        return C0586a.m1788a(context, i);
    }

    public static boolean m1792a(List<DispathcPoiData> list, Context context, int i) {
        C0586a.m1786a((List) list, context);
        return C0586a.m1788a(context, i);
    }

    public static String m1795b(Context context) {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (NameNotFoundException e) {
            }
        } catch (NameNotFoundException e2) {
            Object obj = applicationInfo;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    private static void m1796b(Context context, int i) {
        Intent intent = new Intent();
        String a = C0586a.m1783a();
        if (a != null) {
            intent.putExtra("api_token", a);
            intent.setAction("com.baidu.map.action.OPEN_SERVICE");
            intent.setPackage("com.baidu.BaiduMap");
            f1846u = context.bindService(intent, f1827b, 1);
            if (f1846u) {
                f1847v = new Thread(new C0590e(context, i));
                f1847v.setDaemon(true);
                f1847v.start();
                return;
            }
            Log.e("baidumapsdk", "bind service failed，call openapi");
            C0586a.m1784a(i, context);
        }
    }

    private static void m1797b(NaviParaOption naviParaOption, Context context, int i) {
        f1832g = context.getPackageName();
        f1838m = null;
        f1836k = null;
        f1839n = null;
        f1837l = null;
        if (naviParaOption.getStartPoint() != null) {
            f1836k = naviParaOption.getStartPoint();
        }
        if (naviParaOption.getEndPoint() != null) {
            f1837l = naviParaOption.getEndPoint();
        }
        if (naviParaOption.getStartName() != null) {
            f1838m = naviParaOption.getStartName();
        }
        if (naviParaOption.getEndName() != null) {
            f1839n = naviParaOption.getEndName();
        }
    }

    private static void m1798b(PoiParaOption poiParaOption, Context context, int i) {
        f1841p = null;
        f1842q = null;
        f1843r = null;
        f1844s = 0;
        f1832g = context.getPackageName();
        if (poiParaOption.getUid() != null) {
            f1841p = poiParaOption.getUid();
        }
        if (poiParaOption.getKey() != null) {
            f1842q = poiParaOption.getKey();
        }
        if (poiParaOption.getCenter() != null) {
            f1843r = poiParaOption.getCenter();
        }
        if (poiParaOption.getRadius() != 0) {
            f1844s = poiParaOption.getRadius();
        }
    }

    private static void m1799b(RouteParaOption routeParaOption, Context context, int i) {
        f1838m = null;
        f1836k = null;
        f1839n = null;
        f1837l = null;
        f1832g = context.getPackageName();
        if (routeParaOption.getStartPoint() != null) {
            f1836k = routeParaOption.getStartPoint();
        }
        if (routeParaOption.getEndPoint() != null) {
            f1837l = routeParaOption.getEndPoint();
        }
        if (routeParaOption.getStartName() != null) {
            f1838m = routeParaOption.getStartName();
        }
        if (routeParaOption.getEndName() != null) {
            f1839n = routeParaOption.getEndName();
        }
        if (routeParaOption.getBusStrategyType() != null) {
            f1840o = routeParaOption.getBusStrategyType();
        }
        switch (i) {
            case 0:
                f1831f = 0;
                return;
            case 1:
                f1831f = 1;
                return;
            case 2:
                f1831f = 2;
                return;
            default:
                return;
        }
    }

    private static void m1802c(Context context) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/place/detail?");
        stringBuilder.append("uid=").append(f1841p);
        stringBuilder.append("&show_type=").append("detail_page");
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void m1803c(Context context, int i) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        String[] strArr = new String[]{"driving", "transit", "walking"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/direction?");
        stringBuilder.append("origin=");
        if (!TextUtils.isEmpty(f1838m) && f1836k != null) {
            stringBuilder.append("name:").append(f1838m).append("|latlng:").append(f1836k.latitude).append(",").append(f1836k.longitude);
        } else if (!TextUtils.isEmpty(f1838m)) {
            stringBuilder.append(f1838m);
        } else if (f1836k != null) {
            stringBuilder.append(f1836k.latitude).append(",").append(f1836k.longitude);
        }
        stringBuilder.append("&destination=");
        if (!TextUtils.isEmpty(f1839n) && f1837l != null) {
            stringBuilder.append("name:").append(f1839n).append("|latlng:").append(f1837l.latitude).append(",").append(f1837l.longitude);
        } else if (!TextUtils.isEmpty(f1839n)) {
            stringBuilder.append(f1839n);
        } else if (f1837l != null) {
            stringBuilder.append(f1837l.latitude).append(",").append(f1837l.longitude);
        }
        stringBuilder.append("&mode=").append(strArr[i]);
        stringBuilder.append("&target=").append("1");
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void m1805d(Context context) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/nearbysearch?");
        stringBuilder.append("center=").append(f1843r.latitude).append(",").append(f1843r.longitude);
        stringBuilder.append("&query=").append(f1842q).append("&radius=").append(f1844s);
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void m1807e(Context context) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/navi?");
        stringBuilder.append("origin=").append(f1836k.latitude).append(",").append(f1836k.longitude);
        stringBuilder.append("&location=").append(f1837l.latitude).append(",").append(f1837l.longitude);
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void m1808f(Context context) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/walknavi?");
        stringBuilder.append("origin=").append(f1836k.latitude).append(",").append(f1836k.longitude);
        stringBuilder.append("&destination=").append(f1837l.latitude).append(",").append(f1837l.longitude);
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void m1810g(Context context) {
        if (f1847v != null) {
            f1847v.interrupt();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("baidumap://map/bikenavi?");
        stringBuilder.append("origin=").append(f1836k.latitude).append(",").append(f1836k.longitude);
        stringBuilder.append("&destination=").append(f1837l.latitude).append(",").append(f1837l.longitude);
        stringBuilder.append("&src=").append("sdk_[" + f1832g + "]");
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static boolean m1811g() {
        try {
            Log.d(f1828c, "callDispatchTakeOutRoute");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "route_search_page");
                Bundle bundle2 = new Bundle();
                bundle2.putInt("route_type", f1831f);
                bundle2.putInt("bus_strategy", f1840o.ordinal());
                bundle2.putInt("cross_city_bus_strategy", 5);
                if (f1836k != null) {
                    bundle2.putInt("start_type", 1);
                    bundle2.putInt("start_longitude", (int) CoordUtil.ll2mc(f1836k).getLongitudeE6());
                    bundle2.putInt("start_latitude", (int) CoordUtil.ll2mc(f1836k).getLatitudeE6());
                } else {
                    bundle2.putInt("start_type", 2);
                    bundle2.putInt("start_longitude", 0);
                    bundle2.putInt("start_latitude", 0);
                }
                if (f1838m != null) {
                    bundle2.putString("start_keyword", f1838m);
                } else {
                    bundle2.putString("start_keyword", "地图上的点");
                }
                bundle2.putString("start_uid", "");
                if (f1837l != null) {
                    bundle2.putInt("end_type", 1);
                    bundle2.putInt("end_longitude", (int) CoordUtil.ll2mc(f1837l).getLongitudeE6());
                    bundle2.putInt("end_latitude", (int) CoordUtil.ll2mc(f1837l).getLatitudeE6());
                } else {
                    bundle2.putInt("end_type", 2);
                    bundle2.putInt("end_longitude", 0);
                    bundle2.putInt("end_latitude", 0);
                }
                if (f1839n != null) {
                    bundle2.putString("end_keyword", f1839n);
                } else {
                    bundle2.putString("end_keyword", "地图上的点");
                }
                bundle2.putString("end_uid", "");
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
            return false;
        }
    }

    private static boolean m1812h() {
        try {
            Log.d(f1828c, "callDispatchTakeOutPoiDetials");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "request_poi_detail_page");
                Bundle bundle2 = new Bundle();
                if (f1841p != null) {
                    bundle2.putString("uid", f1841p);
                } else {
                    bundle2.putString("uid", "");
                }
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
        }
    }

    private static boolean m1813i() {
        int i;
        JSONException e;
        if (f1835j == null || f1835j.size() <= 0) {
            return false;
        }
        try {
            Log.d(f1828c, "callDispatchPoiToBaiduMap");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "favorite_page");
                Bundle bundle2 = new Bundle();
                JSONArray jSONArray = new JSONArray();
                int i2 = 0;
                int i3 = 0;
                while (i2 < f1835j.size()) {
                    if (((DispathcPoiData) f1835j.get(i2)).name == null || ((DispathcPoiData) f1835j.get(i2)).name.equals("")) {
                        i = i3;
                    } else if (((DispathcPoiData) f1835j.get(i2)).pt == null) {
                        i = i3;
                    } else {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("name", ((DispathcPoiData) f1835j.get(i2)).name);
                            GeoPoint ll2mc = CoordUtil.ll2mc(((DispathcPoiData) f1835j.get(i2)).pt);
                            jSONObject.put("ptx", ll2mc.getLongitudeE6());
                            jSONObject.put("pty", ll2mc.getLatitudeE6());
                            jSONObject.put("addr", ((DispathcPoiData) f1835j.get(i2)).addr);
                            jSONObject.put("uid", ((DispathcPoiData) f1835j.get(i2)).uid);
                            i = i3 + 1;
                            try {
                                jSONArray.put(jSONObject);
                            } catch (JSONException e2) {
                                e = e2;
                                e.printStackTrace();
                                i2++;
                                i3 = i;
                            }
                        } catch (JSONException e3) {
                            JSONException jSONException = e3;
                            i = i3;
                            e = jSONException;
                            e.printStackTrace();
                            i2++;
                            i3 = i;
                        }
                    }
                    i2++;
                    i3 = i;
                }
                if (i3 == 0) {
                    return false;
                }
                bundle2.putString("data", jSONArray.toString());
                bundle2.putString("from", f1833h);
                bundle2.putString("pkg", f1832g);
                bundle2.putString("cls", f1834i);
                bundle2.putInt(ParamKey.COUNT, i3);
                bundle.putBundle("base_params", bundle2);
                Bundle bundle3 = new Bundle();
                bundle3.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle3);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchPoiToBaiduMap com not found");
            return false;
        } catch (Throwable e4) {
            Log.d(f1828c, "callDispatchPoiToBaiduMap exception", e4);
        }
    }

    private static boolean m1814j() {
        try {
            Log.d(f1828c, "callDispatchTakeOutRouteNavi");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "navigation_page");
                Bundle bundle2 = new Bundle();
                bundle2.putString("coord_type", "bd09ll");
                bundle2.putString("type", "DIS");
                StringBuffer stringBuffer = new StringBuffer();
                if (f1838m != null) {
                    stringBuffer.append("name:" + f1838m + "|");
                }
                stringBuffer.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1836k.latitude), Double.valueOf(f1836k.longitude)}));
                StringBuffer stringBuffer2 = new StringBuffer();
                if (f1839n != null) {
                    stringBuffer2.append("name:" + f1839n + "|");
                }
                stringBuffer2.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1837l.latitude), Double.valueOf(f1837l.longitude)}));
                bundle2.putString("origin", stringBuffer.toString());
                bundle2.putString("destination", stringBuffer2.toString());
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
            return false;
        }
    }

    private static boolean m1815k() {
        try {
            Log.d(f1828c, "callDispatchTakeOutRouteNavi");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "walknavi_page");
                Bundle bundle2 = new Bundle();
                bundle2.putString("coord_type", "bd09ll");
                StringBuffer stringBuffer = new StringBuffer();
                if (f1838m != null) {
                    stringBuffer.append("name:" + f1838m + "|");
                }
                stringBuffer.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1836k.latitude), Double.valueOf(f1836k.longitude)}));
                StringBuffer stringBuffer2 = new StringBuffer();
                if (f1839n != null) {
                    stringBuffer2.append("name:" + f1839n + "|");
                }
                stringBuffer2.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1837l.latitude), Double.valueOf(f1837l.longitude)}));
                bundle2.putString("origin", stringBuffer.toString());
                bundle2.putString("destination", stringBuffer2.toString());
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
            return false;
        }
    }

    private static boolean m1816l() {
        try {
            Log.d(f1828c, "callDispatchTakeOutRouteRidingNavi");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "bikenavi_page");
                Bundle bundle2 = new Bundle();
                bundle2.putString("coord_type", "bd09ll");
                StringBuffer stringBuffer = new StringBuffer();
                if (f1838m != null) {
                    stringBuffer.append("name:" + f1838m + "|");
                }
                stringBuffer.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1836k.latitude), Double.valueOf(f1836k.longitude)}));
                StringBuffer stringBuffer2 = new StringBuffer();
                if (f1839n != null) {
                    stringBuffer2.append("name:" + f1839n + "|");
                }
                stringBuffer2.append(String.format("latlng:%f,%f", new Object[]{Double.valueOf(f1837l.latitude), Double.valueOf(f1837l.longitude)}));
                bundle2.putString("origin", stringBuffer.toString());
                bundle2.putString("destination", stringBuffer2.toString());
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
            return false;
        }
    }

    private static boolean m1817m() {
        try {
            Log.d(f1828c, "callDispatchTakeOutPoiNearbySearch");
            String a = f1830e.mo1734a("map.android.baidu.mainmap");
            if (a != null) {
                Bundle bundle = new Bundle();
                bundle.putString("target", "poi_search_page");
                Bundle bundle2 = new Bundle();
                if (f1842q != null) {
                    bundle2.putString("search_key", f1842q);
                } else {
                    bundle2.putString("search_key", "");
                }
                if (f1843r != null) {
                    bundle2.putInt("center_pt_x", (int) CoordUtil.ll2mc(f1843r).getLongitudeE6());
                    bundle2.putInt("center_pt_y", (int) CoordUtil.ll2mc(f1843r).getLatitudeE6());
                } else {
                    bundle2.putString("search_key", "");
                }
                if (f1844s != 0) {
                    bundle2.putInt("search_radius", f1844s);
                } else {
                    bundle2.putInt("search_radius", 1000);
                }
                bundle2.putBoolean("is_direct_search", true);
                bundle2.putBoolean("is_direct_area_search", true);
                bundle.putBundle("base_params", bundle2);
                bundle2 = new Bundle();
                bundle2.putString("launch_from", "sdk_[" + f1832g + "]");
                bundle.putBundle("ext_params", bundle2);
                return f1830e.mo1735a("map.android.baidu.mainmap", a, bundle);
            }
            Log.d(f1828c, "callDispatchTakeOut com not found");
            return false;
        } catch (Throwable e) {
            Log.d(f1828c, "callDispatchTakeOut exception", e);
        }
    }
}
