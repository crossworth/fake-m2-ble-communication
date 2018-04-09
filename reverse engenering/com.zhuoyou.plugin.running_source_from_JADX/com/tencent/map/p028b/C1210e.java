package com.tencent.map.p028b;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.Iterator;

public final class C1210e {
    private static LocationManager f3809b = null;
    private static float f3810d = 0.0f;
    private Context f3811a = null;
    private C1207a f3812c = null;
    private C1209c f3813e = null;
    private C1208b f3814f = null;
    private boolean f3815g = false;
    private byte[] f3816h = new byte[0];
    private int f3817i = 1024;
    private long f3818j = 0;
    private boolean f3819k = false;
    private int f3820l = 0;
    private int f3821m = 0;

    class C1207a implements Listener, LocationListener {
        private /* synthetic */ C1210e f3805a;

        private C1207a(C1210e c1210e) {
            this.f3805a = c1210e;
        }

        public final void onGpsStatusChanged(int i) {
            switch (i) {
                case 1:
                    C1210e.m3534a(this.f3805a, 1);
                    break;
                case 2:
                    C1210e.m3534a(this.f3805a, 0);
                    break;
                case 3:
                    C1210e.m3534a(this.f3805a, 2);
                    break;
            }
            this.f3805a.m3540b();
        }

        public final void onLocationChanged(Location location) {
            Object obj = null;
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if (latitude != 29.999998211860657d && longitude != 103.99999916553497d && Math.abs(latitude) >= 1.0E-8d && Math.abs(longitude) >= 1.0E-8d && latitude >= -90.0d && latitude <= 90.0d && longitude >= -180.0d && longitude <= 180.0d) {
                    obj = 1;
                }
                if (obj != null) {
                    this.f3805a.f3818j = System.currentTimeMillis();
                    this.f3805a.m3540b();
                    C1210e.m3534a(this.f3805a, 2);
                    this.f3805a.f3814f = new C1208b(this.f3805a, location, this.f3805a.f3820l, this.f3805a.f3821m, this.f3805a.f3817i, this.f3805a.f3818j);
                    if (this.f3805a.f3813e != null) {
                        this.f3805a.f3813e.mo2156a(this.f3805a.f3814f);
                    }
                }
            }
        }

