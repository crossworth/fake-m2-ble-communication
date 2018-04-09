package com.baidu.mapapi.map;

import android.os.Bundle;
import com.baidu.mapapi.map.Overlay.C0477a;

class C0481a implements C0477a {
    final /* synthetic */ BaiduMap f1411a;

    C0481a(BaiduMap baiduMap) {
        this.f1411a = baiduMap;
    }

    public void mo1768a(Overlay overlay) {
        if (overlay != null && this.f1411a.f1025k.contains(overlay)) {
            Bundle a = overlay.mo1766a();
            if (this.f1411a.f1023i != null) {
                this.f1411a.f1023i.m2000d(a);
            }
            this.f1411a.f1025k.remove(overlay);
        }
        if (overlay != null && this.f1411a.f1027m.contains(overlay)) {
            this.f1411a.f1027m.remove(overlay);
        }
        if (overlay != null && this.f1411a.f1026l.contains(overlay)) {
            Marker marker = (Marker) overlay;
            if (marker.f1220n != null) {
                this.f1411a.f1026l.remove(marker);
                if (this.f1411a.f1026l.size() == 0 && this.f1411a.f1023i != null) {
                    this.f1411a.f1023i.m1994b(false);
                }
            }
        }
    }

    public void mo1769b(Overlay overlay) {
        if (overlay != null && this.f1411a.f1025k.contains(overlay)) {
            if (overlay instanceof Marker) {
                Marker marker = (Marker) overlay;
                if (!(marker.f1220n == null || marker.f1220n.size() == 0)) {
                    if (this.f1411a.f1026l.contains(marker)) {
                        this.f1411a.f1026l.remove(marker);
                    }
                    this.f1411a.f1026l.add(marker);
                    if (this.f1411a.f1023i != null) {
                        this.f1411a.f1023i.m1994b(true);
                    }
                }
            }
            Bundle bundle = new Bundle();
            if (this.f1411a.f1023i != null) {
                this.f1411a.f1023i.m1995c(overlay.mo1759a(bundle));
            }
        }
        if (this.f1411a.f1027m.contains(overlay)) {
            this.f1411a.f1027m.remove(overlay);
        }
        if (overlay instanceof Marker) {
            this.f1411a.f1027m.add((Marker) overlay);
        }
    }
}
