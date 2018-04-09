package com.aps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

public class C0475y {
    private static float f1963P = 1.1f;
    private static float f1964Q = 2.2f;
    private static float f1965R = 2.3f;
    private static float f1966S = 3.8f;
    private static int f1967T = 3;
    private static int f1968U = 10;
    private static int f1969V = 2;
    private static int f1970W = 7;
    private static int f1971X = 20;
    private static int f1972Y = 70;
    private static int f1973Z = 120;
    protected static boolean f1974a = false;
    protected static boolean f1975b = true;
    private static int f1976c = 10;
    private static int f1977d = 2;
    private static int f1978e = 10;
    private static int f1979f = 10;
    private static int f1980g = 50;
    private static int f1981h = 200;
    private static Object f1982i = new Object();
    private static C0475y f1983j;
    private Thread f1984A = null;
    private Looper f1985B = null;
    private at f1986C = null;
    private Location f1987D = null;
    private as f1988E = null;
    private Handler f1989F = null;
    private au f1990G = new au(this);
    private LocationListener f1991H = new ao(this);
    private BroadcastReceiver f1992I = new ap(this);
    private GpsStatus f1993J = null;
    private int f1994K = 0;
    private int f1995L = 0;
    private HashMap f1996M = null;
    private int f1997N = 0;
    private int f1998O = 0;
    private boolean f1999k = false;
    private boolean f2000l = false;
    private int f2001m = -1;
    private int f2002n = 0;
    private int f2003o = 0;
    private int f2004p = 10000;
    private long f2005q = 0;
    private Context f2006r;
    private LocationManager f2007s;
    private ai f2008t;
    private aw f2009u;
    private bd f2010v;
    private af f2011w;
    private bc f2012x;
    private av f2013y;
    private ab f2014z;

    private C0475y(Context context) {
        this.f2006r = context;
        this.f2008t = ai.m1757a(context);
        this.f2014z = new ab();
        this.f2009u = new aw(this.f2008t);
        this.f2011w = new af(context);
        this.f2010v = new bd(this.f2011w);
        this.f2012x = new bc(this.f2011w);
        this.f2007s = (LocationManager) this.f2006r.getSystemService("location");
        this.f2013y = av.m1816a(this.f2006r);
        this.f2013y.m1823a(this.f1990G);
        m2066n();
        List allProviders = this.f2007s.getAllProviders();
        boolean z = allProviders != null && allProviders.contains("gps") && allProviders.contains("passive");
        this.f2000l = z;
        be.m1851a(context);
    }

    static /* synthetic */ int m2026a(C0475y c0475y, C0473w c0473w, int i) {
        if (c0475y.f1997N >= f1968U) {
            return 1;
        }
        if (c0475y.f1997N <= f1967T) {
            return 4;
        }
        double c = c0473w.m2024c();
        if (c <= ((double) f1963P)) {
            return 1;
        }
        if (c >= ((double) f1964Q)) {
            return 4;
        }
        c = c0473w.m2023b();
        return c > ((double) f1965R) ? c >= ((double) f1966S) ? 4 : i < f1970W ? i <= f1969V ? 4 : c0475y.f1996M != null ? c0475y.m2027a(c0475y.f1996M) : 3 : 1 : 1;
    }

