package com.baidu.location.p010d;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.widget.AutoScrollHelper;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.Jni;
import com.baidu.location.LocationClient;
import com.baidu.platform.comapi.location.CoordinateType;
import com.zhuoyou.plugin.running.app.Permissions;
import java.util.ArrayList;
import java.util.Iterator;

public class C0407a {
    private ArrayList<BDNotifyListener> f558a = null;
    private float f559b = AutoScrollHelper.NO_MAX;
    private BDLocation f560c = null;
    private long f561d = 0;
    private LocationClient f562e = null;
    private Context f563f = null;
    private int f564g = 0;
    private long f565h = 0;
    private boolean f566i = false;
    private PendingIntent f567j = null;
    private AlarmManager f568k = null;
    private C0405a f569l = null;
    private C0406b f570m = new C0406b(this);
    private boolean f571n = false;

    public class C0405a extends BroadcastReceiver {
        final /* synthetic */ C0407a f556a;

        public C0405a(C0407a c0407a) {
            this.f556a = c0407a;
        }

        public void onReceive(Context context, Intent intent) {
            if (this.f556a.f558a != null && !this.f556a.f558a.isEmpty()) {
                this.f556a.f562e.requestNotifyLocation();
            }
        }
    }

    public class C0406b implements BDLocationListener {
        final /* synthetic */ C0407a f557a;

        public C0406b(C0407a c0407a) {
            this.f557a = c0407a;
        }

        public void onReceiveLocation(BDLocation bDLocation) {
            if (this.f557a.f558a != null && this.f557a.f558a.size() > 0) {
                this.f557a.m626a(bDLocation);
            }
        }
    }

    public C0407a(Context context, LocationClient locationClient) {
        this.f563f = context;
        this.f562e = locationClient;
        this.f562e.registerNotifyLocationListener(this.f570m);
        this.f568k = (AlarmManager) this.f563f.getSystemService("alarm");
        this.f569l = new C0405a(this);
        this.f571n = false;
    }

    private void m625a(long j) {
        try {
            if (this.f567j != null) {
                this.f568k.cancel(this.f567j);
            }
            this.f567j = PendingIntent.getBroadcast(this.f563f, 0, new Intent("android.com.baidu.location.TIMER.NOTIFY"), 134217728);
            if (this.f567j != null) {
                this.f568k.set(0, System.currentTimeMillis() + j, this.f567j);
            }
        } catch (Exception e) {
        }
    }

    private void m626a(BDLocation bDLocation) {
        if (bDLocation.getLocType() != 61 && bDLocation.getLocType() != 161 && bDLocation.getLocType() != 65) {
            m625a(120000);
        } else if (System.currentTimeMillis() - this.f561d >= 5000 && this.f558a != null) {
            this.f560c = bDLocation;
            this.f561d = System.currentTimeMillis();
            float[] fArr = new float[1];
            Iterator it = this.f558a.iterator();
            float f = AutoScrollHelper.NO_MAX;
            while (it.hasNext()) {
                BDNotifyListener bDNotifyListener = (BDNotifyListener) it.next();
                Location.distanceBetween(bDLocation.getLatitude(), bDLocation.getLongitude(), bDNotifyListener.mLatitudeC, bDNotifyListener.mLongitudeC, fArr);
                float radius = (fArr[0] - bDNotifyListener.mRadius) - bDLocation.getRadius();
                if (radius > 0.0f) {
                    if (radius < f) {
                    }
                    radius = f;
                } else {
                    if (bDNotifyListener.Notified < 3) {
                        bDNotifyListener.Notified++;
                        bDNotifyListener.onNotify(bDLocation, fArr[0]);
                        if (bDNotifyListener.Notified < 3) {
                            this.f566i = true;
                        }
                    }
                    radius = f;
                }
                f = radius;
            }
            if (f < this.f559b) {
                this.f559b = f;
            }
            this.f564g = 0;
            m630c();
        }
    }

