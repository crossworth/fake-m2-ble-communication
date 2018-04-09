package com.tencent.map.p013b;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.system.network.util.NetworkConstants;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProGuard */
public final class C0727d {
    private static int f2525a;
    private static int f2526b;
    private static int f2527c;
    private static int f2528d;
    private static int f2529e;
    private static int f2530f;
    private static ArrayList<C0726a> f2531g;
    private static long f2532h;
    private static long f2533i;
    private static long f2534j;
    private static long f2535k;
    private static long f2536l;
    private static long f2537m;
    private static long f2538n;
    private static long f2539o;
    private static long f2540p;
    private static long f2541q;
    private static int f2542r;
    private static int f2543s;
    private static int f2544t;
    private static int f2545u;

    /* compiled from: ProGuard */
    public static class C0726a {
        public long f2517a;
        public long f2518b;
        public long f2519c;
        public long f2520d;
        public int f2521e;
        public long f2522f;
        public int f2523g;
        public int f2524h;
    }

    static {
        f2525a = 10000;
        f2526b = UrlConstant.SEDN_MMS_DELAY;
        f2527c = 5000;
        f2528d = 20000;
        f2529e = NetworkConstants.CONNECTION_TIMEOUT;
        f2530f = UrlConstant.SEDN_MMS_DELAY;
        f2525a = an.f2195I;
        f2526b = 20000;
        f2527c = 8000;
        f2528d = 20000;
        f2529e = NetworkConstants.CONNECTION_TIMEOUT;
        f2530f = UrlConstant.SEDN_MMS_DELAY;
        ConnectivityManager connectivityManager = (ConnectivityManager) C0754t.m2499b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                int type = activeNetworkInfo.getType();
                if (activeNetworkInfo.isConnected() && type == 0) {
                    String subscriberId = ((TelephonyManager) C0754t.m2499b().getSystemService("phone")).getSubscriberId();
                    if (subscriberId != null && subscriberId.length() > 3 && !subscriberId.startsWith("46000") && !subscriberId.startsWith("46002")) {
                        f2525a = UrlConstant.SEDN_MMS_DELAY;
                        f2526b = NetworkConstants.CONNECTION_TIMEOUT;
                        f2527c = 10000;
                        f2528d = NetworkConstants.CONNECTION_TIMEOUT;
                        f2529e = 35000;
                        f2530f = UrlConstant.SEDN_MMS_DELAY;
                    }
                }
            }
        }
    }

    public static int m2418a() {
        int i;
        int i2 = f2525a;
        if (f2534j <= 0 || f2535k <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(f2537m, f2534j) + f2535k) - f2536l);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) C0754t.m2499b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = f2526b;
                } else if (f2535k > 0 && f2535k < ((long) f2527c)) {
                    i = f2527c;
                }
            }
        }
        i2 = (f2545u * f2527c) + i;
        if (i2 <= f2527c) {
            i2 = f2527c;
        }
        if (((long) i2) <= f2535k) {
            i2 = (int) (f2535k + ((long) f2527c));
        }
        if (i2 >= f2526b) {
            i2 = f2526b;
        }
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b == null) {
            b = C0727d.m2419a(Thread.currentThread().getId());
        }
        if (i2 < b.f2523g + f2527c) {
            i2 = b.f2523g + f2527c;
        }
        b.f2523g = i2;
        return i2;
    }

    public static int m2423b() {
        int i;
        int i2 = f2528d;
        if (f2538n <= 0 || f2539o <= 0) {
            i = i2;
        } else {
            i = (int) ((Math.max(f2541q, f2538n) + f2539o) - f2540p);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) C0754t.m2499b().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (!activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) {
                    i = f2529e;
                } else if (f2539o > 0 && f2539o < ((long) f2530f)) {
                    i = f2530f;
                }
            }
        }
        i2 = (f2545u * f2527c) + i;
        if (i2 <= f2530f) {
            i2 = f2530f;
        }
        if (((long) i2) <= f2539o) {
            i2 = (int) (f2539o + ((long) f2530f));
        }
        if (i2 >= f2529e) {
            i2 = f2529e;
        }
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b != null) {
            if (i2 < b.f2524h + f2530f) {
                i2 = b.f2524h + f2530f;
            }
            if (i2 < b.f2523g + f2530f) {
                i2 = b.f2523g + f2530f;
            }
            b.f2524h = i2;
        }
        return i2;
    }

    public static void m2422a(boolean z) {
        if (!z) {
            f2545u++;
        }
        C0726a c = C0727d.m2425c(Thread.currentThread().getId());
        if (c != null) {
            long j = c.f2518b;
        }
    }

    public static void m2421a(HttpURLConnection httpURLConnection) {
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b == null) {
            b = C0727d.m2419a(Thread.currentThread().getId());
        }
        if (b != null) {
            b.f2518b = System.currentTimeMillis();
        }
    }

    public static void m2426c() {
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b != null) {
            b.f2519c = System.currentTimeMillis() - b.f2518b;
            b.f2518b = System.currentTimeMillis();
            f2537m = b.f2519c;
            f2535k = b.f2519c > f2535k ? b.f2519c : f2535k;
            long j = b.f2519c < f2536l ? b.f2519c : f2536l == 0 ? b.f2519c : f2536l;
            f2536l = j;
            if (f2531g != null) {
                synchronized (f2531g) {
                    Iterator it = f2531g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        C0726a c0726a = (C0726a) it.next();
                        if (c0726a.f2519c > 0) {
                            f2534j += c0726a.f2519c;
                            i++;
                        }
                    }
                    if (i > 0) {
                        f2534j /= (long) i;
                    }
                }
            }
        }
    }

    public static void m2427d() {
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b != null) {
            long j;
            b.f2520d = System.currentTimeMillis() - b.f2518b;
            b.f2518b = System.currentTimeMillis();
            f2541q = b.f2520d;
            if (b.f2520d > f2539o) {
                j = b.f2520d;
            } else {
                j = f2539o;
            }
            f2539o = j;
            j = b.f2520d < f2540p ? b.f2520d : f2540p == 0 ? b.f2520d : f2540p;
            f2540p = j;
            if (f2531g != null) {
                synchronized (f2531g) {
                    Iterator it = f2531g.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        C0726a c0726a = (C0726a) it.next();
                        if (c0726a.f2520d > 0) {
                            f2538n += c0726a.f2520d;
                            i++;
                        }
                    }
                    if (i > 0) {
                        f2538n /= (long) i;
                    }
                }
            }
        }
    }

    public static void m2420a(int i) {
        C0726a b = C0727d.m2424b(Thread.currentThread().getId());
        if (b != null) {
            b.f2522f = System.currentTimeMillis() - b.f2518b;
            b.f2518b = System.currentTimeMillis();
            b.f2521e = i;
            int i2 = (int) (((long) (i * 1000)) / (b.f2522f == 0 ? 1 : b.f2522f));
            f2544t = i2;
            f2542r = i2 > f2542r ? f2544t : f2542r;
            i2 = f2544t < f2543s ? f2544t : f2543s == 0 ? f2544t : f2543s;
            f2543s = i2;
            if (f2531g != null) {
                synchronized (f2531g) {
                    Iterator it = f2531g.iterator();
                    while (it.hasNext()) {
                        C0726a c0726a = (C0726a) it.next();
                        int i3 = c0726a.f2521e;
                        long j = c0726a.f2522f;
                    }
                }
            }
            if (f2545u > 0 && b.f2519c < ((long) f2527c) && b.f2520d < ((long) f2530f)) {
                f2545u--;
            }
            b.f2523g = (int) b.f2519c;
        }
    }

    private static C0726a m2419a(long j) {
        C0726a c0726a;
        if (f2531g == null) {
            f2531g = new ArrayList();
        }
        synchronized (f2531g) {
            if (f2531g.size() > 20) {
                int size = f2531g.size();
                int i = 0;
                Object obj = null;
                int i2 = 0;
                while (i < size / 2) {
                    Object obj2;
                    int i3;
                    if (((C0726a) f2531g.get(i2)).f2522f > 0 || System.currentTimeMillis() - ((C0726a) f2531g.get(i2)).f2518b > 600000) {
                        f2531g.remove(i2);
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
                    f2531g.get(0);
                    f2532h = 0;
                    f2531g.get(0);
                    f2533i = 0;
                    f2535k = ((C0726a) f2531g.get(0)).f2519c;
                    f2536l = ((C0726a) f2531g.get(0)).f2519c;
                    f2539o = ((C0726a) f2531g.get(0)).f2520d;
                    f2540p = ((C0726a) f2531g.get(0)).f2520d;
                    if (((C0726a) f2531g.get(0)).f2522f > 0) {
                        f2542r = (int) (((long) (((C0726a) f2531g.get(0)).f2521e * 1000)) / ((C0726a) f2531g.get(0)).f2522f);
                    }
                    f2543s = f2542r;
                    Iterator it = f2531g.iterator();
                    while (it.hasNext()) {
                        c0726a = (C0726a) it.next();
                        if (0 > f2532h) {
                            f2532h = 0;
                        }
                        if (0 < f2533i) {
                            f2533i = 0;
                        }
                        if (c0726a.f2519c > f2535k) {
                            f2535k = c0726a.f2519c;
                        }
                        if (c0726a.f2519c < f2536l) {
                            f2536l = c0726a.f2519c;
                        }
                        if (c0726a.f2520d > f2539o) {
                            f2539o = c0726a.f2520d;
                        }
                        if (c0726a.f2520d < f2540p) {
                            f2540p = c0726a.f2520d;
                        }
                        if (c0726a.f2522f > 0) {
                            int i4 = (int) (((long) (c0726a.f2521e * 1000)) / c0726a.f2522f);
                            if (i4 > f2542r) {
                                f2542r = i4;
                            }
                            if (i4 < f2543s) {
                                f2543s = i4;
                            }
                        }
                    }
                }
            }
            c0726a = new C0726a();
            c0726a.f2517a = j;
            f2531g.add(c0726a);
        }
        return c0726a;
    }

    private static C0726a m2424b(long j) {
        if (f2531g == null) {
            return null;
        }
        synchronized (f2531g) {
            Iterator it = f2531g.iterator();
            while (it.hasNext()) {
                C0726a c0726a = (C0726a) it.next();
                if (c0726a.f2517a == j) {
                    return c0726a;
                }
            }
            return null;
        }
    }

    private static C0726a m2425c(long j) {
        if (f2531g != null) {
            synchronized (f2531g) {
                for (int size = f2531g.size() - 1; size >= 0; size--) {
                    if (((C0726a) f2531g.get(size)).f2517a == j) {
                        C0726a c0726a = (C0726a) f2531g.remove(size);
                        return c0726a;
                    }
                }
            }
        }
        return null;
    }
}
