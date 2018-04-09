package com.umeng.analytics.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.Serializable;
import p031u.aly.am;
import p031u.aly.ap;

/* compiled from: GameState */
public class C0927b {
    public String f3141a;
    public String f3142b;
    private Context f3143c;
    private final String f3144d = "um_g_cache";
    private final String f3145e = "single_level";
    private final String f3146f = "stat_player_level";
    private final String f3147g = "stat_game_level";
    private C0926a f3148h = null;

    /* compiled from: GameState */
    static class C0926a implements Serializable {
        private static final long f3137a = 20140327;
        private String f3138b;
        private long f3139c;
        private long f3140d;

        public C0926a(String str) {
            this.f3138b = str;
        }

        public boolean m3082a(String str) {
            return this.f3138b.equals(str);
        }

        public void m3081a() {
            this.f3140d = System.currentTimeMillis();
        }

        public void m3083b() {
            this.f3139c += System.currentTimeMillis() - this.f3140d;
            this.f3140d = 0;
        }

        public void m3084c() {
            m3081a();
        }

        public void m3085d() {
            m3083b();
        }

        public long m3086e() {
            return this.f3139c;
        }

        public String m3087f() {
            return this.f3138b;
        }
    }

    public C0927b(Context context) {
        this.f3143c = context;
    }

    public C0926a m3088a(String str) {
        this.f3148h = new C0926a(str);
        this.f3148h.m3081a();
        return this.f3148h;
    }

    public void m3089a() {
        if (this.f3148h != null) {
            this.f3148h.m3083b();
            Editor edit = this.f3143c.getSharedPreferences("um_g_cache", 0).edit();
            edit.putString("single_level", am.m3446a(this.f3148h));
            edit.putString("stat_player_level", this.f3142b);
            edit.putString("stat_game_level", this.f3141a);
            edit.commit();
        }
    }

    public void m3091b() {
        SharedPreferences a = ap.m3452a(this.f3143c, "um_g_cache");
        String string = a.getString("single_level", null);
        if (string != null) {
            this.f3148h = (C0926a) am.m3445a(string);
            if (this.f3148h != null) {
                this.f3148h.m3084c();
            }
        }
        if (this.f3142b == null) {
            this.f3142b = a.getString("stat_player_level", null);
            if (this.f3142b == null) {
                SharedPreferences a2 = ap.m3451a(this.f3143c);
                if (a2 != null) {
                    this.f3142b = a2.getString("userlevel", null);
                } else {
                    return;
                }
            }
        }
        if (this.f3141a == null) {
            this.f3141a = a.getString("stat_game_level", null);
        }
    }

    public C0926a m3090b(String str) {
        if (this.f3148h != null) {
            this.f3148h.m3085d();
            if (this.f3148h.m3082a(str)) {
                C0926a c0926a = this.f3148h;
                this.f3148h = null;
                return c0926a;
            }
        }
        return null;
    }
}