    private boolean m629b() {
        if (this.f558a == null || this.f558a.isEmpty()) {
            return false;
        }
        Iterator it = this.f558a.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z = ((BDNotifyListener) it.next()).Notified < 3 ? true : z;
        }
        return z;
    }

    private void m630c() {
        int i = 10000;
        if (m629b()) {
            boolean z;
            int i2 = this.f559b > 5000.0f ? 600000 : this.f559b > 1000.0f ? 120000 : this.f559b > 500.0f ? 60000 : 10000;
            if (this.f566i) {
                this.f566i = false;
            } else {
                i = i2;
            }
            if (this.f564g != 0) {
                if (((long) i) > (this.f565h + ((long) this.f564g)) - System.currentTimeMillis()) {
                    z = false;
                    if (z) {
                        this.f564g = i;
                        this.f565h = System.currentTimeMillis();
                        m625a((long) this.f564g);
                    }
                }
            }
            z = true;
            if (z) {
                this.f564g = i;
                this.f565h = System.currentTimeMillis();
                m625a((long) this.f564g);
            }
        }
    }

    public int m631a(BDNotifyListener bDNotifyListener) {
        if (this.f558a == null) {
            this.f558a = new ArrayList();
        }
        this.f558a.add(bDNotifyListener);
        bDNotifyListener.isAdded = true;
        bDNotifyListener.mNotifyCache = this;
        if (!this.f571n) {
            this.f563f.registerReceiver(this.f569l, new IntentFilter("android.com.baidu.location.TIMER.NOTIFY"), Permissions.PERMISSION_LOCATION, null);
            this.f571n = true;
        }
        if (bDNotifyListener.mCoorType != null) {
            if (!bDNotifyListener.mCoorType.equals(CoordinateType.GCJ02)) {
                double[] coorEncrypt = Jni.coorEncrypt(bDNotifyListener.mLongitude, bDNotifyListener.mLatitude, bDNotifyListener.mCoorType + "2gcj");
                bDNotifyListener.mLongitudeC = coorEncrypt[0];
                bDNotifyListener.mLatitudeC = coorEncrypt[1];
            }
            if (this.f560c == null || System.currentTimeMillis() - this.f561d > StatisticConfig.MIN_UPLOAD_INTERVAL) {
                this.f562e.requestNotifyLocation();
            } else {
                float[] fArr = new float[1];
                Location.distanceBetween(this.f560c.getLatitude(), this.f560c.getLongitude(), bDNotifyListener.mLatitudeC, bDNotifyListener.mLongitudeC, fArr);
                float radius = (fArr[0] - bDNotifyListener.mRadius) - this.f560c.getRadius();
                if (radius > 0.0f) {
                    if (radius < this.f559b) {
                        this.f559b = radius;
                    }
                } else if (bDNotifyListener.Notified < 3) {
                    bDNotifyListener.Notified++;
                    bDNotifyListener.onNotify(this.f560c, fArr[0]);
                    if (bDNotifyListener.Notified < 3) {
                        this.f566i = true;
                    }
                }
            }
            m630c();
        }
        return 1;
    }

    public void m632a() {
        if (this.f567j != null) {
            this.f568k.cancel(this.f567j);
        }
        this.f560c = null;
        this.f561d = 0;
        if (this.f571n) {
            this.f563f.unregisterReceiver(this.f569l);
        }
        this.f571n = false;
    }

    public void m633b(BDNotifyListener bDNotifyListener) {
        if (bDNotifyListener.mCoorType != null) {
            if (!bDNotifyListener.mCoorType.equals(CoordinateType.GCJ02)) {
                double[] coorEncrypt = Jni.coorEncrypt(bDNotifyListener.mLongitude, bDNotifyListener.mLatitude, bDNotifyListener.mCoorType + "2gcj");
                bDNotifyListener.mLongitudeC = coorEncrypt[0];
                bDNotifyListener.mLatitudeC = coorEncrypt[1];
            }
            if (this.f560c == null || System.currentTimeMillis() - this.f561d > 300000) {
                this.f562e.requestNotifyLocation();
            } else {
                float[] fArr = new float[1];
                Location.distanceBetween(this.f560c.getLatitude(), this.f560c.getLongitude(), bDNotifyListener.mLatitudeC, bDNotifyListener.mLongitudeC, fArr);
                float radius = (fArr[0] - bDNotifyListener.mRadius) - this.f560c.getRadius();
                if (radius > 0.0f) {
                    if (radius < this.f559b) {
                        this.f559b = radius;
                    }
                } else if (bDNotifyListener.Notified < 3) {
                    bDNotifyListener.Notified++;
                    bDNotifyListener.onNotify(this.f560c, fArr[0]);
                    if (bDNotifyListener.Notified < 3) {
                        this.f566i = true;
                    }
                }
            }
            m630c();
        }
    }

    public int m634c(BDNotifyListener bDNotifyListener) {
        if (this.f558a == null) {
            return 0;
        }
        if (this.f558a.contains(bDNotifyListener)) {
            this.f558a.remove(bDNotifyListener);
        }
        if (this.f558a.size() == 0 && this.f567j != null) {
            this.f568k.cancel(this.f567j);
        }
        return 1;
    }
}
