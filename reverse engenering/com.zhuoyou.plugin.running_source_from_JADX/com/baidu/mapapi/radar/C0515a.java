package com.baidu.mapapi.radar;

import android.os.Handler;
import android.os.Message;

class C0515a extends Handler {
    final /* synthetic */ RadarSearchManager f1497a;

    C0515a(RadarSearchManager radarSearchManager) {
        this.f1497a = radarSearchManager;
    }

    public void handleMessage(Message message) {
        if (RadarSearchManager.f1485a != null) {
            switch (message.what) {
                case 1:
                    if (this.f1497a.f1493h != null) {
                        RadarUploadInfo onUploadInfoCallback = this.f1497a.f1493h.onUploadInfoCallback();
                        if (onUploadInfoCallback != null) {
                            this.f1497a.f1494i = onUploadInfoCallback;
                        }
                        this.f1497a.m1384a(onUploadInfoCallback);
                        break;
                    }
                    break;
            }
            super.handleMessage(message);
        }
    }
}
