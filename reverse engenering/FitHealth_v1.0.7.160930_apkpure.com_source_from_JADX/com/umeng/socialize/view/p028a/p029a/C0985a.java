package com.umeng.socialize.view.p028a.p029a;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FlowerDataCalc */
public class C0985a {
    private double[] f3380a;
    private double[] f3381b;

    public C0985a(int i) {
        this.f3380a = new double[i];
        this.f3381b = new double[i];
        double d = 6.283185307179586d / ((double) i);
        for (int i2 = 0; i2 < i; i2++) {
            double d2 = ((double) i2) * d;
            this.f3380a[i2] = Math.cos(d2);
            this.f3381b[i2] = Math.sin(d2);
        }
    }

    public List<C0986b> m3279a(int i, int i2, int i3, int i4, int i5) {
        List<C0986b> arrayList = new ArrayList(i4);
        double d = ((double) i) / 2.0d;
        double d2 = ((double) i5) / 2.0d;
        double d3 = ((double) (i - i2)) / 2.0d;
        double d4 = ((double) i3) / 2.0d;
        for (int i6 = 0; i6 < i4; i6++) {
            arrayList.add(new C0986b((int) (d2 - (this.f3380a[i6] * d3)), (int) ((this.f3381b[i6] * d3) + d), (int) (d2 - (this.f3380a[i6] * d4)), (int) ((this.f3381b[i6] * d4) + d)));
        }
        return arrayList;
    }

    public int[] m3280a(int i, int i2, int i3, int i4) {
        int[] iArr = new int[i3];
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        int red2 = Color.red(i2);
        double d = ((double) (red2 - red)) / ((double) (i3 - 1));
        double green2 = ((double) (Color.green(i2) - green)) / ((double) (i3 - 1));
        double blue2 = ((double) (Color.blue(i2) - blue)) / ((double) (i3 - 1));
        for (red2 = 0; red2 < i3; red2++) {
            int i5 = i4;
            iArr[red2] = Color.argb(i5, (int) (((double) red) + (((double) red2) * d)), (int) (((double) green) + (((double) red2) * green2)), (int) (((double) blue) + (((double) red2) * blue2)));
        }
        return iArr;
    }
}
