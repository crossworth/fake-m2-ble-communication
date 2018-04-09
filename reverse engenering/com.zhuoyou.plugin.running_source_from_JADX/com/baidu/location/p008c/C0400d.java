package com.baidu.location.p008c;

import com.baidu.location.BDLocation;
import com.baidu.location.p008c.C0401e.C0399a;
import com.baidu.location.p008c.p009a.C0386d;
import com.baidu.location.p012f.C0448d;
import java.util.Date;

class C0400d implements C0399a {
    final /* synthetic */ C0397b f513a;

    C0400d(C0397b c0397b) {
        this.f513a = c0397b;
    }

    public void mo1749a(double d, double d2) {
        this.f513a.f487a = true;
        this.f513a.f488b = true;
        this.f513a.f482K = 0.5d;
        if (this.f513a.f480I > 0.1d || this.f513a.f481J > 0.1d) {
            double[] a = this.f513a.m550a(this.f513a.f481J, this.f513a.f480I, d, d2);
            try {
                BDLocation bDLocation = new BDLocation();
                bDLocation.setLocType(161);
                bDLocation.setLatitude(a[0]);
                bDLocation.setLongitude(a[1]);
                bDLocation.setRadius(15.0f);
                bDLocation.setTime(this.f513a.f490d.format(new Date()));
                this.f513a.f481J = a[0];
                this.f513a.f480I = a[1];
                if (this.f513a.f509x != null) {
                    bDLocation.setFloor(this.f513a.f509x);
                }
                if (this.f513a.f510y != null) {
                    bDLocation.setBuildingID(this.f513a.f510y);
                }
                if (this.f513a.f511z != null) {
                    bDLocation.setBuildingName(this.f513a.f511z);
                }
                bDLocation.setParkAvailable(this.f513a.f474C);
                if (this.f513a.f473B != null) {
                    bDLocation.setNetworkLocationType(this.f513a.f473B);
                } else {
                    bDLocation.setNetworkLocationType("wf");
                }
                if (this.f513a.f502q && !C0448d.m886a().m925i()) {
                    bDLocation.setIndoorLocMode(true);
                    this.f513a.f507v = this.f513a.f507v + 1;
                    if (this.f513a.f507v < 60 && this.f513a.f497l.m622d() % 3 == 0) {
                        bDLocation.setNetworkLocationType("dr");
                        BDLocation bDLocation2 = new BDLocation(bDLocation);
                        if (C0386d.m487a().m500a(bDLocation2)) {
                            this.f513a.m545a(bDLocation2, 21);
                            return;
                        }
                        bDLocation2.setNetworkLocationType("dr2");
                        this.f513a.m545a(bDLocation2, 21);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
