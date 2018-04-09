package com.tencent.open;

import android.location.Location;
import com.tencent.map.p011a.p012a.C0715b;
import com.tencent.map.p011a.p012a.C0717d;
import com.tencent.open.C0810d.C0809a;
import com.tencent.p004a.p005a.C1711d;

/* compiled from: ProGuard */
public class C1729f extends C0715b {
    private C0809a f4677a;

    public C1729f(C0809a c0809a) {
        super(1, 0, 0, 8);
        this.f4677a = c0809a;
    }

    public void mo2140a(byte[] bArr, int i) {
        super.mo2140a(bArr, i);
        C1711d.m4639c("openSDK_LOG", "location: onLocationDataUpdate = " + bArr);
    }

    public void mo2139a(C0717d c0717d) {
        C1711d.m4639c("openSDK_LOG", "location: onLocationUpdate = " + c0717d);
        super.mo2139a(c0717d);
        if (c0717d != null) {
            Location location = new Location("passive");
            location.setLatitude(c0717d.f2468b);
            location.setLongitude(c0717d.f2469c);
            if (this.f4677a != null) {
                this.f4677a.onLocationUpdate(location);
            }
        }
    }

    public void mo2138a(int i) {
        C1711d.m4639c("openSDK_LOG", "location: onStatusUpdate = " + i);
        super.mo2138a(i);
    }
}
