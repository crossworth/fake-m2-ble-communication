package com.umeng.socialize.shareboard.p027b;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.utils.Log;
import java.lang.reflect.Array;

/* compiled from: UMActionFrame */
public class C0978b extends ViewGroup {
    private static final int f3343a = 3;
    private static final int f3344b = 1;
    private static final int f3345c = 2;
    private int f3346d = 4;
    private int f3347e = 0;
    private int[][] f3348f = ((int[][]) null);
    private C0977a f3349g;
    private int f3350h;
    private int f3351i;
    private int f3352j = 0;
    private int f3353k = -1;
    private int f3354l = 2;
    private Context f3355m = null;

    public C0978b(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f3352j = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.f3355m = context;
    }

    public C0978b(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f3352j = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.f3355m = context;
    }

    public C0978b(Context context) {
        super(context);
        this.f3352j = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.f3355m = context;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.f3349g != null) {
            Context context = getContext();
            m3237a(this.f3349g.mo2202a());
            removeAllViews();
            int length = this.f3348f.length;
            int length2 = this.f3348f[0].length;
            int i5 = (this.f3351i - ((length - 1) * this.f3354l)) / length;
            int i6 = (this.f3350h - ((length2 - 1) * this.f3354l)) / length2;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            while (i8 < length2) {
                int i11 = 0;
                int i12 = i7;
                while (i11 < length) {
                    if (this.f3348f[i11][i8] == 1) {
                        i7 = i12 + 1;
                        View a = this.f3349g.mo2203a(i12, this);
                        LayoutParams layoutParams = a.getLayoutParams();
                        if (layoutParams == null) {
                            a.setLayoutParams(new LayoutParams(i5, i6));
                        } else {
                            layoutParams.height = i6;
                            layoutParams.width = i5;
                        }
                        Object obj = i11 == length + -1 ? 1 : null;
                        int i13 = (i11 * i5) + i10;
                        int i14 = i13 + i5;
                        int i15 = (i8 * i6) + i9;
                        int i16 = i15 + i6;
                        addView(a);
                        measureChild(a, i5, i6);
                        a.layout(i13, i15, i14, i16);
                        if (obj == null && this.f3348f[i11 + 1][i8] == 2) {
                            a = new View(context);
                            a.setBackgroundColor(this.f3353k);
                            addView(a);
                            a.layout(i13 + i5, i15, i3, i16);
                        }
                        a = new View(context);
                        if (obj == null) {
                            a.setBackgroundColor(this.f3352j);
                            i12 = this.f3354l + i10;
                        } else {
                            a.setBackgroundColor(this.f3353k);
                            i12 = 0;
                        }
                        addView(a);
                        a.layout(i13 + i5, i15, this.f3354l + i14, i16);
                        int i17 = i7;
                        i7 = i12;
                        i12 = i17;
                    } else {
                        i7 = i10;
                    }
                    i11++;
                    i10 = i7;
                }
                Object obj2 = i8 > 0 ? this.f3348f[0][i8 + -1] == 1 ? 1 : null : null;
                if (obj2 != null) {
                    View view = new View(context);
                    view.setBackgroundColor(this.f3352j);
                    addView(view);
                    i10 = (i8 * i6) + i9;
                    view.layout(i, i10 - this.f3354l, i3, i10);
                }
                i8++;
                i9 += this.f3354l;
                i7 = i12;
                i10 = 0;
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.f3350h = MeasureSpec.getSize(i2);
        this.f3351i = MeasureSpec.getSize(i);
        setMeasuredDimension(this.f3351i, this.f3350h);
    }

    public void m3237a(int i) {
        int length = this.f3348f.length;
        int length2 = this.f3348f[0].length;
        int i2 = length * length2;
        if (i > i2) {
            i = i2;
        }
        int i3 = i % length;
        int i4 = (i2 - i) - (i3 > 0 ? length - i3 : 0);
        int i5 = i4 + i;
        int i6 = 0;
        i2 = 0;
        while (i6 < length2) {
            i3 = i2;
            for (i2 = 0; i2 < length; i2++) {
                if (i3 >= i4 && i3 < i5) {
                    this.f3348f[i2][i6] = 1;
                } else if (i3 >= i5) {
                    this.f3348f[i2][i6] = 2;
                } else {
                    this.f3348f[i2][i6] = 3;
                }
                i3++;
            }
            i6++;
            i2 = i3;
        }
    }

    public C0977a m3236a() {
        return this.f3349g;
    }

    private void m3235b() {
        if (this.f3355m == null || this.f3349g == null) {
            this.f3348f = (int[][]) Array.newInstance(Integer.TYPE, new int[]{4, 2});
            return;
        }
        if (this.f3355m.getResources().getConfiguration().orientation == 2) {
            this.f3346d = 6;
        }
        int a = this.f3349g.mo2202a();
        this.f3347e = this.f3349g.mo2202a() / this.f3346d;
        if (a % this.f3346d > 0) {
            this.f3347e++;
        }
        Log.m3248d("", "###### row = " + this.f3347e + ", column = " + this.f3346d);
        this.f3348f = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.f3346d, this.f3347e});
    }

    public void m3238a(C0977a c0977a) {
        this.f3349g = c0977a;
        m3235b();
        requestLayout();
    }

    public void m3239b(int i) {
        this.f3352j = i;
    }

    public void m3240c(int i) {
        this.f3353k = i;
    }

    public void m3241d(int i) {
        this.f3354l = i;
    }

    public int m3242e(int i) {
        return (((i - ((this.f3346d - 1) * this.f3354l)) / this.f3346d) * this.f3347e) + ((this.f3347e - 1) * this.f3354l);
    }
}
