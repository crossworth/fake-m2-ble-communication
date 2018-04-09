package com.baidu.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.widget.AutoScrollHelper;
import java.util.ArrayList;
import java.util.Iterator;

public class C1986u implements an, C1619j {
    public static final String fi = "android.com.baidu.location.TIMER.NOTIFY";
    private int fe = 0;
    private Context ff = null;
    private AlarmManager fg = null;
    private C1620a fh = new C1620a(this);
    private PendingIntent fj = null;
    private ArrayList fk = null;
    private BDLocation fl = null;
    private long fm = 0;
    private C0538b fn = null;
    private float fo = AutoScrollHelper.NO_MAX;
    private boolean fp = false;
    private boolean fq = false;
    private long fr = 0;
    private boolean fs = false;
    private LocationClient ft = null;

    public class C0538b extends BroadcastReceiver {
        final /* synthetic */ C1986u f2293a;

        public C0538b(C1986u c1986u) {
            this.f2293a = c1986u;
        }

        public void onReceive(Context context, Intent intent) {
            if (this.f2293a.fk != null && !this.f2293a.fk.isEmpty()) {
                this.f2293a.ft.requestNotifyLocation();
            }
        }
    }

    public class C1620a implements BDLocationListener {
        final /* synthetic */ C1986u f4489a;

        public C1620a(C1986u c1986u) {
            this.f4489a = c1986u;
        }

        public void onReceiveLocation(BDLocation bDLocation) {
            this.f4489a.m6079int(bDLocation);
        }

        public void onReceivePoi(BDLocation bDLocation) {
        }
    }

    public C1986u(Context context, LocationClient locationClient) {
        this.ff = context;
        this.ft = locationClient;
        this.ft.registerNotifyLocationListener(this.fh);
        this.fg = (AlarmManager) this.ff.getSystemService("alarm");
        this.fn = new C0538b(this);
        this.fs = false;
    }

    private void aP() {
        int i = 10000;
        if (aR()) {
            boolean z;
            int i2 = this.fo > 5000.0f ? 600000 : this.fo > 1000.0f ? 120000 : this.fo > 500.0f ? 60000 : 10000;
            if (this.fp) {
                this.fp = false;
            } else {
                i = i2;
            }
            if (this.fe != 0) {
                if (((long) i) > (this.fm + ((long) this.fe)) - System.currentTimeMillis()) {
                    z = false;
                    if (z) {
                        this.fe = i;
                        this.fm = System.currentTimeMillis();
                        m6077if((long) this.fe);
                    }
                }
            }
            z = true;
            if (z) {
                this.fe = i;
                this.fm = System.currentTimeMillis();
                m6077if((long) this.fe);
            }
        }
    }

