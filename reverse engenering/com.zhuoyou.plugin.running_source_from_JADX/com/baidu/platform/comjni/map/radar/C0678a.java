package com.baidu.platform.comjni.map.radar;

import android.os.Bundle;

public class C0678a {
    private long f2241a;
    private JNIRadar f2242b;

    public C0678a() {
        this.f2241a = 0;
        this.f2242b = null;
        this.f2242b = new JNIRadar();
    }

    public long m2274a() {
        this.f2241a = this.f2242b.Create();
        return this.f2241a;
    }

    public String m2275a(int i) {
        return this.f2242b.GetRadarResult(this.f2241a, i);
    }

    public boolean m2276a(Bundle bundle) {
        return this.f2242b.SendUploadLocationInfoRequest(this.f2241a, bundle);
    }

    public int m2277b() {
        return this.f2242b.Release(this.f2241a);
    }

    public boolean m2278b(Bundle bundle) {
        return this.f2242b.SendClearLocationInfoRequest(this.f2241a, bundle);
    }

    public boolean m2279c(Bundle bundle) {
        return this.f2242b.SendGetLocationInfosNearbyRequest(this.f2241a, bundle);
    }
}
