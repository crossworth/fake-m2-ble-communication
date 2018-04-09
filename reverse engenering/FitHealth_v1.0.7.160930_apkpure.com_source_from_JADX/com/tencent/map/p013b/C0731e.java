package com.tencent.map.p013b;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.Iterator;

public final class C0731e {
    private static LocationManager f2550b = null;
    private static float f2551d = 0.0f;
    private Context f2552a = null;
    private C0730c f2553c = null;
    private C0729b f2554e = null;
    private C0728a f2555f = null;
    private boolean f2556g = false;
    private byte[] f2557h = new byte[0];
    private int f2558i = 1024;
    private long f2559j = 0;
    private boolean f2560k = false;
    private int f2561l = 0;
    private int f2562m = 0;

    public class C0728a implements Cloneable {
        private Location f2546a = null;
        private long f2547b = 0;
        private int f2548c = 0;

        public C0728a(C0731e c0731e, Location location, int i, int i2, int i3, long j) {
            if (location != null) {
                this.f2546a = new Location(location);
                this.f2548c = i2;
                this.f2547b = j;
            }
        }

        public final boolean m2428a() {
            return this.f2546a == null ? false : (this.f2548c <= 0 || this.f2548c >= 3) && System.currentTimeMillis() - this.f2547b <= 30000;
        }

        public final Location m2429b() {
            return this.f2546a;
        }

        public final Object clone() {
            C0728a c0728a;
            try {
                c0728a = (C0728a) super.clone();
            } catch (Exception e) {
                c0728a = null;
            }
            if (this.f2546a != null) {
                c0728a.f2546a = new Location(this.f2546a);
            }
            return c0728a;
        }
    }

    public interface C0729b {
        void mo2098a(int i);

        void mo2099a(C0728a c0728a);
    }

    class C0730c implements Listener, LocationListener {
        private /* synthetic */ C0731e f2549a;

        private C0730c(C0731e c0731e) {
            this.f2549a = c0731e;
        }

        public final void onGpsStatusChanged(int i) {
            switch (i) {
                case 1:
                    C0731e.m2432a(this.f2549a, 1);
                    break;
                case 2:
                    C0731e.m2432a(this.f2549a, 0);
                    break;
                case 3:
                    C0731e.m2432a(this.f2549a, 2);
                    break;
            }
            this.f2549a.m2438b();
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
                    this.f2549a.f2559j = System.currentTimeMillis();
                    this.f2549a.m2438b();
                    C0731e.m2432a(this.f2549a, 2);
                    this.f2549a.f2555f = new C0728a(this.f2549a, location, this.f2549a.f2561l, this.f2549a.f2562m, this.f2549a.f2558i, this.f2549a.f2559j);
                    if (this.f2549a.f2554e != null) {
                        this.f2549a.f2554e.mo2099a(this.f2549a.f2555f);
                    }
                }
            }
        }

        public final void onProviderDisabled(String str) {
            if (str != null) {
                try {
                    if (str.equals("gps")) {
                        this.f2549a.f2561l = this.f2549a.f2562m = 0;
                        this.f2549a.f2558i = 0;
                        if (this.f2549a.f2554e != null) {
                            this.f2549a.f2554e.mo2098a(this.f2549a.f2558i);
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
                        this.f2549a.f2558i = 4;
                        if (this.f2549a.f2554e != null) {
                            this.f2549a.f2554e.mo2098a(this.f2549a.f2558i);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    static /* synthetic */ int m2432a(C0731e c0731e, int i) {
        int i2 = c0731e.f2558i | i;
        c0731e.f2558i = i2;
        return i2;
    }

    private void m2438b() {
        this.f2562m = 0;
        this.f2561l = 0;
        GpsStatus gpsStatus = f2550b.getGpsStatus(null);
        if (gpsStatus != null) {
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator it = gpsStatus.getSatellites().iterator();
            if (it != null) {
                while (it.hasNext() && this.f2561l <= maxSatellites) {
                    this.f2561l++;
                    if (((GpsSatellite) it.next()).usedInFix()) {
                        this.f2562m++;
                    }
                }
            }
        }
    }

    public final void m2446a() {
        synchronized (this.f2557h) {
            if (this.f2556g) {
                if (!(f2550b == null || this.f2553c == null)) {
                    f2550b.removeGpsStatusListener(this.f2553c);
                    f2550b.removeUpdates(this.f2553c);
                }
                this.f2556g = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m2447a(com.tencent.map.p013b.C0731e.C0729b r9, android.content.Context r10) {
        /*
        r8 = this;
        r0 = 1;
        r6 = 0;
        r7 = r8.f2557h;
        monitor-enter(r7);
        r1 = r8.f2556g;	 Catch:{ all -> 0x0068 }
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
        r8.f2552a = r10;	 Catch:{ all -> 0x0068 }
        r8.f2554e = r9;	 Catch:{ all -> 0x0068 }
        r0 = r8.f2552a;	 Catch:{ Exception -> 0x0035 }
        r1 = "location";
        r0 = r0.getSystemService(r1);	 Catch:{ Exception -> 0x0035 }
        r0 = (android.location.LocationManager) r0;	 Catch:{ Exception -> 0x0035 }
        f2550b = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = new com.tencent.map.b.e$c;	 Catch:{ Exception -> 0x0035 }
        r1 = 0;
        r0.<init>();	 Catch:{ Exception -> 0x0035 }
        r8.f2553c = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = f2550b;	 Catch:{ Exception -> 0x0035 }
        if (r0 == 0) goto L_0x0032;
    L_0x002e:
        r0 = r8.f2553c;	 Catch:{ Exception -> 0x0035 }
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
        r0 = f2550b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = 0;
        r5 = r8.f2553c;	 Catch:{ Exception -> 0x0064 }
        r0.requestLocationUpdates(r1, r2, r4, r5);	 Catch:{ Exception -> 0x0064 }
        r0 = f2550b;	 Catch:{ Exception -> 0x0064 }
        r1 = r8.f2553c;	 Catch:{ Exception -> 0x0064 }
        r0.addGpsStatusListener(r1);	 Catch:{ Exception -> 0x0064 }
        r0 = f2550b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r0 = r0.isProviderEnabled(r1);	 Catch:{ Exception -> 0x0064 }
        if (r0 == 0) goto L_0x0060;
    L_0x0056:
        r0 = 4;
        r8.f2558i = r0;	 Catch:{ Exception -> 0x0064 }
    L_0x0059:
        r0 = 1;
        r8.f2556g = r0;	 Catch:{ all -> 0x0068 }
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r8.f2556g;
        goto L_0x000a;
    L_0x0060:
        r0 = 0;
        r8.f2558i = r0;	 Catch:{ Exception -> 0x0064 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.e.a(com.tencent.map.b.e$b, android.content.Context):boolean");
    }
}
