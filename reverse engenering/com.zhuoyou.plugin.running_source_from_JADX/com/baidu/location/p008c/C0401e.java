package com.baidu.location.p008c;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.Timer;

public class C0401e {
    private double f514A;
    private double f515B;
    private double f516C;
    private double f517D;
    private double f518E;
    private double f519F;
    private double f520G;
    private int f521H;
    private float f522I;
    private int f523J;
    private int f524K;
    private double[] f525L;
    private boolean f526M;
    Timer f527a;
    public SensorEventListener f528b;
    private C0399a f529c;
    private SensorManager f530d;
    private boolean f531e;
    private int f532f;
    private Sensor f533g;
    private Sensor f534h;
    private final long f535i;
    private volatile int f536j;
    private int f537k;
    private float[] f538l;
    private float[] f539m;
    private double[] f540n;
    private int f541o;
    private double[] f542p;
    private int f543q;
    private int f544r;
    private int f545s;
    private double[] f546t;
    private int f547u;
    private double f548v;
    private int f549w;
    private long f550x;
    private int f551y;
    private int f552z;

    public interface C0399a {
        void mo1749a(double d, double d2);
    }

    private C0401e(Context context, int i) {
        this.f535i = 30;
        this.f536j = 1;
        this.f537k = 1;
        this.f538l = new float[3];
        this.f539m = new float[]{0.0f, 0.0f, 0.0f};
        this.f540n = new double[]{0.0d, 0.0d, 0.0d};
        this.f541o = 31;
        this.f542p = new double[this.f541o];
        this.f543q = 0;
        this.f546t = new double[6];
        this.f547u = 0;
        this.f550x = 0;
        this.f551y = 0;
        this.f552z = 0;
        this.f514A = 0.0d;
        this.f515B = 0.0d;
        this.f516C = 100.0d;
        this.f517D = 0.5d;
        this.f518E = this.f517D;
        this.f519F = 0.85d;
        this.f520G = 0.42d;
        this.f521H = -1;
        this.f522I = 0.0f;
        this.f523J = 20;
        this.f524K = 0;
        this.f525L = new double[this.f523J];
        this.f526M = false;
        this.f528b = new C0402f(this);
        this.f548v = 1.6d;
        this.f549w = 440;
        try {
            this.f530d = (SensorManager) context.getSystemService("sensor");
            this.f532f = i;
            this.f533g = this.f530d.getDefaultSensor(1);
            this.f534h = this.f530d.getDefaultSensor(3);
        } catch (Exception e) {
        }
    }

    public C0401e(Context context, C0399a c0399a) {
        this(context, 1);
        this.f529c = c0399a;
    }

    private double m592a(double d, double d2, double d3) {
        double d4 = d2 - d;
        if (d4 < -180.0d) {
            d4 += 360.0d;
        } else if (d4 > 180.0d) {
            d4 -= 360.0d;
        }
        return (d4 * d3) + d;
    }

    private double m594a(double[] dArr) {
        int i = 0;
        double d = 0.0d;
        double d2 = 0.0d;
        for (double d3 : dArr) {
            d2 += d3;
        }
        d2 /= (double) r6;
        while (i < r6) {
            d += (dArr[i] - d2) * (dArr[i] - d2);
            i++;
        }
        return d / ((double) (r6 - 1));
    }

    static /* synthetic */ int m595a(C0401e c0401e) {
        int i = c0401e.f544r + 1;
        c0401e.f544r = i;
        return i;
    }

    private void m597a(double d) {
        this.f546t[this.f547u % 6] = d;
        this.f547u++;
        this.f547u %= 6;
    }

    private synchronized void m598a(int i) {
        this.f537k |= i;
    }

    private float[] m600a(float f, float f2, float f3) {
        float[] fArr = new float[]{(this.f538l[0] * 0.8f) + (0.19999999f * f), (this.f538l[1] * 0.8f) + (0.19999999f * f2), (this.f538l[2] * 0.8f) + (0.19999999f * f3)};
        fArr[0] = f - this.f538l[0];
        fArr[1] = f2 - this.f538l[1];
        fArr[2] = f3 - this.f538l[2];
        return fArr;
    }

