package com.droi.sdk.selfupdate;

import android.os.Message;
import com.droi.sdk.internal.DroiLog;
import java.io.File;

class C1027d implements DroiDownloadListener {
    final /* synthetic */ C1026c f3414a;

    C1027d(C1026c c1026c) {
        this.f3414a = c1026c;
    }

    public void onStart(long j) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = 0;
        this.f3414a.f3413d.f3388e.sendMessage(message);
    }

    public void onProgress(float f) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = (int) (100.0f * f);
        this.f3414a.f3413d.f3388e.sendMessage(message);
    }

    public void onPatching() {
        this.f3414a.f3413d.f3388e.sendEmptyMessage(3);
    }

    public void onFinished(File file) {
        this.f3414a.f3413d.f3391i = file;
        this.f3414a.f3413d.f3388e.sendEmptyMessage(1);
    }

    public void onFailed(int i) {
        DroiLog.m2871i(DroiUpdateDialogActivity.f3384f, "onFailed:" + i);
        Message message = new Message();
        message.what = 2;
        message.arg1 = i;
        this.f3414a.f3413d.f3388e.sendMessage(message);
    }
}
