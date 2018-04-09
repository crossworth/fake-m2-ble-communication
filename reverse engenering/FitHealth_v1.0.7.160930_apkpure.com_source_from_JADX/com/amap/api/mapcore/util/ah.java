package com.amap.api.mapcore.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: MapOverlayViewGroup */
class ah extends ViewGroup {
    private IAMapDelegate f156a;

    /* compiled from: MapOverlayViewGroup */
    public static class C0198a extends LayoutParams {
        public FPoint f152a = null;
        public int f153b = 0;
        public int f154c = 0;
        public int f155d = 51;

        public C0198a(int i, int i2, FPoint fPoint, int i3, int i4, int i5) {
            super(i, i2);
            this.f152a = fPoint;
            this.f153b = i3;
            this.f154c = i4;
            this.f155d = i5;
        }

        public C0198a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0198a(LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public ah(Context context) {
        super(context);
    }

    public ah(Context context, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f156a = iAMapDelegate;
        setBackgroundColor(-1);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null) {
                if (childAt.getLayoutParams() instanceof C0198a) {
                    m171a(childAt, (C0198a) childAt.getLayoutParams());
                } else {
                    m170a(childAt, childAt.getLayoutParams());
                }
            }
        }
    }

    private void m170a(View view, LayoutParams layoutParams) {
        int[] iArr = new int[2];
        m169a(view, layoutParams.width, layoutParams.height, iArr);
        if (view instanceof C0296z) {
            m168a(view, iArr[0], iArr[1], 20, (this.f156a.getWaterMarkerPositon().y - 80) - iArr[1], 51);
            return;
        }
        m168a(view, iArr[0], iArr[1], 0, 0, 51);
    }

    private void m171a(View view, C0198a c0198a) {
        int[] iArr = new int[2];
        m169a(view, c0198a.width, c0198a.height, iArr);
        if (view instanceof ba) {
            m168a(view, iArr[0], iArr[1], getWidth() - iArr[0], getHeight(), c0198a.f155d);
        } else if (view instanceof aa) {
            m168a(view, iArr[0], iArr[1], getWidth() - iArr[0], iArr[1], c0198a.f155d);
        } else if (view instanceof C0271q) {
            m168a(view, iArr[0], iArr[1], 0, 0, c0198a.f155d);
        } else if (c0198a.f152a != null) {
            IPoint iPoint = new IPoint();
            this.f156a.getMapProjection().map2Win(c0198a.f152a.f2028x, c0198a.f152a.f2029y, iPoint);
            iPoint.f2030x += c0198a.f153b;
            iPoint.f2031y += c0198a.f154c;
            m168a(view, iArr[0], iArr[1], iPoint.f2030x, iPoint.f2031y, c0198a.f155d);
            if (view.getVisibility() == 0) {
                mo1481a();
            }
        }
    }

    protected void mo1481a() {
    }

    private void m169a(View view, int i, int i2, int[] iArr) {
        if (view instanceof ListView) {
            View view2 = (View) view.getParent();
            if (view2 != null) {
                iArr[0] = view2.getWidth();
                iArr[1] = view2.getHeight();
            }
        }
        if (i <= 0 || i2 <= 0) {
            view.measure(0, 0);
        }
        if (i == -2) {
            iArr[0] = view.getMeasuredWidth();
        } else if (i == -1) {
            iArr[0] = getMeasuredWidth();
        } else {
            iArr[0] = i;
        }
        if (i2 == -2) {
            iArr[1] = view.getMeasuredHeight();
        } else if (i2 == -1) {
            iArr[1] = getMeasuredHeight();
        } else {
            iArr[1] = i2;
        }
    }

    private void m168a(View view, int i, int i2, int i3, int i4, int i5) {
        int i6 = i5 & 7;
        int i7 = i5 & 112;
        if (i6 == 5) {
            i3 -= i;
        } else if (i6 == 1) {
            i3 -= i / 2;
        }
        if (i7 == 80) {
            i4 -= i2;
        } else if (i7 == 17) {
            i4 -= i2 / 2;
        } else if (i7 == 16) {
            i4 = (i4 / 2) - (i2 / 2);
        }
        view.layout(i3, i4, i3 + i, i4 + i2);
    }
}
