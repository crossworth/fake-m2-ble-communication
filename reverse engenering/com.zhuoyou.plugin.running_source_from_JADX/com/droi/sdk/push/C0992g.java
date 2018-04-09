package com.droi.sdk.push;

import android.graphics.Bitmap;
import com.droi.sdk.DroiError;
import com.droi.sdk.utility.BitmapBackgroundCallback;

class C0992g implements BitmapBackgroundCallback {
    final /* synthetic */ String f3307a;
    final /* synthetic */ C0991f f3308b;

    C0992g(C0991f c0991f, String str) {
        this.f3308b = c0991f;
        this.f3307a = str;
    }

    public void result(boolean z, Bitmap bitmap, DroiError droiError) {
        this.f3308b.m3051a(this.f3307a, z);
    }
}
