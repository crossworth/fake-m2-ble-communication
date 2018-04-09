package com.amap.api.services.busline;

import com.amap.api.services.core.SuggestionCity;
import java.util.ArrayList;
import java.util.List;

public final class BusLineResult {
    private int f1043a;
    private ArrayList<BusLineItem> f1044b = new ArrayList();
    private BusLineQuery f1045c;
    private List<String> f1046d = new ArrayList();
    private List<SuggestionCity> f1047e = new ArrayList();

    public static BusLineResult createPagedResult(BusLineQuery busLineQuery, int i, List<SuggestionCity> list, List<String> list2, ArrayList<BusLineItem> arrayList) {
        return new BusLineResult(busLineQuery, i, list, list2, arrayList);
    }

    private BusLineResult(BusLineQuery busLineQuery, int i, List<SuggestionCity> list, List<String> list2, ArrayList<BusLineItem> arrayList) {
        this.f1045c = busLineQuery;
        this.f1043a = m1137a(i);
        this.f1047e = list;
        this.f1046d = list2;
        this.f1044b = arrayList;
    }

    private int m1137a(int i) {
        int pageSize = this.f1045c.getPageSize();
        pageSize = ((i + pageSize) - 1) / pageSize;
        if (pageSize > 30) {
            return 30;
        }
        return pageSize;
    }

    public int getPageCount() {
        return this.f1043a;
    }

    public BusLineQuery getQuery() {
        return this.f1045c;
    }

    public List<String> getSearchSuggestionKeywords() {
        return this.f1046d;
    }

    public List<SuggestionCity> getSearchSuggestionCities() {
        return this.f1047e;
    }

    public List<BusLineItem> getBusLines() {
        return this.f1044b;
    }
}
