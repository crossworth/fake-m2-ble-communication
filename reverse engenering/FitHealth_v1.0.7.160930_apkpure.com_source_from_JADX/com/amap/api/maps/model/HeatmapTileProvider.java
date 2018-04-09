package com.amap.api.maps.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.support.v4.util.LongSparseArray;
import android.util.Log;
import com.amap.api.mapcore.util.cy;
import com.amap.api.maps.AMapException;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.MapTilsCacheAndResManager;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HeatmapTileProvider implements TileProvider {
    public static final Gradient DEFAULT_GRADIENT = new Gradient(f4229a, f4230b);
    public static final double DEFAULT_OPACITY = 0.6d;
    public static final int DEFAULT_RADIUS = 12;
    private static final int[] f4229a = new int[]{Color.rgb(102, 225, 0), Color.rgb(255, 0, 0)};
    private static final float[] f4230b = new float[]{0.2f, 1.0f};
    private C0302c f4231c;
    private Collection<WeightedLatLng> f4232d;
    private cy f4233e;
    private int f4234f;
    private Gradient f4235g;
    private int[] f4236h;
    private double[] f4237i;
    private double f4238j;
    private double[] f4239k;

    public static class Builder {
        private Collection<WeightedLatLng> f865a;
        private int f866b = 12;
        private Gradient f867c = HeatmapTileProvider.DEFAULT_GRADIENT;
        private double f868d = HeatmapTileProvider.DEFAULT_OPACITY;

        public Builder data(Collection<LatLng> collection) {
            return weightedData(HeatmapTileProvider.m4314d(collection));
        }

        public Builder weightedData(Collection<WeightedLatLng> collection) {
            this.f865a = collection;
            return this;
        }

        public Builder radius(int i) {
            this.f866b = Math.max(10, Math.min(i, 50));
            return this;
        }

        public Builder gradient(Gradient gradient) {
            this.f867c = gradient;
            return this;
        }

        public Builder transparency(double d) {
            this.f868d = Math.max(0.0d, Math.min(d, WeightedLatLng.DEFAULT_INTENSITY));
            return this;
        }

        public HeatmapTileProvider build() {
            if (this.f865a != null && this.f865a.size() != 0) {
                return new HeatmapTileProvider();
            }
            try {
                throw new AMapException("No input points.");
            } catch (AMapException e) {
                Log.e(MapTilsCacheAndResManager.AUTONAVI_PATH, e.getErrorMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    private HeatmapTileProvider(Builder builder) {
        this.f4232d = builder.f865a;
        this.f4234f = builder.f866b;
        this.f4235g = builder.f867c;
        if (this.f4235g == null || !this.f4235g.isAvailable()) {
            this.f4235g = DEFAULT_GRADIENT;
        }
        this.f4238j = builder.f868d;
        this.f4237i = m4310a(this.f4234f, ((double) this.f4234f) / 3.0d);
        m4308a(this.f4235g);
        m4313c(this.f4232d);
    }

    private void m4313c(Collection<WeightedLatLng> collection) {
        Collection arrayList = new ArrayList();
        for (WeightedLatLng weightedLatLng : collection) {
            if (weightedLatLng.latLng.latitude < 85.0d && weightedLatLng.latLng.latitude > -85.0d) {
                arrayList.add(weightedLatLng);
            }
        }
        this.f4232d = arrayList;
        this.f4233e = m4306a(this.f4232d);
        this.f4231c = new C0302c(this.f4233e);
        for (WeightedLatLng weightedLatLng2 : this.f4232d) {
            this.f4231c.m1107a(weightedLatLng2);
        }
        this.f4239k = m4309a(this.f4234f);
    }

    private static Collection<WeightedLatLng> m4314d(Collection<LatLng> collection) {
        Collection arrayList = new ArrayList();
        for (LatLng weightedLatLng : collection) {
            arrayList.add(new WeightedLatLng(weightedLatLng));
        }
        return arrayList;
    }

    public Tile getTile(int i, int i2, int i3) {
        double d;
        double pow = WeightedLatLng.DEFAULT_INTENSITY / Math.pow(2.0d, (double) i3);
        double d2 = (((double) this.f4234f) * pow) / 256.0d;
        double d3 = ((2.0d * d2) + pow) / ((double) ((this.f4234f * 2) + 256));
        double d4 = (((double) i) * pow) - d2;
        double d5 = (((double) (i + 1)) * pow) + d2;
        double d6 = (((double) i2) * pow) - d2;
        double d7 = (pow * ((double) (i2 + 1))) + d2;
        ArrayList arrayList = new ArrayList();
        Collection a;
        if (d4 < 0.0d) {
            a = this.f4231c.m1106a(new cy(WeightedLatLng.DEFAULT_INTENSITY + d4, WeightedLatLng.DEFAULT_INTENSITY, d6, d7));
            d = -1.0d;
        } else if (d5 > WeightedLatLng.DEFAULT_INTENSITY) {
            a = this.f4231c.m1106a(new cy(0.0d, d5 - WeightedLatLng.DEFAULT_INTENSITY, d6, d7));
            d = WeightedLatLng.DEFAULT_INTENSITY;
        } else {
            Object obj = arrayList;
            d = 0.0d;
        }
        cy cyVar = new cy(d4, d5, d6, d7);
        if (!cyVar.m475a(new cy(this.f4233e.f406a - d2, this.f4233e.f408c + d2, this.f4233e.f407b - d2, d2 + this.f4233e.f409d))) {
            return TileProvider.NO_TILE;
        }
        Collection<WeightedLatLng> a2 = this.f4231c.m1106a(cyVar);
        if (a2.isEmpty()) {
            return TileProvider.NO_TILE;
        }
        double[][] dArr = (double[][]) Array.newInstance(Double.TYPE, new int[]{(this.f4234f * 2) + 256, (this.f4234f * 2) + 256});
        for (WeightedLatLng weightedLatLng : a2) {
            DPoint point = weightedLatLng.getPoint();
            int i4 = (int) ((point.f2026x - d4) / d3);
            int i5 = (int) ((point.f2027y - d6) / d3);
            double[] dArr2 = dArr[i4];
            dArr2[i5] = dArr2[i5] + weightedLatLng.intensity;
        }
        for (WeightedLatLng weightedLatLng2 : r20) {
            point = weightedLatLng2.getPoint();
            i4 = (int) (((point.f2026x + d) - d4) / d3);
            i5 = (int) ((point.f2027y - d6) / d3);
            dArr2 = dArr[i4];
            dArr2[i5] = dArr2[i5] + weightedLatLng2.intensity;
        }
        return m4307a(m4305a(m4311a(dArr, this.f4237i), this.f4236h, this.f4239k[i3]));
    }

    private void m4308a(Gradient gradient) {
        this.f4235g = gradient;
        this.f4236h = gradient.generateColorMap(this.f4238j);
    }

    private double[] m4309a(int i) {
        int i2 = 11;
        double[] dArr = new double[21];
        for (int i3 = 5; i3 < 11; i3++) {
            dArr[i3] = m4304a(this.f4232d, this.f4233e, i, (int) (1280.0d * Math.pow(2.0d, (double) i3)));
            if (i3 == 5) {
                for (int i4 = 0; i4 < i3; i4++) {
                    dArr[i4] = dArr[i3];
                }
            }
        }
        while (i2 < 21) {
            dArr[i2] = dArr[10];
            i2++;
        }
        return dArr;
    }

    private static Tile m4307a(Bitmap bitmap) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return new Tile(256, 256, byteArrayOutputStream.toByteArray());
    }

    static cy m4306a(Collection<WeightedLatLng> collection) {
        Iterator it = collection.iterator();
        WeightedLatLng weightedLatLng = (WeightedLatLng) it.next();
        double d = weightedLatLng.getPoint().f2026x;
        double d2 = weightedLatLng.getPoint().f2026x;
        double d3 = weightedLatLng.getPoint().f2027y;
        double d4 = weightedLatLng.getPoint().f2027y;
        while (it.hasNext()) {
            weightedLatLng = (WeightedLatLng) it.next();
            double d5 = weightedLatLng.getPoint().f2026x;
            double d6 = weightedLatLng.getPoint().f2027y;
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
        return new cy(d, d2, d3, d4);
    }

    static double[] m4310a(int i, double d) {
        double[] dArr = new double[((i * 2) + 1)];
        for (int i2 = -i; i2 <= i; i2++) {
            dArr[i2 + i] = Math.exp(((double) ((-i2) * i2)) / ((2.0d * d) * d));
        }
        return dArr;
    }

    static double[][] m4311a(double[][] dArr, double[] dArr2) {
        int floor = (int) Math.floor(((double) dArr2.length) / 2.0d);
        int length = dArr.length;
        int i = length - (floor * 2);
        int i2 = (floor + i) - 1;
        double[][] dArr3 = (double[][]) Array.newInstance(Double.TYPE, new int[]{length, length});
        int i3 = 0;
        while (i3 < length) {
            int i4;
            for (i4 = 0; i4 < length; i4++) {
                int i5;
                double d = dArr[i3][i4];
                if (d != 0.0d) {
                    i5 = (i2 < i3 + floor ? i2 : i3 + floor) + 1;
                    int i6 = floor > i3 - floor ? floor : i3 - floor;
                    while (i6 < i5) {
                        double[] dArr4 = dArr3[i6];
                        dArr4[i4] = dArr4[i4] + (dArr2[i6 - (i3 - floor)] * d);
                        i6++;
                    }
                }
            }
            i3++;
        }
        double[][] dArr5 = (double[][]) Array.newInstance(Double.TYPE, new int[]{i, i});
        for (i = floor; i < i2 + 1; i++) {
            i3 = 0;
            while (i3 < length) {
                d = dArr3[i][i3];
                if (d != 0.0d) {
                    i5 = (i2 < i3 + floor ? i2 : i3 + floor) + 1;
                    i4 = floor > i3 - floor ? floor : i3 - floor;
                    while (i4 < i5) {
                        dArr4 = dArr5[i - floor];
                        int i7 = i4 - floor;
                        dArr4[i7] = dArr4[i7] + (dArr2[i4 - (i3 - floor)] * d);
                        i4++;
                    }
                }
                i3++;
            }
        }
        return dArr5;
    }

    static Bitmap m4305a(double[][] dArr, int[] iArr, double d) {
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

    static double m4304a(Collection<WeightedLatLng> collection, cy cyVar, int i, int i2) {
        double d = cyVar.f406a;
        double d2 = cyVar.f408c;
        double d3 = cyVar.f407b;
        double d4 = cyVar.f409d;
        double d5 = ((double) ((int) (((double) (i2 / (i * 2))) + 0.5d))) / (d2 - d > d4 - d3 ? d2 - d : d4 - d3);
        LongSparseArray longSparseArray = new LongSparseArray();
        d4 = 0.0d;
        for (WeightedLatLng weightedLatLng : collection) {
            LongSparseArray longSparseArray2;
            int i3 = (int) ((weightedLatLng.getPoint().f2026x - d) * d5);
            int i4 = (int) ((weightedLatLng.getPoint().f2027y - d3) * d5);
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
            if (valueOf.doubleValue() > d4) {
                d2 = valueOf.doubleValue();
            } else {
                d2 = d4;
            }
            d4 = d2;
        }
        return d4;
    }

    public int getTileHeight() {
        return 256;
    }

    public int getTileWidth() {
        return 256;
    }
}
