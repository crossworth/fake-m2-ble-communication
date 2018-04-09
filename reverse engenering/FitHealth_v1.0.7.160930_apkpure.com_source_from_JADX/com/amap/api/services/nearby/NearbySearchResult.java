package com.amap.api.services.nearby;

import java.util.ArrayList;
import java.util.List;

public class NearbySearchResult {
    private List<NearbyInfo> f1236a = new ArrayList();
    private int f1237b = 0;

    public List<NearbyInfo> getNearbyInfoList() {
        return this.f1236a;
    }

    public int getTotalNum() {
        return this.f1237b;
    }

    public void setNearbyInfoList(List<NearbyInfo> list) {
        this.f1236a = list;
        this.f1237b = list.size();
    }
}
