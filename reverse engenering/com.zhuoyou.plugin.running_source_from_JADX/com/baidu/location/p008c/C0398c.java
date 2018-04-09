package com.baidu.location.p008c;

import android.os.Message;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p012f.C0448d;

class C0398c implements BDLocationListener {
    final /* synthetic */ C0397b f512a;

    C0398c(C0397b c0397b) {
        this.f512a = c0397b;
    }

    public void onReceiveLocation(BDLocation bDLocation) {
        if (!(bDLocation == null || C0448d.m886a().m925i())) {
            bDLocation.setUserIndoorState(1);
            bDLocation.setIndoorNetworkState(this.f512a.f486O);
            C0332a.m176a().m181a(bDLocation);
        }
        if (bDLocation != null && bDLocation.getNetworkLocationType().equals("ml")) {
            Message obtainMessage = this.f512a.f489c.obtainMessage(801);
            obtainMessage.obj = bDLocation;
            obtainMessage.sendToTarget();
        }
    }
}