    private boolean aR() {
        if (this.fk == null || this.fk.isEmpty()) {
            return false;
        }
        Iterator it = this.fk.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z = ((BDNotifyListener) it.next()).Notified < 3 ? true : z;
        }
        return z;
    }

    private void m6077if(long j) {
        if (this.fq) {
            this.fg.cancel(this.fj);
        }
        this.fj = PendingIntent.getBroadcast(this.ff, 0, new Intent(fi), 134217728);
        this.fg.set(0, System.currentTimeMillis() + j, this.fj);
    }

    private void m6079int(BDLocation bDLocation) {
        this.fq = false;
        if (bDLocation.getLocType() != 61 && bDLocation.getLocType() != BDLocation.TypeNetWorkLocation && bDLocation.getLocType() != 65) {
            m6077if(120000);
        } else if (System.currentTimeMillis() - this.fr >= 5000 && this.fk != null) {
            this.fl = bDLocation;
            this.fr = System.currentTimeMillis();
            float[] fArr = new float[1];
            Iterator it = this.fk.iterator();
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
                            this.fp = true;
                        }
                    }
                    radius = f;
                }
                f = radius;
            }
            if (f < this.fo) {
                this.fo = f;
            }
            this.fe = 0;
            aP();
        }
    }

    public void aQ() {
        if (this.fq) {
            this.fg.cancel(this.fj);
        }
        this.fl = null;
        this.fr = 0;
        if (this.fs) {
            this.ff.unregisterReceiver(this.fn);
        }
        this.fs = false;
    }

    public int m6080do(BDNotifyListener bDNotifyListener) {
        if (this.fk == null) {
            this.fk = new ArrayList();
        }
        this.fk.add(bDNotifyListener);
        bDNotifyListener.isAdded = true;
        bDNotifyListener.mNotifyCache = this;
        if (!this.fs) {
            this.ff.registerReceiver(this.fn, new IntentFilter(fi));
            this.fs = true;
        }
        if (bDNotifyListener.mCoorType != null) {
            if (!bDNotifyListener.mCoorType.equals(BDGeofence.COORD_TYPE_GCJ)) {
                double[] dArr = Jni.m5812if(bDNotifyListener.mLongitude, bDNotifyListener.mLatitude, bDNotifyListener.mCoorType + "2gcj");
                bDNotifyListener.mLongitudeC = dArr[0];
                bDNotifyListener.mLatitudeC = dArr[1];
            }
            if (this.fl == null || System.currentTimeMillis() - this.fr > 30000) {
                this.ft.requestNotifyLocation();
            } else {
                float[] fArr = new float[1];
                Location.distanceBetween(this.fl.getLatitude(), this.fl.getLongitude(), bDNotifyListener.mLatitudeC, bDNotifyListener.mLongitudeC, fArr);
                float radius = (fArr[0] - bDNotifyListener.mRadius) - this.fl.getRadius();
                if (radius > 0.0f) {
                    if (radius < this.fo) {
                        this.fo = radius;
                    }
                } else if (bDNotifyListener.Notified < 3) {
                    bDNotifyListener.Notified++;
                    bDNotifyListener.onNotify(this.fl, fArr[0]);
                    if (bDNotifyListener.Notified < 3) {
                        this.fp = true;
                    }
                }
            }
            aP();
        }
        return 1;
    }

    public int m6081for(BDNotifyListener bDNotifyListener) {
        if (this.fk == null) {
            return 0;
        }
        if (this.fk.contains(bDNotifyListener)) {
            this.fk.remove(bDNotifyListener);
        }
        if (this.fk.size() == 0 && this.fq) {
            this.fg.cancel(this.fj);
        }
        return 1;
    }

    public void m6082if(BDNotifyListener bDNotifyListener) {
        if (bDNotifyListener.mCoorType != null) {
            if (!bDNotifyListener.mCoorType.equals(BDGeofence.COORD_TYPE_GCJ)) {
                double[] dArr = Jni.m5812if(bDNotifyListener.mLongitude, bDNotifyListener.mLatitude, bDNotifyListener.mCoorType + "2gcj");
                bDNotifyListener.mLongitudeC = dArr[0];
                bDNotifyListener.mLatitudeC = dArr[1];
            }
            if (this.fl == null || System.currentTimeMillis() - this.fr > 300000) {
                this.ft.requestNotifyLocation();
            } else {
                float[] fArr = new float[1];
                Location.distanceBetween(this.fl.getLatitude(), this.fl.getLongitude(), bDNotifyListener.mLatitudeC, bDNotifyListener.mLongitudeC, fArr);
                float radius = (fArr[0] - bDNotifyListener.mRadius) - this.fl.getRadius();
                if (radius > 0.0f) {
                    if (radius < this.fo) {
                        this.fo = radius;
                    }
                } else if (bDNotifyListener.Notified < 3) {
                    bDNotifyListener.Notified++;
                    bDNotifyListener.onNotify(this.fl, fArr[0]);
                    if (bDNotifyListener.Notified < 3) {
                        this.fp = true;
                    }
                }
            }
            aP();
        }
    }
}
