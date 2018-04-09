package com.amap.api.mapcore.util;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IGLSurfaceView;

/* compiled from: AMapGLSurfaceView */
public class C1604k extends GLSurfaceView implements IGLSurfaceView {
    private IAMapDelegate f4221a;

    public C1604k(Context context) {
        this(context, null);
    }

    public C1604k(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4221a = null;
        this.f4221a = new C1592c(this, context);
    }

    public IAMapDelegate m4302a() {
        return this.f4221a;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return this.f4221a.onTouchEvent(motionEvent);
    }

    protected void onWindowVisibilityChanged(int i) {
        if (i == 8 || i == 4) {
            this.f4221a.onPause();
        } else if (i == 0) {
            this.f4221a.onResume();
        }
        super.onWindowVisibilityChanged(i);
    }

    protected void onDetachedFromWindow() {
        this.f4221a.onPause();
        super.onDetachedFromWindow();
    }
}