        public final void onProviderDisabled(String str) {
            if (str != null) {
                try {
                    if (str.equals("gps")) {
                        this.f3805a.f3820l = this.f3805a.f3821m = 0;
                        this.f3805a.f3817i = 0;
                        if (this.f3805a.f3813e != null) {
                            this.f3805a.f3813e.mo2154a(this.f3805a.f3817i);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        public final void onProviderEnabled(String str) {
            if (str != null) {
                try {
                    if (str.equals("gps")) {
                        this.f3805a.f3817i = 4;
                        if (this.f3805a.f3813e != null) {
                            this.f3805a.f3813e.mo2154a(this.f3805a.f3817i);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    public class C1208b implements Cloneable {
        private Location f3806a = null;
        private long f3807b = 0;
        private int f3808c = 0;

        public C1208b(C1210e c1210e, Location location, int i, int i2, int i3, long j) {
            if (location != null) {
                this.f3806a = new Location(location);
                this.f3808c = i2;
                this.f3807b = j;
            }
        }

        public final boolean m3530a() {
            return this.f3806a == null ? false : (this.f3808c <= 0 || this.f3808c >= 3) && System.currentTimeMillis() - this.f3807b <= StatisticConfig.MIN_UPLOAD_INTERVAL;
        }

        public final Location m3531b() {
            return this.f3806a;
        }

        public final Object clone() {
            C1208b c1208b;
            try {
                c1208b = (C1208b) super.clone();
            } catch (Exception e) {
                c1208b = null;
            }
            if (this.f3806a != null) {
                c1208b.f3806a = new Location(this.f3806a);
            }
            return c1208b;
        }
    }

    public interface C1209c {
        void mo2154a(int i);

        void mo2156a(C1208b c1208b);
    }

    static /* synthetic */ int m3534a(C1210e c1210e, int i) {
        int i2 = c1210e.f3817i | i;
        c1210e.f3817i = i2;
        return i2;
    }

    private void m3540b() {
        this.f3821m = 0;
        this.f3820l = 0;
        GpsStatus gpsStatus = f3809b.getGpsStatus(null);
        if (gpsStatus != null) {
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator it = gpsStatus.getSatellites().iterator();
            if (it != null) {
                while (it.hasNext() && this.f3820l <= maxSatellites) {
                    this.f3820l++;
                    if (((GpsSatellite) it.next()).usedInFix()) {
                        this.f3821m++;
                    }
                }
            }
        }
    }

    public final void m3548a() {
        synchronized (this.f3816h) {
            if (this.f3815g) {
                if (!(f3809b == null || this.f3812c == null)) {
                    f3809b.removeGpsStatusListener(this.f3812c);
                    f3809b.removeUpdates(this.f3812c);
                }
                this.f3815g = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m3549a(com.tencent.map.p028b.C1210e.C1209c r9, android.content.Context r10) {
        /*
        r8 = this;
        r0 = 1;
        r6 = 0;
        r7 = r8.f3816h;
        monitor-enter(r7);
        r1 = r8.f3815g;	 Catch:{ all -> 0x0068 }
        if (r1 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
    L_0x000a:
        return r0;
    L_0x000b:
        if (r10 == 0) goto L_0x000f;
    L_0x000d:
        if (r9 != 0) goto L_0x0012;
    L_0x000f:
        monitor-exit(r7);
        r0 = r6;
        goto L_0x000a;
    L_0x0012:
        r8.f3811a = r10;	 Catch:{ all -> 0x0068 }
        r8.f3813e = r9;	 Catch:{ all -> 0x0068 }
        r0 = r8.f3811a;	 Catch:{ Exception -> 0x0035 }
        r1 = "location";
        r0 = r0.getSystemService(r1);	 Catch:{ Exception -> 0x0035 }
        r0 = (android.location.LocationManager) r0;	 Catch:{ Exception -> 0x0035 }
        f3809b = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = new com.tencent.map.b.e$a;	 Catch:{ Exception -> 0x0035 }
        r1 = 0;
        r0.<init>();	 Catch:{ Exception -> 0x0035 }
        r8.f3812c = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = f3809b;	 Catch:{ Exception -> 0x0035 }
        if (r0 == 0) goto L_0x0032;
    L_0x002e:
        r0 = r8.f3812c;	 Catch:{ Exception -> 0x0035 }
        if (r0 != 0) goto L_0x0039;
    L_0x0032:
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r6;
        goto L_0x000a;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r7);
        r0 = r6;
        goto L_0x000a;
    L_0x0039:
        r0 = f3809b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = 0;
        r5 = r8.f3812c;	 Catch:{ Exception -> 0x0064 }
        r0.requestLocationUpdates(r1, r2, r4, r5);	 Catch:{ Exception -> 0x0064 }
        r0 = f3809b;	 Catch:{ Exception -> 0x0064 }
        r1 = r8.f3812c;	 Catch:{ Exception -> 0x0064 }
        r0.addGpsStatusListener(r1);	 Catch:{ Exception -> 0x0064 }
        r0 = f3809b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r0 = r0.isProviderEnabled(r1);	 Catch:{ Exception -> 0x0064 }
        if (r0 == 0) goto L_0x0060;
    L_0x0056:
        r0 = 4;
        r8.f3817i = r0;	 Catch:{ Exception -> 0x0064 }
    L_0x0059:
        r0 = 1;
        r8.f3815g = r0;	 Catch:{ all -> 0x0068 }
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r8.f3815g;
        goto L_0x000a;
    L_0x0060:
        r0 = 0;
        r8.f3817i = r0;	 Catch:{ Exception -> 0x0064 }
        goto L_0x0059;
    L_0x0064:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r6;
        goto L_0x000a;
    L_0x0068:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.e.a(com.tencent.map.b.e$c, android.content.Context):boolean");
    }
}
