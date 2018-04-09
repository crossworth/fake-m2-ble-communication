package com.baidu.mapapi.map;

import android.graphics.Color;
import java.util.HashMap;

public class Gradient {
    private final int f1081a;
    private final int[] f1082b;
    private final float[] f1083c;

    private class C0476a {
        final /* synthetic */ Gradient f1077a;
        private final int f1078b;
        private final int f1079c;
        private final float f1080d;

        private C0476a(Gradient gradient, int i, int i2, float f) {
            this.f1077a = gradient;
            this.f1078b = i;
            this.f1079c = i2;
            this.f1080d = f;
        }
    }

    public Gradient(int[] iArr, float[] fArr) {
        this(iArr, fArr, 1000);
    }

    private Gradient(int[] iArr, float[] fArr, int i) {
        if (iArr == null || fArr == null) {
            throw new IllegalArgumentException("colors and startPoints should not be null");
        } else if (iArr.length != fArr.length) {
            throw new IllegalArgumentException("colors and startPoints should be same length");
        } else if (iArr.length == 0) {
            throw new IllegalArgumentException("No colors have been defined");
        } else {
            for (int i2 = 1; i2 < fArr.length; i2++) {
                if (fArr[i2] <= fArr[i2 - 1]) {
                    throw new IllegalArgumentException("startPoints should be in increasing order");
                }
            }
            this.f1081a = i;
            this.f1082b = new int[iArr.length];
            this.f1083c = new float[fArr.length];
            System.arraycopy(iArr, 0, this.f1082b, 0, iArr.length);
            System.arraycopy(fArr, 0, this.f1083c, 0, fArr.length);
        }
    }

    private static int m1113a(int i, int i2, float f) {
        int i3 = 0;
        int alpha = (int) ((((float) (Color.alpha(i2) - Color.alpha(i))) * f) + ((float) Color.alpha(i)));
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        float[] fArr2 = new float[3];
        Color.RGBToHSV(Color.red(i2), Color.green(i2), Color.blue(i2), fArr2);
        if (fArr[0] - fArr2[0] > 180.0f) {
            fArr2[0] = fArr2[0] + 360.0f;
        } else if (fArr2[0] - fArr[0] > 180.0f) {
            fArr[0] = fArr[0] + 360.0f;
        }
        float[] fArr3 = new float[3];
        while (i3 < 3) {
            fArr3[i3] = ((fArr2[i3] - fArr[i3]) * f) + fArr[i3];
            i3++;
        }
        return Color.HSVToColor(alpha, fArr3);
    }

    private HashMap<Integer, C0476a> m1114a() {
        HashMap<Integer, C0476a> hashMap = new HashMap();
        if (this.f1083c[0] != 0.0f) {
            int argb = Color.argb(0, Color.red(this.f1082b[0]), Color.green(this.f1082b[0]), Color.blue(this.f1082b[0]));
            hashMap.put(Integer.valueOf(0), new C0476a(argb, this.f1082b[0], this.f1083c[0] * ((float) this.f1081a)));
        }
        for (int i = 1; i < this.f1082b.length; i++) {
            hashMap.put(Integer.valueOf((int) (((float) this.f1081a) * this.f1083c[i - 1])), new C0476a(this.f1082b[i - 1], this.f1082b[i], (this.f1083c[i] - this.f1083c[i - 1]) * ((float) this.f1081a)));
        }
        if (this.f1083c[this.f1083c.length - 1] != 1.0f) {
            int length = this.f1083c.length - 1;
            hashMap.put(Integer.valueOf((int) (((float) this.f1081a) * this.f1083c[length])), new C0476a(this.f1082b[length], this.f1082b[length], ((float) this.f1081a) * (1.0f - this.f1083c[length])));
        }
        return hashMap;
    }

    int[] m1115a(double d) {
        int i = 0;
        HashMap a = m1114a();
        int[] iArr = new int[this.f1081a];
        int i2 = 0;
        C0476a c0476a = (C0476a) a.get(Integer.valueOf(0));
        int i3 = 0;
        while (i2 < this.f1081a) {
            int i4;
            C0476a c0476a2;
            if (a.containsKey(Integer.valueOf(i2))) {
                i4 = i2;
                c0476a2 = (C0476a) a.get(Integer.valueOf(i2));
            } else {
                c0476a2 = c0476a;
                i4 = i3;
            }
            iArr[i2] = m1113a(c0476a2.f1078b, c0476a2.f1079c, ((float) (i2 - i4)) / c0476a2.f1080d);
            i2++;
            i3 = i4;
            c0476a = c0476a2;
        }
        if (d != WeightedLatLng.DEFAULT_INTENSITY) {
            while (i < this.f1081a) {
                i3 = iArr[i];
                iArr[i] = Color.argb((int) (((double) Color.alpha(i3)) * d), Color.red(i3), Color.green(i3), Color.blue(i3));
                i++;
            }
        }
        return iArr;
    }
}
