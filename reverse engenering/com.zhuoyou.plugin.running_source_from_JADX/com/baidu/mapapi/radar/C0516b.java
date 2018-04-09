package com.baidu.mapapi.radar;

import android.os.Message;
import java.util.TimerTask;

class C0516b extends TimerTask {
    final /* synthetic */ RadarSearchManager f1498a;

    C0516b(RadarSearchManager radarSearchManager) {
        this.f1498a = radarSearchManager;
    }

    public void run() {
        Message message = new Message();
        message.what = 1;
        this.f1498a.f1492g.sendMessage(message);
    }
}
