package com.tencent.open;

import android.location.Location;
import com.tencent.map.p026a.p027a.C1191b;
import com.tencent.map.p026a.p027a.C1193d;
import com.tencent.open.C1335c.C1275a;
import com.tencent.open.p036a.C1314f;

/* compiled from: ProGuard */
public class C1336d extends C1191b {
    private C1275a f4178a;

    public C1336d(C1275a c1275a) {
        super(1, 0, 0, 8);
        this.f4178a = c1275a;
    }

    public void mo2211a(byte[] bArr, int i) {
        super.mo2211a(bArr, i);
    }

    public void mo2210a(C1193d c1193d) {
        C1314f.m3870c("openSDK_LOG.SosoLocationListener", "location: onLocationUpdate = " + c1193d);
        super.mo2210a(c1193d);
        if (c1193d != null) {
            Location location = new Location("passive");
            location.setLatitude(c1193d.f3723b);
            location.setLongitude(c1193d.f3724c);
            if (this.f4178a != null) {
                this.f4178a.onLocationUpdate(location);
            }
        }
    }

    public void mo2209a(int i) {
        C1314f.m3870c("openSDK_LOG.SosoLocationListener", "location: onStatusUpdate = " + i);
        super.mo2209a(i);
    }
}
