package com.amap.api.services.poisearch;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import java.util.ArrayList;
import java.util.List;

public final class PoiResult {
    private int f1244a;
    private ArrayList<PoiItem> f1245b = new ArrayList();
    private Query f1246c;
    private SearchBound f1247d;
    private List<String> f1248e;
    private List<SuggestionCity> f1249f;
    private int f1250g;

    public static PoiResult createPagedResult(Query query, SearchBound searchBound, List<String> list, List<SuggestionCity> list2, int i, int i2, ArrayList<PoiItem> arrayList) {
        return new PoiResult(query, searchBound, list, list2, i, i2, arrayList);
    }

    private PoiResult(Query query, SearchBound searchBound, List<String> list, List<SuggestionCity> list2, int i, int i2, ArrayList<PoiItem> arrayList) {
        this.f1246c = query;
        this.f1247d = searchBound;
        this.f1248e = list;
        this.f1249f = list2;
        this.f1250g = i;
        this.f1244a = m1194a(i2);
        this.f1245b = arrayList;
    }

    public int getPageCount() {
        return this.f1244a;
    }

    public Query getQuery() {
        return this.f1246c;
    }

    public SearchBound getBound() {
        return this.f1247d;
    }

    public ArrayList<PoiItem> getPois() {
        return this.f1245b;
    }

    public List<String> getSearchSuggestionKeywords() {
        return this.f1248e;
    }

    public List<SuggestionCity> getSearchSuggestionCitys() {
        return this.f1249f;
    }

    private int m1194a(int i) {
        int i2 = ((this.f1250g + i) - 1) / this.f1250g;
        if (i2 > 30) {
            return 30;
        }
        return i2;
    }
}
