package com.baidu.mapapi.map.offline;

import com.baidu.platform.comapi.map.C0499x;
import java.util.List;

class C0500a implements C0499x {
    final /* synthetic */ MKOfflineMap f1441a;

    C0500a(MKOfflineMap mKOfflineMap) {
        this.f1441a = mKOfflineMap;
    }

    public void mo1792a(int i, int i2) {
        switch (i) {
            case 4:
                List<MKOLUpdateElement> allUpdateInfo = this.f1441a.getAllUpdateInfo();
                if (allUpdateInfo != null) {
                    for (MKOLUpdateElement mKOLUpdateElement : allUpdateInfo) {
                        if (mKOLUpdateElement.update) {
                            this.f1441a.f1440c.onGetOfflineMapState(4, mKOLUpdateElement.cityID);
                        }
                    }
                    return;
                }
                return;
            case 6:
                this.f1441a.f1440c.onGetOfflineMapState(6, i2);
                return;
            case 8:
                this.f1441a.f1440c.onGetOfflineMapState(0, 65535 & (i2 >> 16));
                return;
            case 10:
                this.f1441a.f1440c.onGetOfflineMapState(2, i2);
                return;
            case 12:
                this.f1441a.f1439b.m2070a(true, false);
                return;
            default:
                return;
        }
    }
}
