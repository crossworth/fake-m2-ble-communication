package com.umeng.socialize.editorpage.location;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.text.TextUtils;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;

/* compiled from: DefaultLocationProvider */
public class C1811a implements SocializeLocationProvider {
    private static final String f4824a = "DefaultLocationProvider";
    private Location f4825b;
    private Context f4826c;
    private C0961d f4827d;
    private C0960c f4828e = null;
    private String f4829f;

    public void m4993a(Context context) {
        this.f4826c = context;
        this.f4828e = new C0960c();
        mo2174b();
    }

    public void m4992a() {
        if (this.f4827d != null && this.f4828e != null) {
            this.f4827d.m3224a(this.f4828e);
        }
    }

    public Location mo2174b() {
        if (this.f4825b == null) {
            if (DeviceConfig.checkPermission(this.f4826c, "android.permission.ACCESS_FINE_LOCATION")) {
                m4991a(this.f4826c, 1);
            } else if (DeviceConfig.checkPermission(this.f4826c, "android.permission.ACCESS_COARSE_LOCATION")) {
                m4991a(this.f4826c, 2);
            }
        }
        return this.f4825b;
    }

    private void m4991a(Context context, int i) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(i);
        String a = this.f4827d.m3221a(criteria, true);
        if (a != null) {
            this.f4829f = a;
        }
        Log.m3248d(f4824a, "Get location from " + this.f4829f);
        try {
            if (!TextUtils.isEmpty(this.f4829f)) {
                Location a2 = this.f4827d.m3220a(this.f4829f);
                if (a2 != null) {
                    this.f4825b = a2;
                } else if (this.f4827d.m3225b(this.f4829f) && this.f4828e != null && (context instanceof Activity)) {
                    this.f4827d.m3222a((Activity) context, this.f4829f, 1, 0.0f, this.f4828e);
                }
            }
        } catch (IllegalArgumentException e) {
        }
    }

    public void m4995a(C0961d c0961d) {
        this.f4827d = c0961d;
    }

    protected C0961d m4998c() {
        return this.f4827d;
    }

    protected void m4994a(Location location) {
        this.f4825b = location;
    }

    public void m4996a(String str) {
        this.f4829f = str;
    }
}
