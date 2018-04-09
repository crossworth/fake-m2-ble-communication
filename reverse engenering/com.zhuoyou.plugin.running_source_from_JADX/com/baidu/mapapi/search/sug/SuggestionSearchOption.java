package com.baidu.mapapi.search.sug;

import com.baidu.mapapi.model.LatLng;

public class SuggestionSearchOption {
    String f1820a = null;
    String f1821b = null;
    LatLng f1822c = null;

    public SuggestionSearchOption city(String str) {
        this.f1820a = str;
        return this;
    }

    public SuggestionSearchOption keyword(String str) {
        this.f1821b = str;
        return this;
    }

    public SuggestionSearchOption location(LatLng latLng) {
        this.f1822c = latLng;
        return this;
    }
}
