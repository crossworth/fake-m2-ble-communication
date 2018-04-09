package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.util.LongSparseArray;
import android.support.v4.view.GravityCompat;
import android.util.SparseIntArray;
import com.baidu.mapapi.map.C0495l.C0480a;
import com.baidu.mapapi.model.LatLng;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class HeatMap {
    public static final Gradient DEFAULT_GRADIENT = new Gradient(f1111d, f1112e);
    public static final double DEFAULT_OPACITY = 0.6d;
    public static final int DEFAULT_RADIUS = 12;
    private static final String f1109b = HeatMap.class.getSimpleName();
    private static final SparseIntArray f1110c = new SparseIntArray();
    private static final int[] f1111d = new int[]{Color.rgb(0, 0, 200), Color.rgb(0, 225, 0), Color.rgb(255, 0, 0)};
    private static final float[] f1112e = new float[]{0.08f, 0.4f, 1.0f};
    private static int f1113r = 0;
    BaiduMap f1114a;
    private C0495l<WeightedLatLng> f1115f;
    private Collection<WeightedLatLng> f1116g;
    private int f1117h;
    private Gradient f1118i;
    private double f1119j;
    private C0489f f1120k;
    private int[] f1121l;
    private double[] f1122m;
    private double[] f1123n;
    private HashMap<String, Tile> f1124o;
    private ExecutorService f1125p;
    private HashSet<String> f1126q;

    public static class Builder {
        private Collection<WeightedLatLng> f1105a;
        private int f1106b = 12;
        private Gradient f1107c = HeatMap.DEFAULT_GRADIENT;
        private double f1108d = HeatMap.DEFAULT_OPACITY;

        public HeatMap build() {
            if (this.f1105a != null) {
                return new HeatMap();
            }
            throw new IllegalStateException("No input data: you must use either .data or .weightedData before building");
        }

        public Builder data(Collection<LatLng> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("No input points.");
            } else if (!collection.contains(null)) {
                return weightedData(HeatMap.m1137c((Collection) collection));
            } else {
                throw new IllegalArgumentException("input points can not contain null.");
            }
        }

        public Builder gradient(Gradient gradient) {
            if (gradient == null) {
                throw new IllegalArgumentException("gradient can not be null");
            }
            this.f1107c = gradient;
            return this;
        }

        public Builder opacity(double d) {
            this.f1108d = d;
            if (this.f1108d >= 0.0d && this.f1108d <= WeightedLatLng.DEFAULT_INTENSITY) {
                return this;
            }
            throw new IllegalArgumentException("Opacity must be in range [0, 1]");
        }

        public Builder radius(int i) {
            this.f1106b = i;
            if (this.f1106b >= 10 && this.f1106b <= 50) {
                return this;
            }
            throw new IllegalArgumentException("Radius not within bounds.");
        }

        public Builder weightedData(Collection<WeightedLatLng> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("No input points.");
            } else if (collection.contains(null)) {
                throw new IllegalArgumentException("input points can not contain null.");
            } else {
                Collection arrayList = new ArrayList();
                for (WeightedLatLng weightedLatLng : collection) {
                    LatLng latLng = weightedLatLng.latLng;
                    if (latLng.latitude < 0.37532d || latLng.latitude > 54.562495d || latLng.longitude < 72.508319d || latLng.longitude > 135.942198d) {
                        arrayList.add(weightedLatLng);
                    }
                }
                collection.removeAll(arrayList);
                this.f1105a = collection;
                return this;
            }
        }
    }

    static {
        f1110c.put(3, GravityCompat.RELATIVE_LAYOUT_DIRECTION);
        f1110c.put(4, 4194304);
        f1110c.put(5, 2097152);
        f1110c.put(6, 1048576);
        f1110c.put(7, 524288);
        f1110c.put(8, 262144);
        f1110c.put(9, 131072);
        f1110c.put(10, 65536);
        f1110c.put(11, 32768);
        f1110c.put(12, 16384);
        f1110c.put(13, 8192);
        f1110c.put(14, 4096);
        f1110c.put(15, 2048);
        f1110c.put(16, 1024);
        f1110c.put(17, 512);
        f1110c.put(18, 256);
        f1110c.put(19, 128);
        f1110c.put(20, 64);
    }

    private HeatMap(Builder builder) {
        this.f1124o = new HashMap();
        this.f1125p = Executors.newFixedThreadPool(1);
        this.f1126q = new HashSet();
        this.f1116g = builder.f1105a;
        this.f1117h = builder.f1106b;
        this.f1118i = builder.f1107c;
        this.f1119j = builder.f1108d;
        this.f1122m = m1131a(this.f1117h, ((double) this.f1117h) / 3.0d);
        m1126a(this.f1118i);
        m1135b(this.f1116g);
    }

    private static double m1122a(Collection<WeightedLatLng> collection, C0489f c0489f, int i, int i2) {
        double d = c0489f.f1415a;
        double d2 = c0489f.f1417c;
        double d3 = c0489f.f1416b;
        double d4 = c0489f.f1418d;
        double d5 = ((double) ((int) (((double) (i2 / (i * 2))) + 0.5d))) / (d2 - d > d4 - d3 ? d2 - d : d4 - d3);
        LongSparseArray longSparseArray = new LongSparseArray();
        d4 = 0.0d;
        for (WeightedLatLng weightedLatLng : collection) {
            LongSparseArray longSparseArray2;
            int i3 = (int) ((((double) weightedLatLng.mo1767a().x) - d) * d5);
            int i4 = (int) ((((double) weightedLatLng.mo1767a().y) - d3) * d5);
            LongSparseArray longSparseArray3 = (LongSparseArray) longSparseArray.get((long) i3);
            if (longSparseArray3 == null) {
                longSparseArray3 = new LongSparseArray();
                longSparseArray.put((long) i3, longSparseArray3);
                longSparseArray2 = longSparseArray3;
            } else {
                longSparseArray2 = longSparseArray3;
            }
            Double d6 = (Double) longSparseArray2.get((long) i4);
            if (d6 == null) {
                d6 = Double.valueOf(0.0d);
            }
            Double valueOf = Double.valueOf(weightedLatLng.intensity + d6.doubleValue());
            longSparseArray2.put((long) i4, valueOf);
            d4 = valueOf.doubleValue() > d4 ? valueOf.doubleValue() : d4;
        }
        return d4;
    }

    private static Bitmap m1123a(double[][] dArr, int[] iArr, double d) {
        int i = iArr[iArr.length - 1];
        double length = ((double) (iArr.length - 1)) / d;
        int length2 = dArr.length;
        int[] iArr2 = new int[(length2 * length2)];
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                double d2 = dArr[i3][i2];
                int i4 = (i2 * length2) + i3;
                int i5 = (int) (d2 * length);
                if (d2 == 0.0d) {
                    iArr2[i4] = 0;
                } else if (i5 < iArr.length) {
                    iArr2[i4] = iArr[i5];
                } else {
                    iArr2[i4] = i;
                }
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(length2, length2, Config.ARGB_8888);
        createBitmap.setPixels(iArr2, 0, length2, 0, 0, length2, length2);
        return createBitmap;
    }

    private static Tile m1124a(Bitmap bitmap) {
        Buffer allocate = ByteBuffer.allocate((bitmap.getWidth() * bitmap.getHeight()) * 4);
        bitmap.copyPixelsToBuffer(allocate);
        return new Tile(256, 256, allocate.array());
    }

    private void m1126a(Gradient gradient) {
        this.f1118i = gradient;
        this.f1121l = gradient.m1115a(this.f1119j);
    }

    private synchronized void m1128a(String str, Tile tile) {
        this.f1124o.put(str, tile);
    }

    private synchronized boolean m1129a(String str) {
        return this.f1126q.contains(str);
    }

    private double[] m1130a(int i) {
        int i2 = 11;
        double[] dArr = new double[20];
        for (int i3 = 5; i3 < 11; i3++) {
            dArr[i3] = m1122a(this.f1116g, this.f1120k, i, (int) (1280.0d * Math.pow(2.0d, (double) (i3 - 3))));
            if (i3 == 5) {
                for (int i4 = 0; i4 < i3; i4++) {
                    dArr[i4] = dArr[i3];
                }
            }
        }
        while (i2 < 20) {
            dArr[i2] = dArr[10];
            i2++;
        }
        return dArr;
    }

    private static double[] m1131a(int i, double d) {
        double[] dArr = new double[((i * 2) + 1)];
        for (int i2 = -i; i2 <= i; i2++) {
            dArr[i2 + i] = Math.exp(((double) ((-i2) * i2)) / ((2.0d * d) * d));
        }
        return dArr;
    }

    private static double[][] m1132a(double[][] dArr, double[] dArr2) {
        int i;
        int i2;
        int floor = (int) Math.floor(((double) dArr2.length) / 2.0d);
        int length = dArr.length;
        int i3 = length - (floor * 2);
        int i4 = (floor + i3) - 1;
        double[][] dArr3 = (double[][]) Array.newInstance(Double.TYPE, new int[]{length, length});
        int i5 = 0;
        while (i5 < length) {
            for (i = 0; i < length; i++) {
                double d = dArr[i5][i];
                if (d != 0.0d) {
                    i2 = (i4 < i5 + floor ? i4 : i5 + floor) + 1;
                    int i6 = floor > i5 - floor ? floor : i5 - floor;
                    while (i6 < i2) {
                        double[] dArr4 = dArr3[i6];
                        dArr4[i] = dArr4[i] + (dArr2[i6 - (i5 - floor)] * d);
                        i6++;
                    }
                }
            }
            i5++;
        }
        double[][] dArr5 = (double[][]) Array.newInstance(Double.TYPE, new int[]{i3, i3});
        for (i3 = floor; i3 < i4 + 1; i3++) {
            i5 = 0;
            while (i5 < length) {
                d = dArr3[i3][i5];
                if (d != 0.0d) {
                    i2 = (i4 < i5 + floor ? i4 : i5 + floor) + 1;
                    i = floor > i5 - floor ? floor : i5 - floor;
                    while (i < i2) {
                        dArr4 = dArr5[i3 - floor];
                        int i7 = i - floor;
                        dArr4[i7] = dArr4[i7] + (dArr2[i - (i5 - floor)] * d);
                        i++;
                    }
                }
                i5++;
            }
        }
        return dArr5;
    }

    private void m1133b(int i, int i2, int i3) {
        double d = (double) f1110c.get(i3);
        double d2 = (((double) this.f1117h) * d) / 256.0d;
        double d3 = ((2.0d * d2) + d) / ((double) ((this.f1117h * 2) + 256));
        if (i >= 0 && i2 >= 0) {
            double d4 = (((double) i) * d) - d2;
            double d5 = (d * ((double) (i2 + 1))) + d2;
            C0489f c0489f = new C0489f(d4, (((double) (i + 1)) * d) + d2, (((double) i2) * d) - d2, d5);
            if (c0489f.m1298a(new C0489f(this.f1120k.f1415a - d2, this.f1120k.f1417c + d2, this.f1120k.f1416b - d2, d2 + this.f1120k.f1418d))) {
                Collection<WeightedLatLng> a = this.f1115f.m1325a(c0489f);
                if (!a.isEmpty()) {
                    double[][] dArr = (double[][]) Array.newInstance(Double.TYPE, new int[]{(this.f1117h * 2) + 256, (this.f1117h * 2) + 256});
                    for (WeightedLatLng weightedLatLng : a) {
                        Point a2 = weightedLatLng.mo1767a();
                        int i4 = (int) ((((double) a2.x) - d4) / d3);
                        int i5 = (int) ((d5 - ((double) a2.y)) / d3);
                        if (i4 >= (this.f1117h * 2) + 256) {
                            i4 = ((this.f1117h * 2) + 256) - 1;
                        }
                        if (i5 >= (this.f1117h * 2) + 256) {
                            i5 = ((this.f1117h * 2) + 256) - 1;
                        }
                        double[] dArr2 = dArr[i4];
                        dArr2[i5] = dArr2[i5] + weightedLatLng.intensity;
                    }
                    Bitmap a3 = m1123a(m1132a(dArr, this.f1122m), this.f1121l, this.f1123n[i3 - 1]);
                    Tile a4 = m1124a(a3);
                    a3.recycle();
                    m1128a(i + "_" + i2 + "_" + i3, a4);
                    if (this.f1124o.size() > f1113r) {
                        m1141a();
                    }
                    if (this.f1114a != null) {
                        this.f1114a.m1098a();
                    }
                }
            }
        }
    }

    private synchronized void m1134b(String str) {
        this.f1126q.add(str);
    }

    private void m1135b(Collection<WeightedLatLng> collection) {
        this.f1116g = collection;
        if (this.f1116g.isEmpty()) {
            throw new IllegalArgumentException("No input points.");
        }
        this.f1120k = m1138d(this.f1116g);
        this.f1115f = new C0495l(this.f1120k);
        for (C0480a a : this.f1116g) {
            this.f1115f.m1326a(a);
        }
        this.f1123n = m1130a(this.f1117h);
    }

    private synchronized Tile m1136c(String str) {
        Tile tile;
        if (this.f1124o.containsKey(str)) {
            tile = (Tile) this.f1124o.get(str);
            this.f1124o.remove(str);
        } else {
            tile = null;
        }
        return tile;
    }

    private static Collection<WeightedLatLng> m1137c(Collection<LatLng> collection) {
        Collection arrayList = new ArrayList();
        for (LatLng weightedLatLng : collection) {
            arrayList.add(new WeightedLatLng(weightedLatLng));
        }
        return arrayList;
    }

    private static C0489f m1138d(Collection<WeightedLatLng> collection) {
        Iterator it = collection.iterator();
        WeightedLatLng weightedLatLng = (WeightedLatLng) it.next();
        double d = (double) weightedLatLng.mo1767a().x;
        double d2 = (double) weightedLatLng.mo1767a().x;
        double d3 = (double) weightedLatLng.mo1767a().y;
        double d4 = (double) weightedLatLng.mo1767a().y;
        while (it.hasNext()) {
            weightedLatLng = (WeightedLatLng) it.next();
            double d5 = (double) weightedLatLng.mo1767a().x;
            double d6 = (double) weightedLatLng.mo1767a().y;
            if (d5 < d) {
                d = d5;
            }
            if (d5 > d2) {
                d2 = d5;
            }
            if (d6 < d3) {
                d3 = d6;
            }
            if (d6 > d4) {
                d4 = d6;
            }
        }
        return new C0489f(d, d2, d3, d4);
    }

    private synchronized void m1139d() {
        this.f1124o.clear();
    }

    Tile m1140a(int i, int i2, int i3) {
        String str = i + "_" + i2 + "_" + i3;
        Tile c = m1136c(str);
        if (c != null) {
            return c;
        }
        if (!m1129a(str)) {
            if (this.f1114a != null && f1113r == 0) {
                MapStatus mapStatus = this.f1114a.getMapStatus();
                f1113r = ((((mapStatus.f1153a.f1972j.f1960d - mapStatus.f1153a.f1972j.f1959c) / 256) + 2) * (((mapStatus.f1153a.f1972j.f1958b - mapStatus.f1153a.f1972j.f1957a) / 256) + 2)) * 4;
            }
            if (this.f1124o.size() > f1113r) {
                m1141a();
            }
            if (!this.f1125p.isShutdown()) {
                try {
                    this.f1125p.execute(new C0490g(this, i, i2, i3));
                    m1134b(str);
                } catch (RejectedExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    synchronized void m1141a() {
        this.f1126q.clear();
        this.f1124o.clear();
    }

    void m1142b() {
        m1139d();
    }

    void m1143c() {
        this.f1125p.shutdownNow();
    }

    public void removeHeatMap() {
        if (this.f1114a != null) {
            this.f1114a.m1099a(this);
        }
    }
}
