package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

/* compiled from: AMapDelegateImp */
class C0253f extends Handler {
    final /* synthetic */ C1592c f579a;

    C0253f(C1592c c1592c) {
        this.f579a = c1592c;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        try {
            if (this.f579a.ac != null) {
                this.f579a.ac.onTouch((MotionEvent) message.obj);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "onTouchHandler");
            th.printStackTrace();
        }
    }
}
