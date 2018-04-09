package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.Message;

/* compiled from: UiSettingsDelegateImp */
class ay extends Handler {
    final /* synthetic */ ax f205a;

    ay(ax axVar) {
        this.f205a = axVar;
    }

    public void handleMessage(Message message) {
        if (message != null) {
            switch (message.what) {
                case 0:
                    this.f205a.f4027b.showZoomControlsEnabled(this.f205a.f4033h);
                    return;
                case 1:
                    this.f205a.f4027b.showScaleEnabled(this.f205a.f4035j);
                    return;
                case 2:
                    this.f205a.f4027b.showCompassEnabled(this.f205a.f4034i);
                    return;
                case 3:
                    this.f205a.f4027b.showMyLocationButtonEnabled(this.f205a.f4031f);
                    return;
                case 4:
                    this.f205a.f4027b.showIndoorSwitchControlsEnabled(this.f205a.f4038m);
                    return;
                default:
                    return;
            }
        }
    }
}
