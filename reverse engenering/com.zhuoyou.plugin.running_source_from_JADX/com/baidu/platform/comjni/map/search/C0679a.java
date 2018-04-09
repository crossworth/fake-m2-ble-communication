package com.baidu.platform.comjni.map.search;

import android.os.Bundle;

public class C0679a {
    private static final String f2243a = C0679a.class.getSimpleName();
    private long f2244b;
    private JNISearch f2245c;

    public C0679a() {
        this.f2244b = 0;
        this.f2245c = null;
        this.f2245c = new JNISearch();
    }

    public long m2280a() {
        this.f2244b = this.f2245c.Create();
        return this.f2244b;
    }

    public String m2281a(int i) {
        return this.f2245c.GetSearchResult(this.f2244b, i);
    }

    public boolean m2282a(int i, int i2) {
        return this.f2245c.ReverseGeocodeSearch(this.f2244b, i, i2);
    }

    public boolean m2283a(int i, int i2, String str, String str2) {
        return this.f2245c.PoiRGCShareUrlSearch(this.f2244b, i, i2, str, str2);
    }

    public boolean m2284a(Bundle bundle) {
        return this.f2245c.ForceSearchByCityName(this.f2244b, bundle);
    }

    public boolean m2285a(String str) {
        return this.f2245c.POIDetailSearchPlace(this.f2244b, str);
    }

    public boolean m2286a(String str, String str2) {
        return this.f2245c.BusLineDetailSearch(this.f2244b, str, str2);
    }

    public int m2287b() {
        return this.f2245c.Release(this.f2244b);
    }

    public boolean m2288b(Bundle bundle) {
        return this.f2245c.AreaSearch(this.f2244b, bundle);
    }

    public boolean m2289b(String str) {
        return this.f2245c.PoiDetailShareUrlSearch(this.f2244b, str);
    }

    public boolean m2290b(String str, String str2) {
        return this.f2245c.geocode(this.f2244b, str, str2);
    }

    public boolean m2291c(Bundle bundle) {
        return this.f2245c.indoorSearch(this.f2244b, bundle);
    }

    public boolean m2292c(String str, String str2) {
        return this.f2245c.districtSearch(this.f2244b, str, str2);
    }

    public boolean m2293d(Bundle bundle) {
        return this.f2245c.RoutePlanByBus(this.f2244b, bundle);
    }

    public boolean m2294e(Bundle bundle) {
        return this.f2245c.RoutePlanByTransit(this.f2244b, bundle);
    }

    public boolean m2295f(Bundle bundle) {
        return this.f2245c.RoutePlanByCar(this.f2244b, bundle);
    }

    public boolean m2296g(Bundle bundle) {
        return this.f2245c.RoutePlanByFoot(this.f2244b, bundle);
    }

    public boolean m2297h(Bundle bundle) {
        return this.f2245c.routePlanByBike(this.f2244b, bundle);
    }

    public boolean m2298i(Bundle bundle) {
        return this.f2245c.routePlanIndoor(this.f2244b, bundle);
    }

    public boolean m2299j(Bundle bundle) {
        return this.f2245c.SuggestionSearch(this.f2244b, bundle);
    }

    public boolean m2300k(Bundle bundle) {
        return this.f2245c.routeShareUrlSearch(this.f2244b, bundle);
    }

    public boolean m2301l(Bundle bundle) {
        return this.f2245c.MapBoundSearch(this.f2244b, bundle);
    }
}
