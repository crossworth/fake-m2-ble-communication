package com.droi.sdk.selfupdate;

import android.content.Context;
import java.io.File;

class C1034i implements DroiDownloadListener {
    final /* synthetic */ Context f3431a;
    final /* synthetic */ C1032g f3432b;

    C1034i(C1032g c1032g, Context context) {
        this.f3432b = c1032g;
        this.f3431a = context;
    }

    public void onStart(long j) {
    }

    public void onProgress(float f) {
    }

    public void onPatching() {
    }

    public void onFinished(File file) {
        C1032g.m3203a(this.f3431a, file, 3);
    }

    public void onFailed(int i) {
    }
}
