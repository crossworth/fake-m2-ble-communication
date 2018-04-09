package com.amap.api.services.cloud;

import com.amap.api.services.cloud.CloudSearch.Query;
import com.amap.api.services.cloud.CloudSearch.SearchBound;
import java.util.ArrayList;

public final class CloudResult {
    private int f1074a = m1149a(this.f1076c);
    private ArrayList<CloudItem> f1075b;
    private int f1076c;
    private int f1077d;
    private Query f1078e;
    private SearchBound f1079f;

    public static CloudResult createPagedResult(Query query, int i, SearchBound searchBound, int i2, ArrayList<CloudItem> arrayList) {
        return new CloudResult(query, i, searchBound, i2, arrayList);
    }

    private CloudResult(Query query, int i, SearchBound searchBound, int i2, ArrayList<CloudItem> arrayList) {
        this.f1078e = query;
        this.f1076c = i;
        this.f1077d = i2;
        this.f1075b = arrayList;
        this.f1079f = searchBound;
    }

    public int getPageCount() {
        return this.f1074a;
    }

    public Query getQuery() {
        return this.f1078e;
    }

    public SearchBound getBound() {
        return this.f1079f;
    }

    public ArrayList<CloudItem> getClouds() {
        return this.f1075b;
    }

    public int getTotalCount() {
        return this.f1076c;
    }

    private int m1149a(int i) {
        return ((this.f1077d + i) - 1) / this.f1077d;
    }
}
