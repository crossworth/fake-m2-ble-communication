package com.amap.api.services.geocoder;

public class RegeocodeResult {
    private RegeocodeQuery f1193a;
    private RegeocodeAddress f1194b;

    public RegeocodeResult(RegeocodeQuery regeocodeQuery, RegeocodeAddress regeocodeAddress) {
        this.f1193a = regeocodeQuery;
        this.f1194b = regeocodeAddress;
    }

    public RegeocodeQuery getRegeocodeQuery() {
        return this.f1193a;
    }

    public void setRegeocodeQuery(RegeocodeQuery regeocodeQuery) {
        this.f1193a = regeocodeQuery;
    }

    public RegeocodeAddress getRegeocodeAddress() {
        return this.f1194b;
    }

    public void setRegeocodeAddress(RegeocodeAddress regeocodeAddress) {
        this.f1194b = regeocodeAddress;
    }
}
