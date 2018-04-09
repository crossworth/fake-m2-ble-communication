package com.tencent.map.p011a.p012a;

import com.facebook.internal.AnalyticsEvents;
import com.tencent.map.p013b.C0751q;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.Iterator;

public class C0717d {
    private long f2466A;
    public int f2467a;
    public double f2468b;
    public double f2469c;
    public double f2470d;
    public double f2471e;
    public double f2472f;
    public double f2473g;
    public int f2474h;
    public String f2475i;
    public String f2476j;
    public String f2477k;
    public String f2478l;
    public String f2479m;
    public String f2480n;
    public String f2481o;
    public String f2482p;
    public String f2483q;
    public String f2484r;
    public String f2485s;
    public String f2486t;
    public String f2487u;
    public String f2488v;
    public ArrayList<C0716c> f2489w;
    public boolean f2490x;
    public int f2491y;
    public int f2492z;

    public C0717d() {
        this.f2467a = 1;
        this.f2468b = 0.0d;
        this.f2469c = 0.0d;
        this.f2470d = -1.0d;
        this.f2471e = 0.0d;
        this.f2472f = 0.0d;
        this.f2473g = 0.0d;
        this.f2474h = 0;
        this.f2475i = null;
        this.f2476j = null;
        this.f2477k = null;
        this.f2478l = null;
        this.f2479m = null;
        this.f2480n = null;
        this.f2481o = null;
        this.f2482p = null;
        this.f2483q = null;
        this.f2484r = null;
        this.f2485s = null;
        this.f2486t = null;
        this.f2487u = null;
        this.f2488v = null;
        this.f2489w = null;
        this.f2490x = false;
        this.f2491y = 0;
        this.f2492z = -1;
        this.f2466A = -1;
        this.f2471e = 0.0d;
        this.f2470d = 0.0d;
        this.f2469c = 0.0d;
        this.f2468b = 0.0d;
        this.f2482p = null;
        this.f2481o = null;
        this.f2480n = null;
        this.f2479m = null;
        this.f2490x = false;
        this.f2466A = System.currentTimeMillis();
        this.f2491y = 0;
        this.f2492z = -1;
        this.f2489w = null;
    }

    public C0717d(C0717d c0717d) {
        this.f2467a = 1;
        this.f2468b = 0.0d;
        this.f2469c = 0.0d;
        this.f2470d = -1.0d;
        this.f2471e = 0.0d;
        this.f2472f = 0.0d;
        this.f2473g = 0.0d;
        this.f2474h = 0;
        this.f2475i = null;
        this.f2476j = null;
        this.f2477k = null;
        this.f2478l = null;
        this.f2479m = null;
        this.f2480n = null;
        this.f2481o = null;
        this.f2482p = null;
        this.f2483q = null;
        this.f2484r = null;
        this.f2485s = null;
        this.f2486t = null;
        this.f2487u = null;
        this.f2488v = null;
        this.f2489w = null;
        this.f2490x = false;
        this.f2491y = 0;
        this.f2492z = -1;
        this.f2466A = -1;
        this.f2467a = c0717d.f2467a;
        this.f2468b = c0717d.f2468b;
        this.f2469c = c0717d.f2469c;
        this.f2470d = c0717d.f2470d;
        this.f2471e = c0717d.f2471e;
        this.f2490x = c0717d.f2490x;
        this.f2475i = c0717d.f2475i;
        this.f2474h = 0;
        this.f2476j = c0717d.f2476j;
        this.f2477k = c0717d.f2477k;
        this.f2478l = c0717d.f2478l;
        this.f2479m = c0717d.f2479m;
        this.f2480n = c0717d.f2480n;
        this.f2481o = c0717d.f2481o;
        this.f2482p = c0717d.f2482p;
        this.f2483q = c0717d.f2483q;
        this.f2484r = c0717d.f2484r;
        this.f2485s = c0717d.f2485s;
        this.f2486t = c0717d.f2486t;
        this.f2487u = c0717d.f2487u;
        this.f2488v = c0717d.f2488v;
        this.f2466A = c0717d.m2397a();
        this.f2491y = c0717d.f2491y;
        this.f2492z = c0717d.f2492z;
        this.f2489w = null;
        if (c0717d.f2489w != null) {
            this.f2489w = new ArrayList();
            Iterator it = c0717d.f2489w.iterator();
            while (it.hasNext()) {
                this.f2489w.add((C0716c) it.next());
            }
        }
    }

    public long m2397a() {
        return this.f2466A;
    }

    public void m2398a(String str) {
        String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        this.f2478l = str2;
        this.f2477k = str2;
        this.f2476j = str2;
        this.f2475i = str2;
        if (str != null) {
            String[] split = str.split(SeparatorConstants.SEPARATOR_ADS_ID);
            if (split != null) {
                int length = split.length;
                if (length > 0) {
                    this.f2475i = split[0];
                }
                if (length > 1) {
                    this.f2476j = split[1];
                }
                if (length == 3) {
                    this.f2477k = split[1];
                } else if (length > 3) {
                    this.f2477k = split[2];
                }
                if (length == 3) {
                    this.f2478l = split[2];
                } else if (length > 3) {
                    this.f2478l = split[3];
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f2492z).append(" ").append(this.f2491y).append(" ").append(this.f2490x ? "Mars" : "WGS84").append(" ").append(this.f2467a == 0 ? "GPS" : "Network").append("\n");
        stringBuilder.append(this.f2468b).append(" ").append(this.f2469c).append("\n");
        stringBuilder.append(this.f2470d).append(" ").append(this.f2471e).append("\n");
        stringBuilder.append(this.f2472f).append(" ").append(this.f2473g).append("\n");
        if (this.f2492z == 3 || this.f2492z == 4) {
            stringBuilder.append(this.f2475i).append(" ").append(this.f2476j).append(" ").append(this.f2477k).append(" ").append(this.f2478l).append(" ").append(this.f2479m).append(" ").append(this.f2480n).append(" ").append(this.f2481o).append(" ").append(this.f2482p).append("\n");
        }
        if (this.f2492z == 4 && this.f2489w != null) {
            stringBuilder.append(this.f2489w.size()).append("\n");
            Iterator it = this.f2489w.iterator();
            while (it.hasNext()) {
                C0716c c0716c = (C0716c) it.next();
                stringBuilder.append(c0716c.f2460a).append(SeparatorConstants.SEPARATOR_ADS_ID).append(c0716c.f2461b).append(SeparatorConstants.SEPARATOR_ADS_ID).append(c0716c.f2462c).append(SeparatorConstants.SEPARATOR_ADS_ID).append(c0716c.f2463d).append(SeparatorConstants.SEPARATOR_ADS_ID).append(c0716c.f2464e).append(SeparatorConstants.SEPARATOR_ADS_ID).append(c0716c.f2465f).append("\n");
            }
        }
        if (this.f2492z == 7) {
            if (this.f2474h == 0) {
                stringBuilder.append(this.f2475i).append(" ").append(this.f2476j).append(" ").append(this.f2477k).append(" ").append(this.f2478l).append(" ").append(this.f2479m).append(" ").append(this.f2480n).append(" ").append(this.f2481o).append(" ").append(this.f2482p).append("\n");
            } else if (this.f2474h == 1) {
                stringBuilder.append(this.f2475i).append(" ").append(this.f2483q).append(" ").append(this.f2484r).append(" ").append(this.f2485s).append(" ").append(this.f2486t).append(" ").append(this.f2487u).append(" ").append(this.f2488v).append("\n");
            }
        }
        C0751q.m2483a(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
