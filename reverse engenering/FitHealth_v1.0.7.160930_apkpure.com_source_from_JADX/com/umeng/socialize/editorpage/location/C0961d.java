package com.umeng.socialize.editorpage.location;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.umeng.socialize.utils.DeviceConfig;

/* compiled from: SocializeLocationManager */
public class C0961d {
    LocationManager f3307a = null;

    public void m3223a(Context context) {
        if (DeviceConfig.checkPermission(context, "android.permission.ACCESS_FINE_LOCATION") || DeviceConfig.checkPermission(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            this.f3307a = (LocationManager) context.getApplicationContext().getSystemService("location");
        }
    }

    public String m3221a(Criteria criteria, boolean z) {
        return this.f3307a == null ? null : this.f3307a.getBestProvider(criteria, z);
    }

    public Location m3220a(String str) {
        return this.f3307a == null ? null : this.f3307a.getLastKnownLocation(str);
    }

    public boolean m3225b(String str) {
        return this.f3307a == null ? false : this.f3307a.isProviderEnabled(str);
    }

    public void m3222a(Activity activity, String str, long j, float f, LocationListener locationListener) {
        if (this.f3307a != null) {
            activity.runOnUiThread(new C0962e(this, str, j, f, locationListener));
        }
    }

    public void m3224a(LocationListener locationListener) {
        if (this.f3307a != null) {
            this.f3307a.removeUpdates(locationListener);
        }
    }
}
