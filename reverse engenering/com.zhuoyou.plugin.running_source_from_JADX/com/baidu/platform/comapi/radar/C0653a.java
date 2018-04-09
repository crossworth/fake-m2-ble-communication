package com.baidu.platform.comapi.radar;

import android.os.Bundle;
import android.os.Handler;
import com.baidu.mapapi.MessageCenter;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comjni.map.radar.C0678a;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;

public class C0653a {
    private static C0655d f2135b = null;
    private static Handler f2136c = null;
    private static C0653a f2137d = null;
    private C0678a f2138a = null;

    private C0653a() {
        f2135b = new C0655d();
        f2136c = new C0654b(this);
        MessageCenter.registMessage(m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT, f2136c);
        f2135b.m2099a(this);
    }

    public static C0653a m2087a() {
        if (f2137d == null) {
            synchronized (C0653a.class) {
                if (f2137d == null) {
                    f2137d = new C0653a();
                    f2137d.m2089f();
                }
            }
        }
        return f2137d;
    }

    private boolean m2089f() {
        if (this.f2138a != null) {
            return true;
        }
        this.f2138a = new C0678a();
        if (this.f2138a.m2274a() != 0) {
            return true;
        }
        this.f2138a = null;
        return false;
    }

    public void m2090a(C0514c c0514c) {
        f2135b.m2100a(c0514c);
    }

    public boolean m2091a(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("user_id", str);
        return this.f2138a.m2278b(bundle);
    }

    public boolean m2092a(String str, LatLng latLng, int i, int i2, int i3, int i4, int i5, String str2) {
        if (str == null || str.equals("") || latLng == null || i <= 0) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("user_id", str);
        bundle.putDouble("locx", latLng.longitude);
        bundle.putDouble("locy", latLng.latitude);
        bundle.putInt("radius", i);
        bundle.putInt("pagenum", i2);
        bundle.putInt(ParamKey.COUNT, i3);
        bundle.putInt("sortby", i4);
        bundle.putInt("sortrule", i5);
        bundle.putString("time_interval", str2);
        return this.f2138a.m2279c(bundle);
    }

    public boolean m2093a(String str, LatLng latLng, String str2) {
        if (str == null || str.equals("") || latLng == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("user_id", str);
        bundle.putDouble("locx", latLng.longitude);
        bundle.putDouble("locy", latLng.latitude);
        bundle.putString("comments", str2);
        return this.f2138a.m2276a(bundle);
    }

    public void m2094b() {
        f2135b.m2097a();
    }

    public String m2095c() {
        return this.f2138a.m2275a(30002);
    }

    public void m2096d() {
        if (f2137d != null) {
            MessageCenter.unregistMessage(m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT, f2136c);
            if (f2137d.f2138a != null) {
                f2137d.f2138a.m2277b();
                f2137d.f2138a = null;
                f2135b.m2101b();
                f2135b = null;
            }
            f2137d = null;
        }
    }
}
