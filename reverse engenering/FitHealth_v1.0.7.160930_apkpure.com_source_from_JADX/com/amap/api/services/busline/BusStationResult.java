package com.amap.api.services.busline;

import com.amap.api.services.core.SuggestionCity;
import java.util.ArrayList;
import java.util.List;

public final class BusStationResult {
    private int f1059a;
    private ArrayList<BusStationItem> f1060b = new ArrayList();
    private BusStationQuery f1061c;
    private List<String> f1062d = new ArrayList();
    private List<SuggestionCity> f1063e = new ArrayList();

    public static BusStationResult createPagedResult(BusStationQuery busStationQuery, int i, List<SuggestionCity> list, List<String> list2, ArrayList<BusStationItem> arrayList) {
        return new BusStationResult(busStationQuery, i, list, list2, arrayList);
    }

    private BusStationResult(BusStationQuery busStationQuery, int i, List<SuggestionCity> list, List<String> list2, ArrayList<BusStationItem> arrayList) {
        this.f1061c = busStationQuery;
        this.f1059a = m1142a(i);
        this.f1063e = list;
        this.f1062d = list2;
        this.f1060b = arrayList;
    }

    private int m1142a(int i) {
        int pageSize = this.f1061c.getPageSize();
        pageSize = ((i + pageSize) - 1) / pageSize;
        if (pageSize > 30) {
            return 30;
        }
        return pageSize;
    }

    public int getPageCount() {
        return this.f1059a;
    }

    public BusStationQuery getQuery() {
        return this.f1061c;
    }

    public List<String> getSearchSuggestionKeywords() {
        return this.f1062d;
    }

    public List<SuggestionCity> getSearchSuggestionCities() {
        return this.f1063e;
    }

    public List<BusStationItem> getBusStations() {
        return this.f1060b;
    }
}
