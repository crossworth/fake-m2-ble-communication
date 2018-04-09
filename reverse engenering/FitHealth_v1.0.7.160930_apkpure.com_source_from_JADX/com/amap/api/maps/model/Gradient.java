package com.amap.api.maps.model;

import android.graphics.Color;
import android.util.Log;
import com.amap.api.maps.AMapException;
import com.autonavi.amap.mapcore.MapTilsCacheAndResManager;
import java.util.HashMap;

public class Gradient {
    private int f848a;
    private int[] f849b;
    private float[] f850c;
    private boolean f851d;

    private class C0299a {
        final /* synthetic */ Gradient f844a;
        private final int f845b;
        private final int f846c;
        private final float f847d;

        private C0299a(Gradient gradient, int i, int i2, float f) {
            this.f844a = gradient;
            this.f845b = i;
            this.f846c = i2;
            this.f847d = f;
        }
    }

    public Gradient(int[] iArr, float[] fArr) {
        this(iArr, fArr, 1000);
    }

    private Gradient(int[] iArr, float[] fArr, int i) {
        int i2 = 1;
        this.f851d = true;
        if (iArr == null || fArr == null) {
            try {
                throw new AMapException("colors and startPoints should not be null");
            } catch (AMapException e) {
                this.f851d = false;
                Log.e(MapTilsCacheAndResManager.AUTONAVI_PATH, e.getErrorMessage());
                e.printStackTrace();
            }
        } else if (iArr.length != fArr.length) {
            throw new AMapException("colors and startPoints should be same length");
        } else if (iArr.length == 0) {
            throw new AMapException("No colors have been defined");
        } else {
            while (i2 < fArr.length) {
                if (fArr[i2] <= fArr[i2 - 1]) {
                    throw new AMapException("startPoints should be in increasing order");
                }
                i2++;
            }
            this.f848a = i;
            this.f849b = new int[iArr.length];
            this.f850c = new float[fArr.length];
            System.arraycopy(iArr, 0, this.f849b, 0, iArr.length);
            System.arraycopy(fArr, 0, this.f850c, 0, fArr.length);
            this.f851d = true;
        }
    }

    private HashMap<Integer, C0299a> m1083a() {
        HashMap<Integer, C0299a> hashMap = new HashMap();
        if (this.f850c[0] != 0.0f) {
            int argb = Color.argb(0, Color.red(this.f849b[0]), Color.green(this.f849b[0]), Color.blue(this.f849b[0]));
            hashMap.put(Integer.valueOf(0), new C0299a(argb, this.f849b[0], this.f850c[0] * ((float) this.f848a)));
        }
        for (int i = 1; i < this.f849b.length; i++) {
            hashMap.put(Integer.valueOf((int) (((float) this.f848a) * this.f850c[i - 1])), new C0299a(this.f849b[i - 1], this.f849b[i], (this.f850c[i] - this.f850c[i - 1]) * ((float) this.f848a)));
        }
        if (this.f850c[this.f850c.length - 1] != 1.0f) {
            int length = this.f850c.length - 1;
            hashMap.put(Integer.valueOf((int) (((float) this.f848a) * this.f850c[length])), new C0299a(this.f849b[length], this.f849b[length], ((float) this.f848a) * (1.0f - this.f850c[length])));
        }
        return hashMap;
    }

    protected int[] generateColorMap(double d) {
        int i = 0;
        HashMap a = m1083a();
        int[] iArr = new int[this.f848a];
        int i2 = 0;
        C0299a c0299a = (C0299a) a.get(Integer.valueOf(0));
        int i3 = 0;
        while (i2 < this.f848a) {
            int i4;
            C0299a c0299a2;
            if (a.containsKey(Integer.valueOf(i2))) {
                i4 = i2;
                c0299a2 = (C0299a) a.get(Integer.valueOf(i2));
            } else {
                c0299a2 = c0299a;
                i4 = i3;
            }
            iArr[i2] = m1082a(c0299a2.f845b, c0299a2.f846c, ((float) (i2 - i4)) / c0299a2.f847d);
            i2++;
            i3 = i4;
            c0299a = c0299a2;
        }
        if (d != WeightedLatLng.DEFAULT_INTENSITY) {
            while (i < this.f848a) {
                i3 = iArr[i];
                iArr[i] = Color.argb((int) (((double) Color.alpha(i3)) * d), Color.red(i3), Color.green(i3), Color.blue(i3));
                i++;
            }
        }
        return iArr;
    }

    static int m1082a(int i, int i2, float f) {
        int i3 = 0;
        int alpha = (int) ((((float) (Color.alpha(i2) - Color.alpha(i))) * f) + ((float) Color.alpha(i)));
        float[] fArr = new float[3];
        Color.RGBToHSV(Color.red(i), Color.green(i), Color.blue(i), fArr);
        float[] fArr2 = new float[3];
        Color.RGBToHSV(Color.red(i2), Color.green(i2), Color.blue(i2), fArr2);
        if (fArr[0] - fArr2[0] > BitmapDescriptorFactory.HUE_CYAN) {
            fArr2[0] = fArr2[0] + 360.0f;
        } else if (fArr2[0] - fArr[0] > BitmapDescriptorFactory.HUE_CYAN) {
            fArr[0] = fArr[0] + 360.0f;
        }
        float[] fArr3 = new float[3];
        while (i3 < 3) {
            fArr3[i3] = ((fArr2[i3] - fArr[i3]) * f) + fArr[i3];
            i3++;
        }
        return Color.HSVToColor(alpha, fArr3);
    }

    protected boolean isAvailable() {
        return this.f851d;
    }
}
