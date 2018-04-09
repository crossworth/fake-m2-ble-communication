package com.baidu.platform.comjni.map.search;

import android.os.Bundle;

public class JNISearch {
    public native boolean AreaSearch(long j, Bundle bundle);

    public native boolean BusLineDetailSearch(long j, String str, String str2);

    public native long Create();

    public native boolean ForceSearchByCityName(long j, Bundle bundle);

    public native String GetSearchResult(long j, int i);

    public native boolean MapBoundSearch(long j, Bundle bundle);

    public native boolean POIDetailSearchPlace(long j, String str);

    public native boolean PoiDetailShareUrlSearch(long j, String str);

    public native boolean PoiRGCShareUrlSearch(long j, int i, int i2, String str, String str2);

    public native int Release(long j);

    public native boolean ReverseGeocodeSearch(long j, int i, int i2);

    public native boolean RoutePlanByBus(long j, Bundle bundle);

    public native boolean RoutePlanByCar(long j, Bundle bundle);

    public native boolean RoutePlanByFoot(long j, Bundle bundle);

    public native boolean RoutePlanByTransit(long j, Bundle bundle);

    public native boolean SuggestionSearch(long j, Bundle bundle);

    public native boolean districtSearch(long j, String str, String str2);

    public native boolean geocode(long j, String str, String str2);

    public native boolean indoorSearch(long j, Bundle bundle);

    public native boolean routePlanByBike(long j, Bundle bundle);

    public native boolean routePlanIndoor(long j, Bundle bundle);

    public native boolean routeShareUrlSearch(long j, Bundle bundle);
}
