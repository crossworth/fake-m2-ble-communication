package com.tencent.map.p028b;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProGuard */
public final class C1226k {
    private static int f3902a;
    private static int f3903b;
    private static int f3904c;
    private static int f3905d;
    private static int f3906e;
    private static int f3907f;
    private static ArrayList<C1225a> f3908g;
    private static long f3909h;
    private static long f3910i;
    private static long f3911j;
    private static long f3912k;
    private static long f3913l;
    private static long f3914m;
    private static long f3915n;
    private static long f3916o;
    private static long f3917p;
    private static long f3918q;
    private static int f3919r;
    private static int f3920s;
    private static int f3921t;
    private static int f3922u;

    /* compiled from: ProGuard */
    public static class C1225a {
        public long f3894a;
        public long f3895b;
        public long f3896c;
        public long f3897d;
        public int f3898e;
        public long f3899f;
        public int f3900g;
        public int f3901h;
    }

    static {
        f3902a = 10000;
        f3903b = 15000;
        f3904c = 5000;
        f3905d = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
        f3906e = 25000;
        f3907f = 15000;
        f3902a = 12000;
        f3903b = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
        f3904c = 8000;
        f3905d = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
        f3906e = 25000;
        f3907f = 15000;
        ConnectivityManager connectivityManager = (ConnectivityManager) C1227l.m3645b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                int type = activeNetworkInfo.getType();
                if (activeNetworkInfo.isConnected() && type == 0) {
                    String subscriberId = ((TelephonyManager) C1227l.m3645b().getSystemService("phone")).getSubscriberId();
                    if (subscriberId != null && subscriberId.length() > 3 && !subscriberId.startsWith("46000") && !subscriberId.startsWith("46002")) {
                        f3902a = 15000;
                        f3903b = 25000;
                        f3904c = 10000;
                        f3905d = 25000;
                        f3906e = 35000;
                        f3907f = 15000;
                    }
                }
            }
        }
    }

    public static int m3634a() {
        int i;
        int i2 = f3902a;
        if (f3911j <= 0 || f3912k <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(f3914m, f3911j) + f3912k) - f3913l);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) C1227l.m3645b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = f3903b;
                } else if (f3912k > 0 && f3912k < ((long) f3904c)) {
                    i = f3904c;
                }
            }
        }
        i2 = (f3922u * f3904c) + i;
        if (i2 <= f3904c) {
            i2 = f3904c;
        }
        if (((long) i2) <= f3912k) {
            i2 = (int) (f3912k + ((long) f3904c));
        }
        if (i2 >= f3903b) {
            i2 = f3903b;
        }
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b == null) {
            b = C1226k.m3635a(Thread.currentThread().getId());
        }
        if (i2 < b.f3900g + f3904c) {
            i2 = b.f3900g + f3904c;
        }
        b.f3900g = i2;
        return i2;
    }

    public static int m3639b() {
        int i;
        int i2 = f3905d;
        if (f3915n <= 0 || f3916o <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(f3918q, f3915n) + f3916o) - f3917p);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) C1227l.m3645b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = f3906e;
                } else if (f3916o > 0 && f3916o < ((long) f3907f)) {
                    i = f3907f;
                }
            }
        }
        i2 = (f3922u * f3904c) + i;
        if (i2 <= f3907f) {
            i2 = f3907f;
        }
        if (((long) i2) <= f3916o) {
            i2 = (int) (f3916o + ((long) f3907f));
        }
        if (i2 >= f3906e) {
            i2 = f3906e;
        }
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b != null) {
            if (i2 < b.f3901h + f3907f) {
                i2 = b.f3901h + f3907f;
            }
            if (i2 < b.f3900g + f3907f) {
                i2 = b.f3900g + f3907f;
            }
            b.f3901h = i2;
        }
        return i2;
    }

    public static void m3638a(boolean z) {
        if (!z) {
            f3922u++;
        }
        C1225a c = C1226k.m3641c(Thread.currentThread().getId());
        if (c != null) {
            long j = c.f3895b;
        }
    }

    public static void m3637a(HttpURLConnection httpURLConnection) {
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b == null) {
            b = C1226k.m3635a(Thread.currentThread().getId());
        }
        if (b != null) {
            b.f3895b = System.currentTimeMillis();
        }
    }

    public static void m3642c() {
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b != null) {
            b.f3896c = System.currentTimeMillis() - b.f3895b;
            b.f3895b = System.currentTimeMillis();
            f3914m = b.f3896c;
            f3912k = b.f3896c > f3912k ? b.f3896c : f3912k;
            long j = b.f3896c < f3913l ? b.f3896c : f3913l == 0 ? b.f3896c : f3913l;
            f3913l = j;
            if (f3908g != null) {
                synchronized (f3908g) {
                    Iterator it = f3908g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        C1225a c1225a = (C1225a) it.next();
                        if (c1225a.f3896c > 0) {
                            f3911j += c1225a.f3896c;
                            i++;
                        }
                    }
                    if (i > 0) {
                        f3911j /= (long) i;
                    }
                }
            }
        }
    }

    public static void m3643d() {
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b != null) {
            long j;
            b.f3897d = System.currentTimeMillis() - b.f3895b;
            b.f3895b = System.currentTimeMillis();
            f3918q = b.f3897d;
            if (b.f3897d > f3916o) {
                j = b.f3897d;
            } else {
                j = f3916o;
            }
            f3916o = j;
            j = b.f3897d < f3917p ? b.f3897d : f3917p == 0 ? b.f3897d : f3917p;
            f3917p = j;
            if (f3908g != null) {
                synchronized (f3908g) {
                    Iterator it = f3908g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        C1225a c1225a = (C1225a) it.next();
                        if (c1225a.f3897d > 0) {
                            f3915n += c1225a.f3897d;
                            i++;
                        }
                    }
                    if (i > 0) {
                        f3915n /= (long) i;
                    }
                }
            }
        }
    }

    public static void m3636a(int i) {
        C1225a b = C1226k.m3640b(Thread.currentThread().getId());
        if (b != null) {
            b.f3899f = System.currentTimeMillis() - b.f3895b;
            b.f3895b = System.currentTimeMillis();
            b.f3898e = i;
            int i2 = (int) (((long) (i * 1000)) / (b.f3899f == 0 ? 1 : b.f3899f));
            f3921t = i2;
            f3919r = i2 > f3919r ? f3921t : f3919r;
            i2 = f3921t < f3920s ? f3921t : f3920s == 0 ? f3921t : f3920s;
            f3920s = i2;
            if (f3908g != null) {
                synchronized (f3908g) {
                    Iterator it = f3908g.iterator();
                    while (it.hasNext()) {
                        C1225a c1225a = (C1225a) it.next();
                        int i3 = c1225a.f3898e;
                        long j = c1225a.f3899f;
                    }
                }
            }
            if (f3922u > 0 && b.f3896c < ((long) f3904c) && b.f3897d < ((long) f3907f)) {
                f3922u--;
            }
            b.f3900g = (int) b.f3896c;
        }
    }

    private static C1225a m3635a(long j) {
        C1225a c1225a;
        if (f3908g == null) {
            f3908g = new ArrayList();
        }
        synchronized (f3908g) {
            if (f3908g.size() > 20) {
                int size = f3908g.size();
                int i = 0;
                Object obj = null;
                int i2 = 0;
                while (i < size / 2) {
                    Object obj2;
                    int i3;
                    if (((C1225a) f3908g.get(i2)).f3899f > 0 || System.currentTimeMillis() - ((C1225a) f3908g.get(i2)).f3895b > 600000) {
                        f3908g.remove(i2);
                        obj2 = 1;
                        i3 = i2;
                    } else {
                        Object obj3 = obj;
                        i3 = i2 + 1;
                        obj2 = obj3;
                    }
                    i++;
                    i2 = i3;
                    obj = obj2;
                }
                if (obj != null) {
                    f3908g.get(0);
                    f3909h = 0;
                    f3908g.get(0);
                    f3910i = 0;
                    f3912k = ((C1225a) f3908g.get(0)).f3896c;
                    f3913l = ((C1225a) f3908g.get(0)).f3896c;
                    f3916o = ((C1225a) f3908g.get(0)).f3897d;
                    f3917p = ((C1225a) f3908g.get(0)).f3897d;
                    if (((C1225a) f3908g.get(0)).f3899f > 0) {
                        f3919r = (int) (((long) (((C1225a) f3908g.get(0)).f3898e * 1000)) / ((C1225a) f3908g.get(0)).f3899f);
                    }
                    f3920s = f3919r;
                    Iterator it = f3908g.iterator();
                    while (it.hasNext()) {
                        c1225a = (C1225a) it.next();
                        if (0 > f3909h) {
                            f3909h = 0;
                        }
                        if (0 < f3910i) {
                            f3910i = 0;
                        }
                        if (c1225a.f3896c > f3912k) {
                            f3912k = c1225a.f3896c;
                        }
                        if (c1225a.f3896c < f3913l) {
                            f3913l = c1225a.f3896c;
                        }
                        if (c1225a.f3897d > f3916o) {
                            f3916o = c1225a.f3897d;
                        }
                        if (c1225a.f3897d < f3917p) {
                            f3917p = c1225a.f3897d;
                        }
                        if (c1225a.f3899f > 0) {
                            int i4 = (int) (((long) (c1225a.f3898e * 1000)) / c1225a.f3899f);
                            if (i4 > f3919r) {
                                f3919r = i4;
                            }
                            if (i4 < f3920s) {
                                f3920s = i4;
                            }
                        }
                    }
                }
            }
            c1225a = new C1225a();
            c1225a.f3894a = j;
            f3908g.add(c1225a);
        }
        return c1225a;
    }

    private static C1225a m3640b(long j) {
        if (f3908g == null) {
            return null;
        }
        synchronized (f3908g) {
            Iterator it = f3908g.iterator();
            while (it.hasNext()) {
                C1225a c1225a = (C1225a) it.next();
                if (c1225a.f3894a == j) {
                    return c1225a;
                }
            }
            return null;
        }
    }

    private static C1225a m3641c(long j) {
        if (f3908g != null) {
            synchronized (f3908g) {
                for (int size = f3908g.size() - 1; size >= 0; size--) {
                    if (((C1225a) f3908g.get(size)).f3894a == j) {
                        C1225a c1225a = (C1225a) f3908g.remove(size);
                        return c1225a;
                    }
                }
            }
        }
        return null;
    }
}
