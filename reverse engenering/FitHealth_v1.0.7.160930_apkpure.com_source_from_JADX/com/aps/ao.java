package com.aps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.text.SimpleDateFormat;

final class ao implements LocationListener {
    private /* synthetic */ C0475y f1758a;

    ao(C0475y c0475y) {
        this.f1758a = c0475y;
    }

    private static boolean m1812a(Location location) {
        return location != null && "gps".equalsIgnoreCase(location.getProvider()) && location.getLatitude() > -90.0d && location.getLatitude() < 90.0d && location.getLongitude() > -180.0d && location.getLongitude() < 180.0d;
    }

    public final void onLocationChanged(Location location) {
        try {
            long time = location.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.format(Long.valueOf(time));
            simpleDateFormat.format(Long.valueOf(currentTimeMillis));
            if (time > 0) {
                currentTimeMillis = time;
            }
            if (location != null && m1812a(location)) {
                if (location.getSpeed() > ((float) C0475y.f1978e)) {
                    aw.m1833a(C0475y.f1981h);
                    aw.m1834b(C0475y.f1981h * 10);
                } else if (location.getSpeed() > ((float) C0475y.f1977d)) {
                    aw.m1833a(C0475y.f1980g);
                    aw.m1834b(C0475y.f1980g * 10);
                } else {
                    aw.m1833a(C0475y.f1979f);
                    aw.m1834b(C0475y.f1979f * 10);
                }
                this.f1758a.f2013y.m1824a();
                m1812a(location);
                if (this.f1758a.f2013y.m1824a() && m1812a(location)) {
                    location.setTime(System.currentTimeMillis());
                    this.f1758a.f2005q = System.currentTimeMillis();
                    this.f1758a.f1987D = location;
                    if (!this.f1758a.f1999k) {
                        C0475y.m2037a(this.f1758a, location, 0, currentTimeMillis);
                    } else {
                        be.m1850a("collector");
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public final void onProviderDisabled(String str) {
    }

    public final void onProviderEnabled(String str) {
    }

    public final void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
