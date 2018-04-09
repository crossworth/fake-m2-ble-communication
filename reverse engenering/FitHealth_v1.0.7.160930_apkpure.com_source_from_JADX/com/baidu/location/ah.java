package com.baidu.location;

import android.text.TextUtils;

public class ah implements BDGeofence {
    private static final String f4459byte = "Administrative";
    private static final int f4460case = 2;
    private static final int f4461d = 100;
    private static final int f4462e = 2;
    private static final int f4463else = 1;
    private static final String f4464for = "Circle";
    private static final int f4465h = 3;
    public static final int f4466int = 1;
    private static final long f4467void = 2592000;
    private final int f4468a;
    private float f4469b;
    private final int f4470c;
    private boolean f4471char;
    private final String f4472do;
    private boolean f4473f;
    private long f4474g;
    private final double f4475goto;
    private boolean f4476if;
    private final long f4477long;
    private final String f4478new;
    private final double f4479try;

    public ah(int i, String str, double d, double d2, int i2, long j, String str2) {
        m4575if(i2);
        m4577if(str);
        m4573a(d, d2);
        m4574a(str2);
        m4576if(j);
        this.f4470c = i;
        this.f4472do = str;
        this.f4475goto = d;
        this.f4479try = d2;
        this.f4468a = i2;
        this.f4477long = j;
        this.f4478new = str2;
    }

    public ah(String str, double d, double d2, int i, long j, String str2) {
        this(1, str, d2, d, i, j, str2);
    }

    private static String m4572a(int i) {
        switch (i) {
            case 1:
                return f4464for;
            case 2:
                return f4459byte;
            default:
                return null;
        }
    }

    private static void m4573a(double d, double d2) {
    }

    private static void m4574a(String str) {
        if (!str.equals(BDGeofence.COORD_TYPE_BD09) && !str.equals(BDGeofence.COORD_TYPE_BD09LL) && !str.equals(BDGeofence.COORD_TYPE_GCJ)) {
            throw new IllegalArgumentException("invalid coord type: " + str);
        }
    }

    private static void m4575if(int i) {
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException("invalid radius type: " + i);
        }
    }

    private static void m4576if(long j) {
        if (((double) j) / 1000.0d > 2592000.0d) {
            throw new IllegalArgumentException("invalid druationMillis :" + j);
        }
    }

    private static void m4577if(String str) {
        if (TextUtils.isEmpty(str) || str.length() > 100) {
            throw new IllegalArgumentException("Geofence name is null or too long: " + str);
        }
    }

    public double m4578a() {
        return this.f4479try;
    }

    public void m4579a(float f) {
        this.f4469b = f;
    }

    public void m4580a(long j) {
        this.f4474g = j;
    }

    public void m4581a(boolean z) {
        this.f4476if = z;
    }

    public double m4582byte() {
        return this.f4475goto;
    }

    public int m4583case() {
        return this.f4471char ? 1 : this.f4476if ? 2 : 3;
    }

    public long m4584char() {
        return this.f4474g;
    }

    public float m4585do() {
        return this.f4469b;
    }

    public void m4586do(boolean z) {
        this.f4473f = z;
    }

    public long m4587else() {
        return this.f4477long;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ah)) {
            return false;
        }
        ah ahVar = (ah) obj;
        return this.f4468a != ahVar.f4468a ? false : this.f4475goto != ahVar.f4475goto ? false : this.f4479try != ahVar.f4479try ? false : this.f4470c != ahVar.f4470c ? false : this.f4478new == ahVar.f4478new;
    }

    public boolean m4588for() {
        return this.f4471char;
    }

    public String getGeofenceId() {
        return this.f4472do;
    }

    public void m4589if(boolean z) {
        this.f4471char = z;
    }

    public boolean m4590if() {
        return this.f4476if;
    }

    public String m4591int() {
        return this.f4478new;
    }

    public int m4592new() {
        return this.f4468a;
    }

    public String toString() {
        return String.format("Geofence[Type:%s, Name:%s, latitude:%.6f, longitude:%.6f, radius:%.0f, expriation:%d, coordType:%s, fenceType:%d]", new Object[]{m4572a(this.f4470c), this.f4472do, Double.valueOf(this.f4475goto), Double.valueOf(this.f4479try), Float.valueOf(this.f4469b), Long.valueOf(this.f4477long), this.f4478new, Integer.valueOf(m4583case())});
    }

    public boolean m4593try() {
        return this.f4473f;
    }
}
