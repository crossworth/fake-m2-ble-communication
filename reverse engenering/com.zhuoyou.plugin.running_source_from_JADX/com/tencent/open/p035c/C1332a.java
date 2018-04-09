package com.tencent.open.p035c;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

/* compiled from: ProGuard */
public class C1332a extends RelativeLayout {
    private static final String f4170a = C1332a.class.getName();
    private Rect f4171b = null;
    private boolean f4172c = false;
    private C1279a f4173d = null;

    /* compiled from: ProGuard */
    public interface C1279a {
        void onKeyboardHidden();

        void onKeyboardShown(int i);
    }

    public C1332a(Context context) {
        super(context);
        if (this.f4171b == null) {
            this.f4171b = new Rect();
        }
    }

    public void m3919a(C1279a c1279a) {
        this.f4173d = c1279a;
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i2);
        Activity activity = (Activity) getContext();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.f4171b);
        int height = (activity.getWindowManager().getDefaultDisplay().getHeight() - this.f4171b.top) - size;
        if (!(this.f4173d == null || size == 0)) {
            if (height > 100) {
                this.f4173d.onKeyboardShown((Math.abs(this.f4171b.height()) - getPaddingBottom()) - getPaddingTop());
            } else {
                this.f4173d.onKeyboardHidden();
            }
        }
        super.onMeasure(i, i2);
    }
}
