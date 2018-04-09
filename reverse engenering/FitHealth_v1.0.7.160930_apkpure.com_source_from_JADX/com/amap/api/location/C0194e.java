package com.amap.api.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Message;
import com.amap.api.location.core.C0188c;
import com.aps.C0471u;

/* compiled from: IGPSManager */
class C0194e implements LocationListener {
    final /* synthetic */ C0193d f120a;

    C0194e(C0193d c0193d) {
        this.f120a = c0193d;
    }

    public void onLocationChanged(Location location) {
        AMapLocation aMapLocation;
        AMapLocation aMapLocation2;
        Exception e;
        Throwable th;
        Message message;
        Message message2;
        try {
            this.f120a.f118d.m62b(true);
            this.f120a.f118d.f59d = System.currentTimeMillis();
            aMapLocation = null;
            if (location == null) {
                message2 = new Message();
                message2.obj = null;
                message2.what = 100;
                if (this.f120a.f117c != null) {
                    this.f120a.f117c.sendMessage(message2);
                }
                this.f120a.f118d.f58c = true;
                this.f120a.f118d.f59d = System.currentTimeMillis();
                if (this.f120a.f118d.f57b != null && this.f120a.f118d.f57b.f76a != null) {
                    this.f120a.f118d.f57b.f76a.mo1796a(null);
                    return;
                }
                return;
            }
            if (C0188c.m86a(location.getLatitude(), location.getLongitude())) {
                double[] a = C0471u.m2019a(location.getLongitude(), location.getLatitude());
                aMapLocation2 = new AMapLocation(location);
                try {
                    aMapLocation2.setLatitude(a[1]);
                    aMapLocation2.setLongitude(a[0]);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        message2 = new Message();
                        message2.obj = aMapLocation2;
                        message2.what = 100;
                        if (this.f120a.f117c != null) {
                            this.f120a.f117c.sendMessage(message2);
                        }
                        this.f120a.f118d.f58c = true;
                        this.f120a.f118d.f59d = System.currentTimeMillis();
                        if (this.f120a.f118d.f57b != null && this.f120a.f118d.f57b.f76a != null) {
                            this.f120a.f118d.f57b.f76a.mo1796a(aMapLocation2);
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        aMapLocation = aMapLocation2;
                        message = new Message();
                        message.obj = aMapLocation;
                        message.what = 100;
                        if (this.f120a.f117c != null) {
                            this.f120a.f117c.sendMessage(message);
                        }
                        this.f120a.f118d.f58c = true;
                        this.f120a.f118d.f59d = System.currentTimeMillis();
                        this.f120a.f118d.f57b.f76a.mo1796a(aMapLocation);
                        throw th;
                    }
                }
            }
            aMapLocation2 = new AMapLocation(location);
            message2 = new Message();
            message2.obj = aMapLocation2;
            message2.what = 100;
            if (this.f120a.f117c != null) {
                this.f120a.f117c.sendMessage(message2);
            }
            this.f120a.f118d.f58c = true;
            this.f120a.f118d.f59d = System.currentTimeMillis();
            if (this.f120a.f118d.f57b != null && this.f120a.f118d.f57b.f76a != null) {
                this.f120a.f118d.f57b.f76a.mo1796a(aMapLocation2);
            }
        } catch (Exception e3) {
            e = e3;
            aMapLocation2 = null;
            e.printStackTrace();
            message2 = new Message();
            message2.obj = aMapLocation2;
            message2.what = 100;
            if (this.f120a.f117c != null) {
                this.f120a.f117c.sendMessage(message2);
            }
            this.f120a.f118d.f58c = true;
            this.f120a.f118d.f59d = System.currentTimeMillis();
            if (this.f120a.f118d.f57b != null) {
            }
        } catch (Throwable th3) {
            th3.printStackTrace();
        }
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
