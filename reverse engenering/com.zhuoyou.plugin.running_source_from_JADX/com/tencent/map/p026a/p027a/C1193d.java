package com.tencent.map.p026a.p027a;

import com.tencent.map.p028b.C1222h;
import com.umeng.facebook.internal.AnalyticsEvents;
import java.util.ArrayList;
import java.util.Iterator;

public class C1193d {
    private long f3721A;
    public int f3722a;
    public double f3723b;
    public double f3724c;
    public double f3725d;
    public double f3726e;
    public double f3727f;
    public double f3728g;
    public int f3729h;
    public String f3730i;
    public String f3731j;
    public String f3732k;
    public String f3733l;
    public String f3734m;
    public String f3735n;
    public String f3736o;
    public String f3737p;
    public String f3738q;
    public String f3739r;
    public String f3740s;
    public String f3741t;
    public String f3742u;
    public String f3743v;
    public ArrayList<C1192c> f3744w;
    public boolean f3745x;
    public int f3746y;
    public int f3747z;

    public C1193d() {
        this.f3722a = 1;
        this.f3723b = 0.0d;
        this.f3724c = 0.0d;
        this.f3725d = -1.0d;
        this.f3726e = 0.0d;
        this.f3727f = 0.0d;
        this.f3728g = 0.0d;
        this.f3729h = 0;
        this.f3730i = null;
        this.f3731j = null;
        this.f3732k = null;
        this.f3733l = null;
        this.f3734m = null;
        this.f3735n = null;
        this.f3736o = null;
        this.f3737p = null;
        this.f3738q = null;
        this.f3739r = null;
        this.f3740s = null;
        this.f3741t = null;
        this.f3742u = null;
        this.f3743v = null;
        this.f3744w = null;
        this.f3745x = false;
        this.f3746y = 0;
        this.f3747z = -1;
        this.f3721A = -1;
        this.f3726e = 0.0d;
        this.f3725d = 0.0d;
        this.f3724c = 0.0d;
        this.f3723b = 0.0d;
        this.f3737p = null;
        this.f3736o = null;
        this.f3735n = null;
        this.f3734m = null;
        this.f3745x = false;
        this.f3721A = System.currentTimeMillis();
        this.f3746y = 0;
        this.f3747z = -1;
        this.f3744w = null;
    }

    public C1193d(C1193d c1193d) {
        this.f3722a = 1;
        this.f3723b = 0.0d;
        this.f3724c = 0.0d;
        this.f3725d = -1.0d;
        this.f3726e = 0.0d;
        this.f3727f = 0.0d;
        this.f3728g = 0.0d;
        this.f3729h = 0;
        this.f3730i = null;
        this.f3731j = null;
        this.f3732k = null;
        this.f3733l = null;
        this.f3734m = null;
        this.f3735n = null;
        this.f3736o = null;
        this.f3737p = null;
        this.f3738q = null;
        this.f3739r = null;
        this.f3740s = null;
        this.f3741t = null;
        this.f3742u = null;
        this.f3743v = null;
        this.f3744w = null;
        this.f3745x = false;
        this.f3746y = 0;
        this.f3747z = -1;
        this.f3721A = -1;
        this.f3722a = c1193d.f3722a;
        this.f3723b = c1193d.f3723b;
        this.f3724c = c1193d.f3724c;
        this.f3725d = c1193d.f3725d;
        this.f3726e = c1193d.f3726e;
        this.f3745x = c1193d.f3745x;
        this.f3730i = c1193d.f3730i;
        this.f3729h = 0;
        this.f3731j = c1193d.f3731j;
        this.f3732k = c1193d.f3732k;
        this.f3733l = c1193d.f3733l;
        this.f3734m = c1193d.f3734m;
        this.f3735n = c1193d.f3735n;
        this.f3736o = c1193d.f3736o;
        this.f3737p = c1193d.f3737p;
        this.f3738q = c1193d.f3738q;
        this.f3739r = c1193d.f3739r;
        this.f3740s = c1193d.f3740s;
        this.f3741t = c1193d.f3741t;
        this.f3742u = c1193d.f3742u;
        this.f3743v = c1193d.f3743v;
        this.f3721A = c1193d.m3496a();
        this.f3746y = c1193d.f3746y;
        this.f3747z = c1193d.f3747z;
        this.f3744w = null;
        if (c1193d.f3744w != null) {
            this.f3744w = new ArrayList();
            Iterator it = c1193d.f3744w.iterator();
            while (it.hasNext()) {
                this.f3744w.add((C1192c) it.next());
            }
        }
    }

    public long m3496a() {
        return this.f3721A;
    }

    public void m3497a(String str) {
        String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        this.f3733l = str2;
        this.f3732k = str2;
        this.f3731j = str2;
        this.f3730i = str2;
        if (str != null) {
            String[] split = str.split(",");
            if (split != null) {
                int length = split.length;
                if (length > 0) {
                    this.f3730i = split[0];
                }
                if (length > 1) {
                    this.f3731j = split[1];
                }
                if (length == 3) {
                    this.f3732k = split[1];
                } else if (length > 3) {
                    this.f3732k = split[2];
                }
                if (length == 3) {
                    this.f3733l = split[2];
                } else if (length > 3) {
                    this.f3733l = split[3];
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f3747z).append(" ").append(this.f3746y).append(" ").append(this.f3745x ? "Mars" : "WGS84").append(" ").append(this.f3722a == 0 ? "GPS" : "Network").append("\n");
        stringBuilder.append(this.f3723b).append(" ").append(this.f3724c).append("\n");
        stringBuilder.append(this.f3725d).append(" ").append(this.f3726e).append("\n");
        stringBuilder.append(this.f3727f).append(" ").append(this.f3728g).append("\n");
        if (this.f3747z == 3 || this.f3747z == 4) {
            stringBuilder.append(this.f3730i).append(" ").append(this.f3731j).append(" ").append(this.f3732k).append(" ").append(this.f3733l).append(" ").append(this.f3734m).append(" ").append(this.f3735n).append(" ").append(this.f3736o).append(" ").append(this.f3737p).append("\n");
        }
        if (this.f3747z == 4 && this.f3744w != null) {
            stringBuilder.append(this.f3744w.size()).append("\n");
            Iterator it = this.f3744w.iterator();
            while (it.hasNext()) {
                C1192c c1192c = (C1192c) it.next();
                stringBuilder.append(c1192c.f3715a).append(",").append(c1192c.f3716b).append(",").append(c1192c.f3717c).append(",").append(c1192c.f3718d).append(",").append(c1192c.f3719e).append(",").append(c1192c.f3720f).append("\n");
            }
        }
        if (this.f3747z == 7) {
            if (this.f3729h == 0) {
                stringBuilder.append(this.f3730i).append(" ").append(this.f3731j).append(" ").append(this.f3732k).append(" ").append(this.f3733l).append(" ").append(this.f3734m).append(" ").append(this.f3735n).append(" ").append(this.f3736o).append(" ").append(this.f3737p).append("\n");
            } else if (this.f3729h == 1) {
                stringBuilder.append(this.f3730i).append(" ").append(this.f3738q).append(" ").append(this.f3739r).append(" ").append(this.f3740s).append(" ").append(this.f3741t).append(" ").append(this.f3742u).append(" ").append(this.f3743v).append("\n");
            }
        }
        C1222h.m3619a(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
