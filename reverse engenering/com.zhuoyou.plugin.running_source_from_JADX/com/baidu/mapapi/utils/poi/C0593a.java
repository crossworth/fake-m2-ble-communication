package com.baidu.mapapi.utils.poi;

import android.content.Context;
import android.util.Log;
import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch.C05911;
import com.baidu.platform.comapi.p016a.C0596a.C0592a;
import com.baidu.platform.comapi.p016a.C0598c;

final class C0593a implements C0592a<C0598c> {
    final /* synthetic */ Context f1859a;

    C0593a(Context context) {
        this.f1859a = context;
    }

    public void mo1826a(HttpStateError httpStateError) {
        switch (C05911.f1853b[httpStateError.ordinal()]) {
            case 1:
                Log.d("baidumapsdk", "current network is not available");
                return;
            case 2:
                Log.d("baidumapsdk", "network inner error, please check network");
                return;
            default:
                return;
        }
    }

    public void m1827a(C0598c c0598c) {
        if (c0598c == null) {
            Log.d("baidumapsdk", "pano info is null");
            return;
        }
        switch (C05911.f1852a[c0598c.m1847a().ordinal()]) {
            case 1:
                Log.d("baidumapsdk", "pano uid is error, please check param poi uid");
                return;
            case 2:
                Log.d("baidumapsdk", "pano id not found for this poi point");
                return;
            case 3:
                Log.d("baidumapsdk", "please check ak for permission");
                return;
            case 4:
                if (c0598c.m1851c() == 1) {
                    try {
                        BaiduMapPoiSearch.m1823b(c0598c.m1850b(), this.f1859a);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                Log.d("baidumapsdk", "this point do not support for pano show");
                return;
            default:
                return;
        }
    }
}
