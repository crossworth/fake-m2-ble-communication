package com.baidu.platform.comjni.map.favorite;

import android.os.Bundle;

public class C0677a {
    private long f2239a;
    private JNIFavorite f2240b;

    public static class C0676a {
        public static boolean f2238a = false;

        private static void m2261b() {
            f2238a = true;
        }
    }

    public C0677a() {
        this.f2239a = 0;
        this.f2240b = null;
        this.f2240b = new JNIFavorite();
    }

    public int m2262a(Bundle bundle) {
        try {
            return this.f2240b.GetAll(this.f2239a, bundle);
        } catch (Throwable th) {
            return 0;
        }
    }

    public long m2263a() {
        this.f2239a = this.f2240b.Create();
        return this.f2239a;
    }

    public boolean m2264a(int i) {
        return this.f2240b.SetType(this.f2239a, i);
    }

    public boolean m2265a(String str) {
        return this.f2240b.Remove(this.f2239a, str);
    }

    public boolean m2266a(String str, String str2) {
        C0676a.m2261b();
        return this.f2240b.Add(this.f2239a, str, str2);
    }

    public boolean m2267a(String str, String str2, String str3, int i, int i2, int i3) {
        return this.f2240b.Load(this.f2239a, str, str2, str3, i, i2, i3);
    }

    public int m2268b() {
        return this.f2240b.Release(this.f2239a);
    }

    public String m2269b(String str) {
        try {
            return this.f2240b.GetValue(this.f2239a, str);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean m2270b(String str, String str2) {
        C0676a.m2261b();
        return this.f2240b.Update(this.f2239a, str, str2);
    }

    public boolean m2271c() {
        return this.f2240b.Clear(this.f2239a);
    }

    public boolean m2272c(String str) {
        try {
            return this.f2240b.IsExist(this.f2239a, str);
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean m2273d() {
        return this.f2240b.SaveCache(this.f2239a);
    }
}
