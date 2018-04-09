package com.amap.api.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.api.location.core.C0189d;
import com.aps.C0454j;
import com.tencent.open.yyb.TitleBar;
import java.util.Iterator;
import java.util.Vector;

/* compiled from: AMapLocationManager */
public class C0182a {
    C0193d f56a = null;
    C0185c f57b = null;
    boolean f58c = false;
    long f59d;
    boolean f60e = true;
    boolean f61f = true;
    C1583b f62g;
    private Context f63h;
    private Vector<C0195g> f64i = null;
    private C0181a f65j = null;
    private Vector<C0195g> f66k = new Vector();
    private AMapLocation f67l;
    private AMapLocation f68m;
    private Thread f69n;
    private long f70o = 2000;
    private float f71p = TitleBar.SHAREBTN_RIGHT_MARGIN;

    /* compiled from: AMapLocationManager */
    class C0181a extends Handler {
        final /* synthetic */ C0182a f55a;

        public C0181a(C0182a c0182a, Looper looper) {
            this.f55a = c0182a;
            super(looper);
            Looper.prepare();
        }

        public C0181a(C0182a c0182a) {
            this.f55a = c0182a;
        }

        public void handleMessage(Message message) {
            if (message != null) {
                try {
                    if (message.what == 100 && this.f55a.f64i != null) {
                        this.f55a.f67l = (AMapLocation) message.obj;
                        if (!(this.f55a.f67l == null || this.f55a.f67l.getAdCode() == null || this.f55a.f67l.getAdCode().length() <= 0)) {
                            this.f55a.f68m = this.f55a.f67l;
                        }
                        Iterator it = this.f55a.f64i.iterator();
                        while (it.hasNext()) {
                            C0195g c0195g = (C0195g) it.next();
                            if (c0195g.f122b != null) {
                                AMapLocation aMapLocation = (AMapLocation) message.obj;
                                if (c0195g.f123c.booleanValue() || aMapLocation.getAMapException().getErrorCode() == 0) {
                                    c0195g.f122b.onLocationChanged(aMapLocation);
                                    if (c0195g.f123c.booleanValue() && c0195g.f121a == -1 && this.f55a.f66k != null) {
                                        this.f55a.f66k.add(c0195g);
                                    }
                                }
                            }
                        }
                        if (this.f55a.f66k != null && this.f55a.f66k.size() > 0) {
                            for (int i = 0; i < this.f55a.f66k.size(); i++) {
                                this.f55a.m57a(((C0195g) this.f55a.f66k.get(i)).f122b);
                            }
                            this.f55a.f66k.clear();
                        }
                        if (this.f55a.f67l != null) {
                            C0189d.m105a(this.f55a.f63h, this.f55a.f67l);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                }
            }
        }
    }

    C0182a(Context context, LocationManager locationManager) {
        this.f63h = context;
        m47c();
        if (Looper.myLooper() == null) {
            this.f65j = new C0181a(this, context.getMainLooper());
        } else {
            this.f65j = new C0181a(this);
        }
        this.f56a = new C0193d(context, locationManager, this.f65j, this);
        this.f57b = new C0185c(context, this.f65j, this);
        m62b(false);
        this.f60e = true;
        this.f61f = true;
        this.f62g = new C1583b(this, context);
    }

    private void m47c() {
        this.f64i = new Vector();
    }

    void m53a(double d, double d2, float f, long j, PendingIntent pendingIntent) {
        C0454j c0454j = new C0454j();
        c0454j.f1895b = d;
        c0454j.f1894a = d2;
        c0454j.f1896c = f;
        c0454j.m1954a(j);
        this.f57b.m72a(c0454j, pendingIntent);
    }

    void m60b(double d, double d2, float f, long j, PendingIntent pendingIntent) {
        C0454j c0454j = new C0454j();
        c0454j.f1895b = d;
        c0454j.f1894a = d2;
        c0454j.f1896c = f;
        c0454j.m1954a(j);
        this.f57b.m74b(c0454j, pendingIntent);
    }

    void m56a(PendingIntent pendingIntent) {
        this.f57b.m71a(pendingIntent);
    }

    void m61b(PendingIntent pendingIntent) {
        this.f57b.m73b(pendingIntent);
    }

    AMapLocation m52a() {
        if (this.f67l != null) {
            return this.f67l;
        }
        return C0189d.m109b(this.f63h);
    }

    void m55a(long j, float f, AMapLocationListener aMapLocationListener, String str, boolean z) {
        this.f70o = j;
        this.f71p = f;
        if (aMapLocationListener != null) {
            this.f64i.add(new C0195g(j, f, aMapLocationListener, str, z));
        }
        if ("gps".equals(str)) {
            this.f56a.m127a(j, f);
        } else if (LocationProviderProxy.AMapNetwork.equals(str)) {
            if (this.f61f) {
                this.f56a.m127a(j, f);
            }
            this.f57b.m70a(j);
            m48c(true);
            if (this.f69n == null) {
                this.f69n = new Thread(this.f57b);
                this.f69n.start();
            }
        }
    }

    private void m48c(boolean z) {
        this.f60e = z;
    }

    void m57a(AMapLocationListener aMapLocationListener) {
        int size = this.f64i.size();
        int i = 0;
        while (i < size) {
            int i2;
            C0195g c0195g = (C0195g) this.f64i.get(i);
            if (aMapLocationListener.equals(c0195g.f122b)) {
                this.f64i.remove(c0195g);
                i2 = i - 1;
                i = size - 1;
            } else {
                i2 = i;
                i = size;
            }
            size = i;
            i = i2 + 1;
        }
        if (this.f56a != null && this.f64i.size() == 0) {
            this.f56a.m128b();
            m62b(false);
            m48c(false);
            if (this.f69n != null) {
                this.f69n.interrupt();
                this.f69n = null;
            }
        }
    }

    void m58a(boolean z) {
        m50d(z);
        if (this.f64i != null && this.f64i.size() > 0) {
            if (z) {
                this.f56a.m128b();
                this.f56a.m127a(this.f70o, this.f71p);
                return;
            }
            this.f56a.m128b();
        }
    }

    private void m50d(boolean z) {
        this.f61f = z;
    }

    synchronized void m59b() {
        if (this.f56a != null) {
            this.f56a.m128b();
            this.f56a.m126a();
            this.f56a = null;
        }
        if (this.f57b != null) {
            this.f57b.m69a();
            this.f57b = null;
        }
        if (this.f64i != null) {
            this.f64i.clear();
        }
        m62b(false);
        this.f69n = null;
    }

    void m62b(boolean z) {
        this.f58c = z;
    }

    void m54a(final int i, final AMapLocalWeatherListener aMapLocalWeatherListener) {
        try {
            new Thread(this) {
                final /* synthetic */ C0182a f54c;

                public void run() {
                    this.f54c.f62g.m3965a(i, aMapLocalWeatherListener, this.f54c.f68m);
                }
            }.start();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