    private int m2027a(HashMap hashMap) {
        if (this.f1994K > 4) {
            int i;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            int i2 = 0;
            for (Entry value : hashMap.entrySet()) {
                List list = (List) value.getValue();
                if (list != null) {
                    Object a = m2039a(list);
                    if (a != null) {
                        arrayList.add(a);
                        i = i2 + 1;
                        arrayList2.add(Integer.valueOf(i2));
                        i2 = i;
                    }
                }
                i = i2;
                i2 = i;
            }
            if (!arrayList.isEmpty()) {
                double[] dArr = new double[2];
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    double[] dArr2 = (double[]) arrayList.get(i3);
                    i = ((Integer) arrayList2.get(i3)).intValue();
                    dArr2[0] = dArr2[0] * ((double) i);
                    dArr2[1] = dArr2[1] * ((double) i);
                    dArr[0] = dArr[0] + dArr2[0];
                    dArr[1] = dArr[1] + dArr2[1];
                }
                dArr[0] = dArr[0] / ((double) size);
                dArr[1] = dArr[1] / ((double) size);
                double d = dArr[0];
                double d2 = dArr[1];
                double toDegrees = d2 == 0.0d ? d > 0.0d ? 90.0d : d < 0.0d ? 270.0d : 0.0d : Math.toDegrees(Math.atan(d / d2));
                double[] dArr3 = new double[]{Math.sqrt((d * d) + (d2 * d2)), toDegrees};
                String.format(Locale.CHINA, "%d,%d,%d,%d", new Object[]{Long.valueOf(Math.round(dArr[0] * 100.0d)), Long.valueOf(Math.round(dArr[1] * 100.0d)), Long.valueOf(Math.round(dArr3[0] * 100.0d)), Long.valueOf(Math.round(dArr3[1] * 100.0d))});
                if (dArr3[0] <= ((double) f1972Y)) {
                    return 1;
                }
                if (dArr3[0] >= ((double) f1973Z)) {
                    return 4;
                }
            }
        }
        return 3;
    }

    public static C0475y m2034a(Context context) {
        if (f1983j == null) {
            synchronized (f1982i) {
                if (f1983j == null) {
                    f1983j = new C0475y(context);
                }
            }
        }
        return f1983j;
    }

    static /* synthetic */ void m2037a(C0475y c0475y, Location location, int i, long j) {
        Location location2;
        int i2;
        int i3;
        aa a;
        int i4 = 1;
        System.currentTimeMillis();
        boolean a2 = c0475y.f2009u.m1835a(location);
        if (a2) {
            c0475y.f2009u.f1786b.f1789b = new Location(location);
        }
        boolean b = c0475y.f2009u.m1836b(location);
        if (b) {
            c0475y.f2009u.f1785a.f1799b = new Location(location);
        }
        if (i == 1) {
            location2 = c0475y.f1987D;
            i2 = 1;
            i3 = 1;
        } else if (i == 2) {
            location2 = c0475y.f1987D;
            i2 = 1;
            i3 = 0;
        } else {
            boolean z = a2;
            a2 = b;
            location2 = location;
        }
        if (i3 == 0) {
            i4 = i2 != 0 ? 2 : 0;
        } else if (i2 != 0) {
            i4 = 3;
        }
        try {
            ab abVar = c0475y.f2014z;
            a = ab.m1727a(location2, c0475y.f2008t, i4, (byte) c0475y.f1998O, j);
        } catch (Exception e) {
            a = null;
        }
        if (a != null && c0475y.f2008t != null) {
            List n = c0475y.f2008t.m1801n();
            Long valueOf = Long.valueOf(0);
            if (n != null && n.size() > 0) {
                valueOf = (Long) n.get(0);
            }
            c0475y.f2010v.m1849a(valueOf.longValue(), a.m1726a());
        }
    }

    private double[] m2039a(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        double[] dArr = new double[2];
        for (GpsSatellite gpsSatellite : list) {
            if (gpsSatellite != null) {
                double elevation = (double) (90.0f - gpsSatellite.getElevation());
                double azimuth = (double) gpsSatellite.getAzimuth();
                double[] dArr2 = new double[]{Math.sin(Math.toRadians(azimuth)) * elevation, elevation * Math.cos(Math.toRadians(azimuth))};
                dArr[0] = dArr[0] + dArr2[0];
                dArr[1] = dArr[1] + dArr2[1];
            }
        }
        int size = list.size();
        dArr[0] = dArr[0] / ((double) size);
        dArr[1] = dArr[1] / ((double) size);
        return dArr;
    }

    static /* synthetic */ String m2043b(C0475y c0475y, String str) {
        return str;
    }

    private void m2066n() {
        this.f2002n = this.f2013y.m1826b() * 1000;
        this.f2003o = this.f2013y.m1828c();
        aw awVar = this.f2009u;
        int i = this.f2002n;
        i = this.f2003o;
        aw.m1832a();
    }

    public void m2069a() {
        if (this.f2000l && this.f2008t != null && !f1974a) {
            IntentFilter intentFilter = new IntentFilter("android.location.GPS_ENABLED_CHANGE");
            intentFilter.addAction("android.location.GPS_FIX_CHANGE");
            this.f2006r.registerReceiver(this.f1992I, intentFilter);
            String str = "";
            this.f2007s.removeUpdates(this.f1991H);
            if (this.f1985B != null) {
                this.f1985B.quit();
                this.f1985B = null;
            }
            if (this.f1984A != null) {
                this.f1984A.interrupt();
                this.f1984A = null;
            }
            this.f1984A = new aq(this, str);
            this.f1984A.start();
            this.f2008t.m1787a();
            f1974a = true;
        }
    }

    public void m2070a(int i) {
        if (i == 256 || i == 8736 || i == 768) {
            this.f2011w.m1749a(i);
            return;
        }
        throw new RuntimeException("invalid Size! must be COLLECTOR_SMALL_SIZE or COLLECTOR_BIG_SIZE or COLLECTOR_MEDIUM_SIZE");
    }

    public void m2071a(ae aeVar, String str) {
        boolean a = this.f2013y.m1825a(str);
        if (aeVar != null) {
            byte[] a2 = aeVar.m1730a();
            if (a && a2 != null) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.f2006r.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == 1) {
                        this.f2013y.m1822a(a2.length + this.f2013y.m1830e());
                    } else {
                        this.f2013y.m1827b(a2.length + this.f2013y.m1831f());
                    }
                }
            }
            aeVar.m1729a(a);
            this.f2012x.m1848a(aeVar);
        }
    }

    public void m2072b() {
        if (this.f2000l && this.f2008t != null && f1974a) {
            if (this.f1992I != null) {
                try {
                    this.f2006r.unregisterReceiver(this.f1992I);
                } catch (Exception e) {
                }
            }
            this.f2007s.removeGpsStatusListener(this.f1988E);
            this.f2007s.removeNmeaListener(this.f1988E);
            this.f1988E = null;
            this.f2007s.removeUpdates(this.f1991H);
            if (this.f1985B != null) {
                this.f1985B.quit();
                this.f1985B = null;
            }
            if (this.f1984A != null) {
                this.f1984A.interrupt();
                this.f1984A = null;
            }
            if (this.f1986C != null) {
                this.f1999k = false;
                this.f1986C.interrupt();
                this.f1986C = null;
            }
            this.f2008t.m1789b();
            f1974a = false;
        }
    }

    public void m2073c() {
        if (this.f2000l) {
            m2072b();
        }
    }

    public ae m2074d() {
        m2075e();
        return !this.f2013y.m1824a() ? null : this.f2012x.m1847a(this.f2013y.m1829d());
    }

    public boolean m2075e() {
        if (this.f2008t == null) {
            return false;
        }
        List n = this.f2008t.m1801n();
        return (n == null || n.size() <= 0) ? false : this.f2011w.m1751b(((Long) n.get(0)).longValue());
    }

    public int m2076f() {
        return this.f2012x != null ? this.f2012x.m1846a() : 0;
    }
}