    private boolean m605b(double d) {
        for (int i = 1; i <= 5; i++) {
            if (this.f546t[((((this.f547u - 1) - i) + 6) + 6) % 6] - this.f546t[((this.f547u - 1) + 6) % 6] > d) {
                return true;
            }
        }
        return false;
    }

    private boolean m610f() {
        for (int i = 0; i < this.f523J; i++) {
            if (this.f525L[i] > 1.0E-7d) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ int m611g(C0401e c0401e) {
        int i = c0401e.f545s + 1;
        c0401e.f545s = i;
        return i;
    }

    private void m612g() {
        if (this.f544r >= 20) {
            long currentTimeMillis = System.currentTimeMillis();
            Object obj = new float[3];
            System.arraycopy(this.f539m, 0, obj, 0, 3);
            Object obj2 = new double[3];
            System.arraycopy(this.f540n, 0, obj2, 0, 3);
            double sqrt = Math.sqrt((double) ((obj[2] * obj[2]) + ((obj[0] * obj[0]) + (obj[1] * obj[1]))));
            this.f542p[this.f543q] = sqrt;
            m597a(sqrt);
            this.f552z++;
            if (sqrt > this.f515B) {
                this.f515B = sqrt;
            } else if (sqrt < this.f516C) {
                this.f516C = sqrt;
            }
            this.f543q++;
            if (this.f543q == this.f541o) {
                this.f543q = 0;
                double a = m594a(this.f542p);
                if (this.f536j != 0 || a >= 0.3d) {
                    m598a(1);
                    this.f536j = 1;
                } else {
                    m598a(0);
                    this.f536j = 0;
                }
            }
            if (currentTimeMillis - this.f550x > ((long) this.f549w) && m605b(this.f548v)) {
                this.f551y++;
                this.f550x = currentTimeMillis;
                double d = obj2[0];
                if (this.f552z >= 40 || this.f552z <= 0) {
                    this.f518E = this.f517D;
                } else {
                    this.f518E = Math.sqrt(Math.sqrt(this.f515B - this.f516C)) * this.f520G;
                    if (this.f518E > this.f519F) {
                        this.f518E = this.f519F;
                    } else if (this.f518E < this.f517D) {
                        this.f518E = this.f517D;
                    }
                }
                d += (double) this.f522I;
                if (d > 360.0d) {
                    d -= 360.0d;
                }
                if (d < 0.0d) {
                    d += 360.0d;
                }
                this.f552z = 1;
                this.f515B = sqrt;
                this.f516C = sqrt;
                if (this.f526M) {
                    this.f529c.mo1749a(this.f518E, d);
                }
            }
        }
    }

    public void m619a() {
        if (!this.f531e) {
            if (this.f533g != null) {
                try {
                    this.f530d.registerListener(this.f528b, this.f533g, this.f532f);
                } catch (Exception e) {
                }
                this.f527a = new Timer("UpdateData", false);
                this.f527a.schedule(new C0403g(this), 500, 30);
                this.f531e = true;
            }
            if (this.f534h != null) {
                try {
                    this.f530d.registerListener(this.f528b, this.f534h, this.f532f);
                } catch (Exception e2) {
                }
            }
        }
    }

    public void m620b() {
        if (this.f531e) {
            try {
                this.f530d.unregisterListener(this.f528b);
            } catch (Exception e) {
            }
            this.f527a.cancel();
            this.f527a.purge();
            this.f527a = null;
            this.f531e = false;
        }
    }

    public synchronized int m621c() {
        return this.f544r < 20 ? 1 : this.f537k;
    }

    public synchronized int m622d() {
        return this.f544r < 20 ? -1 : this.f551y;
    }

    public synchronized void m623e() {
        this.f537k = 0;
    }
}
