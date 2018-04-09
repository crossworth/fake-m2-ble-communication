package com.droi.sdk.push;

import android.content.Context;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.utility.Utility;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class C0991f {
    final long f3281a;
    final int f3282b;
    final String f3283c;
    final String f3284d;
    final String f3285e;
    final String f3286f;
    final String f3287g;
    final boolean f3288h;
    final boolean f3289i;
    final boolean f3290j;
    final boolean f3291k;
    final int f3292l;
    final String f3293m;
    final String f3294n;
    final String f3295o;
    public boolean f3296p = false;
    private int f3297q;
    private final String f3298r;
    private final long f3299s;
    private long f3300t;
    private long f3301u;
    private Set f3302v = new HashSet();
    private Set f3303w = new HashSet();
    private AtomicInteger f3304x = new AtomicInteger(0);
    private int f3305y = 1;
    private C0993h f3306z;

    public C0991f(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            C1012g.m3140b("DroiPushNotification: create json object failed - " + str);
            jSONObject = null;
        }
        this.f3281a = C1004t.m3073a(jSONObject);
        this.f3282b = C1004t.m3074b(jSONObject);
        this.f3298r = C1004t.m3075c(jSONObject);
        this.f3283c = C1004t.m3076d(jSONObject);
        this.f3284d = C1004t.m3077e(jSONObject);
        this.f3285e = C1004t.m3078f(jSONObject);
        this.f3286f = C1004t.m3079g(jSONObject);
        this.f3287g = C1004t.m3080h(jSONObject);
        this.f3288h = C1004t.m3082j(jSONObject);
        this.f3289i = C1004t.m3083k(jSONObject);
        this.f3290j = C1004t.m3084l(jSONObject);
        this.f3291k = C1004t.m3085m(jSONObject);
        this.f3292l = C1004t.m3086n(jSONObject);
        this.f3293m = C1004t.m3087o(jSONObject);
        this.f3294n = C1004t.m3088p(jSONObject);
        this.f3295o = C1004t.m3089q(jSONObject);
        this.f3299s = C1004t.m3081i(jSONObject) * 60000;
        long[] t = C1004t.m3092t(jSONObject);
        if (t.length == 2) {
            this.f3300t = t[0];
            this.f3301u = t[1];
        } else {
            this.f3300t = 0;
            this.f3301u = 0;
        }
        if (this.f3299s < 0) {
            this.f3296p = false;
        } else if (this.f3299s > 0) {
            this.f3305y = 3;
        } else if (this.f3299s == 0) {
            if (this.f3301u > this.f3300t) {
                this.f3305y = 2;
                if (System.currentTimeMillis() > this.f3301u) {
                    this.f3296p = false;
                }
            } else if (this.f3301u < this.f3300t) {
                this.f3296p = false;
            } else {
                this.f3296p = this.f3300t == 0;
            }
        }
        if (C1015j.m3168d(this.f3286f)) {
            C1012g.m3141c("DroiPushNotification: notify_icon - " + this.f3286f);
            if (this.f3282b == 2) {
                this.f3303w.add(this.f3286f);
            }
            this.f3302v.add(this.f3286f);
        }
        if (C1015j.m3168d(this.f3287g)) {
            C1012g.m3141c("DroiPushNotification: big_icon - " + this.f3287g);
            this.f3302v.add(this.f3287g);
        }
        if (C1015j.m3168d(this.f3295o)) {
            C1012g.m3141c("DroiPushNotification: action_icon - " + this.f3295o);
            this.f3302v.add(this.f3295o);
        }
    }

    public int m3048a() {
        return this.f3297q;
    }

    void m3049a(int i) {
        this.f3297q = i;
    }

    public void m3050a(C0993h c0993h) {
        this.f3306z = c0993h;
    }

    public synchronized void m3051a(String str, boolean z) {
        C1012g.m3138a("download " + str + " result - " + z);
        if (z || !this.f3303w.contains(str)) {
            this.f3304x.addAndGet(1);
            if (this.f3304x.get() == this.f3302v.size()) {
                C1012g.m3141c("PushMsg_" + this.f3281a + ": images all loaded");
                this.f3306z.mo1934a(this, true);
            }
        } else {
            C1012g.m3141c("PushMsg_" + this.f3281a + ": load failed>>>" + str);
            this.f3306z.mo1934a(this, false);
        }
    }

    boolean m3052a(Context context) {
        if (m3053b() && (!C1015j.m3168d(this.f3283c) || !C1015j.m3168d(this.f3284d))) {
            return false;
        }
        if (this.f3292l == 3) {
            if (!C1015j.m3168d(this.f3294n)) {
                return false;
            }
            String str = this.f3294n.split("\\|")[0];
            if (!C1015j.m3168d(str) || C1015j.m3165c(context, str)) {
                return false;
            }
        }
        return true;
    }

    boolean m3053b() {
        return this.f3282b == 1;
    }

    public void m3054c() {
        if (this.f3302v != null && !this.f3302v.isEmpty()) {
            for (String str : this.f3302v) {
                Utility.getBitmapInBackground(str, 0, 0, new C0992g(this, str));
            }
        }
    }

    public boolean m3055d() {
        return !this.f3302v.isEmpty();
    }

    public boolean m3056e() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f3305y == 2) {
            if (currentTimeMillis >= this.f3300t && currentTimeMillis <= this.f3301u) {
                return true;
            }
        } else if (this.f3305y != 3 || Math.abs(currentTimeMillis - this.f3300t) >= this.f3299s) {
            return true;
        }
        return false;
    }
}
