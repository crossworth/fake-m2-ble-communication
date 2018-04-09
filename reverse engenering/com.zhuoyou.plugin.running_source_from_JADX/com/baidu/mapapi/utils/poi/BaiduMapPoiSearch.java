package com.baidu.mapapi.utils.poi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.utils.C0586a;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.platform.comapi.p016a.C0596a;
import com.baidu.platform.comapi.p016a.C0599d;
import java.util.List;

public class BaiduMapPoiSearch {
    private static boolean f1854a = true;

    static /* synthetic */ class C05911 {
        static final /* synthetic */ int[] f1852a = new int[C0599d.values().length];
        static final /* synthetic */ int[] f1853b = new int[HttpStateError.values().length];

        static {
            try {
                f1853b[HttpStateError.NETWORK_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1853b[HttpStateError.INNER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1852a[C0599d.PANO_UID_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1852a[C0599d.PANO_NOT_FOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1852a[C0599d.PANO_NO_TOKEN.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1852a[C0599d.PANO_NO_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private static void m1820a(PoiParaOption poiParaOption, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.map.baidu.com/place/detail?");
        stringBuilder.append("uid=");
        stringBuilder.append(poiParaOption.f1855a);
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

    private static void m1822b(PoiParaOption poiParaOption, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.map.baidu.com/place/search?");
        stringBuilder.append("query=");
        stringBuilder.append(poiParaOption.f1856b);
        stringBuilder.append("&location=");
        stringBuilder.append(poiParaOption.f1857c.latitude);
        stringBuilder.append(",");
        stringBuilder.append(poiParaOption.f1857c.longitude);
        stringBuilder.append("&radius=");
        stringBuilder.append(poiParaOption.f1858d);
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

    private static void m1823b(String str, Context context) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("pano id can not be null.");
        } else if (context == null) {
            throw new RuntimeException("context cannot be null.");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("baidumap://map/streetscape?");
            stringBuilder.append("panoid=").append(str);
            stringBuilder.append("&pid=").append(str);
            stringBuilder.append("&panotype=").append("street");
            stringBuilder.append("&src=").append("sdk_[" + context.getPackageName() + "]");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
            intent.setFlags(268435456);
            if (intent == null || intent.resolveActivity(context.getPackageManager()) == null) {
                throw new RuntimeException("BaiduMap app is not installed.");
            }
            context.startActivity(intent);
        }
    }

    public static boolean dispatchPoiToBaiduMap(List<DispathcPoiData> list, Context context) throws Exception {
        if (list.isEmpty() || list.size() <= 0) {
            throw new NullPointerException("dispatch poidata is null");
        }
        int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
        if (baiduMapVersion == 0) {
            Log.e("baidumapsdk", "BaiduMap app is not installed.");
        } else if (baiduMapVersion >= 840) {
            return C0586a.m1792a((List) list, context, 6);
        } else {
            Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.4");
        }
        return false;
    }

    public static void finish(Context context) {
        if (context != null) {
            C0586a.m1785a(context);
        }
    }

    public static void openBaiduMapPanoShow(String str, Context context) {
        new C0596a(context).m1846a(str, new C0593a(context));
    }

    public static boolean openBaiduMapPoiDetialsPage(PoiParaOption poiParaOption, Context context) {
        if (poiParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (poiParaOption.f1855a == null) {
            throw new IllegalPoiSearchArgumentException("poi uid can not be null.");
        } else if (poiParaOption.f1855a.equals("")) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi uid can not be empty string");
            return false;
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1854a) {
                    m1820a(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return C0586a.m1790a(poiParaOption, context, 3);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (f1854a) {
                    m1820a(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static boolean openBaiduMapPoiNearbySearch(PoiParaOption poiParaOption, Context context) {
        if (poiParaOption == null || context == null) {
            throw new IllegalPoiSearchArgumentException("para or context can not be null.");
        } else if (poiParaOption.f1856b == null) {
            throw new IllegalPoiSearchArgumentException("poi search key can not be null.");
        } else if (poiParaOption.f1857c == null) {
            throw new IllegalPoiSearchArgumentException("poi search center can not be null.");
        } else if (poiParaOption.f1857c.longitude == 0.0d || poiParaOption.f1857c.latitude == 0.0d) {
            throw new IllegalPoiSearchArgumentException("poi search center longitude or latitude can not be 0.");
        } else if (poiParaOption.f1858d == 0) {
            throw new IllegalPoiSearchArgumentException("poi search radius larger than 0.");
        } else if (poiParaOption.f1856b.equals("")) {
            Log.e(BaiduMapRoutePlan.class.getName(), "poi key can not be empty string");
            return false;
        } else {
            int baiduMapVersion = OpenClientUtil.getBaiduMapVersion(context);
            if (baiduMapVersion == 0) {
                Log.e("baidumapsdk", "BaiduMap app is not installed.");
                if (f1854a) {
                    m1822b(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("BaiduMap app is not installed.");
            } else if (baiduMapVersion >= 810) {
                return C0586a.m1790a(poiParaOption, context, 4);
            } else {
                Log.e("baidumapsdk", "Baidumap app version is too lowl.Version is greater than 8.1");
                if (f1854a) {
                    m1822b(poiParaOption, context);
                    return true;
                }
                throw new IllegalPoiSearchArgumentException("Baidumap app version is too lowl.Version is greater than 8.1");
            }
        }
    }

    public static void setSupportWebPoi(boolean z) {
        f1854a = z;
    }
}
