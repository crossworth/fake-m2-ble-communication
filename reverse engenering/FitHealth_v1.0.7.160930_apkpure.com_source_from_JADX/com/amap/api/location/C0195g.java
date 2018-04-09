package com.amap.api.location;

/* compiled from: RequestLocationEntity */
public class C0195g {
    long f121a;
    public AMapLocationListener f122b;
    Boolean f123c;

    public C0195g(long j, float f, AMapLocationListener aMapLocationListener, String str, boolean z) {
        this.f121a = j;
        this.f122b = aMapLocationListener;
        this.f123c = Boolean.valueOf(z);
    }

    public int hashCode() {
        return (this.f122b == null ? 0 : this.f122b.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        C0195g c0195g = (C0195g) obj;
        if (this.f122b == null) {
            if (c0195g.f122b != null) {
                return false;
            }
            return true;
        } else if (this.f122b.equals(c0195g.f122b)) {
            return true;
        } else {
            return false;
        }
    }
}
