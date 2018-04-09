package com.tencent.open.p020b;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

/* compiled from: ProGuard */
public class C0805a extends RelativeLayout {
    private static final String f2738a = C0805a.class.getName();
    private Rect f2739b = null;
    private boolean f2740c = false;
    private C0804a f2741d = null;

    /* compiled from: ProGuard */
    public interface C0804a {
        void onKeyboardHidden();

        void onKeyboardShown(int i);
    }

    public C0805a(Context context) {
        super(context);
        if (this.f2739b == null) {
            this.f2739b = new Rect();
        }
    }

    public C0805a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (this.f2739b == null) {
            this.f2739b = new Rect();
        }
    }

    public void m2571a(C0804a c0804a) {
        this.f2741d = c0804a;
    }

    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i2);
        Activity activity = (Activity) getContext();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.f2739b);
        int height = (activity.getWindowManager().getDefaultDisplay().getHeight() - this.f2739b.top) - size;
        if (!(this.f2741d == null || size == 0)) {
            if (height > 100) {
                this.f2741d.onKeyboardShown((Math.abs(this.f2739b.height()) - getPaddingBottom()) - getPaddingTop());
            } else {
                this.f2741d.onKeyboardHidden();
            }
        }
        super.onMeasure(i, i2);
    }
}
