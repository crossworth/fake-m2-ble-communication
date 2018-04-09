package com.amap.api.mapcore.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.autonavi.amap.mapcore.interfaces.GLTextureView;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IGLSurfaceView;

/* compiled from: AMapGLTextureView */
public class C1605l extends GLTextureView implements IGLSurfaceView {
    private IAMapDelegate f4222a;

    public C1605l(Context context) {
        this(context, null);
    }

    public C1605l(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4222a = null;
        this.f4222a = new C1592c(this, context);
    }

    public IAMapDelegate m4303a() {
        return this.f4222a;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return this.f4222a.onTouchEvent(motionEvent);
    }

    public void setZOrderOnTop(boolean z) {
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 8 || i == 4) {
            this.f4222a.onPause();
        } else if (i == 0) {
            this.f4222a.onResume();
        }
    }

    protected void onDetachedFromWindow() {
        this.f4222a.onPause();
        super.onDetachedFromWindow();
    }
}
