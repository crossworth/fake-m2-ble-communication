package com.umeng.socialize.shareboard;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.umeng.socialize.shareboard.p027b.C0977a;
import com.umeng.socialize.shareboard.p027b.C0978b;
import com.umeng.socialize.utils.Log;

/* compiled from: UMActionBoard */
public class C0980c extends RelativeLayout {
    private static final int f3357a = 150;
    private Context f3358b;
    private C0978b f3359c;
    private Animation f3360d;
    private View f3361e;

    public C0980c(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public C0980c(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public C0980c(Context context) {
        super(context);
        this.f3358b = context;
        m3243b();
    }

    private void m3243b() {
        this.f3359c = new C0978b(this.f3358b);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(12);
        this.f3359c.setLayoutParams(layoutParams);
        this.f3360d = new TranslateAnimation(0.0f, 0.0f, 80.0f, 0.0f);
        this.f3360d.setDuration(150);
        this.f3361e = new View(this.f3358b);
        layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(10);
        this.f3361e.setLayoutParams(layoutParams);
        this.f3361e.setBackgroundColor(Color.argb(50, 0, 0, 0));
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1500);
        this.f3361e.setAnimation(alphaAnimation);
        addView(this.f3361e);
        addView(this.f3359c);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        Log.m3248d("onMeasure", "ActionBoard, width = " + size + ", height = " + size2);
        LayoutParams layoutParams = this.f3359c.getLayoutParams();
        layoutParams.height = this.f3359c.m3242e(size);
        layoutParams.width = size;
        LayoutParams layoutParams2 = this.f3361e.getLayoutParams();
        layoutParams2.height = size2 - layoutParams.height;
        layoutParams2.width = size;
    }

    public void m3246a(C0977a c0977a) {
        this.f3359c.m3238a(c0977a);
        this.f3359c.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public void m3245a(OnClickListener onClickListener) {
        this.f3361e.setOnClickListener(onClickListener);
    }

    public void m3244a() {
        this.f3359c.startAnimation(this.f3360d);
    }
}
