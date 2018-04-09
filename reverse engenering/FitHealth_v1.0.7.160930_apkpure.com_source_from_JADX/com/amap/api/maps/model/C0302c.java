package com.amap.api.maps.model;

import com.amap.api.mapcore.util.cy;
import com.autonavi.amap.mapcore.DPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: PointQuadTree */
class C0302c {
    private final cy f973a;
    private final int f974b;
    private List<WeightedLatLng> f975c;
    private List<C0302c> f976d;

    protected C0302c(cy cyVar) {
        this(cyVar, 0);
    }

    private C0302c(double d, double d2, double d3, double d4, int i) {
        this(new cy(d, d2, d3, d4), i);
    }

    private C0302c(cy cyVar, int i) {
        this.f976d = null;
        this.f973a = cyVar;
        this.f974b = i;
    }

    protected void m1107a(WeightedLatLng weightedLatLng) {
        DPoint point = weightedLatLng.getPoint();
        if (this.f973a.m473a(point.f2026x, point.f2027y)) {
            m1104a(point.f2026x, point.f2027y, weightedLatLng);
        }
    }

    private void m1104a(double d, double d2, WeightedLatLng weightedLatLng) {
        if (this.f976d == null) {
            if (this.f975c == null) {
                this.f975c = new ArrayList();
            }
            this.f975c.add(weightedLatLng);
            if (this.f975c.size() > 50 && this.f974b < 40) {
                m1103a();
            }
        } else if (d2 < this.f973a.f411f) {
            if (d < this.f973a.f410e) {
                ((C0302c) this.f976d.get(0)).m1104a(d, d2, weightedLatLng);
            } else {
                ((C0302c) this.f976d.get(1)).m1104a(d, d2, weightedLatLng);
            }
        } else if (d < this.f973a.f410e) {
            ((C0302c) this.f976d.get(2)).m1104a(d, d2, weightedLatLng);
        } else {
            ((C0302c) this.f976d.get(3)).m1104a(d, d2, weightedLatLng);
        }
    }

    private void m1103a() {
        this.f976d = new ArrayList(4);
        this.f976d.add(new C0302c(this.f973a.f406a, this.f973a.f410e, this.f973a.f407b, this.f973a.f411f, this.f974b + 1));
        this.f976d.add(new C0302c(this.f973a.f410e, this.f973a.f408c, this.f973a.f407b, this.f973a.f411f, this.f974b + 1));
        this.f976d.add(new C0302c(this.f973a.f406a, this.f973a.f410e, this.f973a.f411f, this.f973a.f409d, this.f974b + 1));
        this.f976d.add(new C0302c(this.f973a.f410e, this.f973a.f408c, this.f973a.f411f, this.f973a.f409d, this.f974b + 1));
        List<WeightedLatLng> list = this.f975c;
        this.f975c = null;
        for (WeightedLatLng weightedLatLng : list) {
            m1104a(weightedLatLng.getPoint().f2026x, weightedLatLng.getPoint().f2027y, weightedLatLng);
        }
    }

    protected Collection<WeightedLatLng> m1106a(cy cyVar) {
        Collection<WeightedLatLng> arrayList = new ArrayList();
        m1105a(cyVar, arrayList);
        return arrayList;
    }

    private void m1105a(cy cyVar, Collection<WeightedLatLng> collection) {
        if (!this.f973a.m475a(cyVar)) {
            return;
        }
        if (this.f976d != null) {
            for (C0302c a : this.f976d) {
                a.m1105a(cyVar, collection);
            }
        } else if (this.f975c == null) {
        } else {
            if (cyVar.m477b(this.f973a)) {
                collection.addAll(this.f975c);
                return;
            }
            for (WeightedLatLng weightedLatLng : this.f975c) {
                if (cyVar.m476a(weightedLatLng.getPoint())) {
                    collection.add(weightedLatLng);
                }
            }
        }
    }
}
