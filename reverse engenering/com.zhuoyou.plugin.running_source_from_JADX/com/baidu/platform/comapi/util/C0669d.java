package com.baidu.platform.comapi.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public final class C0669d {
    private final boolean f2182a;
    private final String f2183b;
    private final String f2184c;
    private final String f2185d;
    private final String f2186e;
    private final String f2187f;

    C0669d(Context context) {
        this.f2182a = false;
        this.f2183b = Environment.getExternalStorageDirectory().getAbsolutePath();
        this.f2184c = this.f2183b + File.separator + "BaiduMapSDKNew";
        this.f2185d = context.getCacheDir().getAbsolutePath();
        this.f2186e = "";
        this.f2187f = "";
    }

    C0669d(String str, boolean z, String str2, Context context) {
        this.f2182a = z;
        this.f2183b = str;
        this.f2184c = this.f2183b + File.separator + "BaiduMapSDKNew";
        this.f2185d = this.f2184c + File.separator + "cache";
        this.f2186e = context.getCacheDir().getAbsolutePath();
        this.f2187f = str2;
    }

    public String m2154a() {
        return this.f2183b;
    }

    public String m2155b() {
        return this.f2183b + File.separator + "BaiduMapSDKNew";
    }

    public String m2156c() {
        return this.f2185d;
    }

    public String m2157d() {
        return this.f2186e;
    }

    public boolean equals(Object obj) {
        if (obj == null || !C0669d.class.isInstance(obj)) {
            return false;
        }
        return this.f2183b.equals(((C0669d) obj).f2183b);
    }
}
