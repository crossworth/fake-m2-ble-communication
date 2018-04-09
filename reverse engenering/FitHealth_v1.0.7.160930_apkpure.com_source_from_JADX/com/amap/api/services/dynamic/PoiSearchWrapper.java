package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IPoiSearch;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.amap.api.services.proguard.an;

public class PoiSearchWrapper implements IPoiSearch {
    private IPoiSearch f4270a = null;

    public PoiSearchWrapper(Context context, Query query) {
        this.f4270a = new an(context, query);
    }

    public void setOnPoiSearchListener(OnPoiSearchListener onPoiSearchListener) {
        if (this.f4270a != null) {
            this.f4270a.setOnPoiSearchListener(onPoiSearchListener);
        }
    }

    public void setLanguage(String str) {
        if (this.f4270a != null) {
            this.f4270a.setLanguage(str);
        }
    }

    public String getLanguage() {
        if (this.f4270a != null) {
            return this.f4270a.getLanguage();
        }
        return null;
    }

    public PoiResult searchPOI() throws AMapException {
        if (this.f4270a != null) {
            return this.f4270a.searchPOI();
        }
        return null;
    }

    public void searchPOIAsyn() {
        if (this.f4270a != null) {
            this.f4270a.searchPOIAsyn();
        }
    }

    public PoiItem searchPOIId(String str) throws AMapException {
        if (this.f4270a != null) {
            return this.f4270a.searchPOIId(str);
        }
        return null;
    }

    public void searchPOIIdAsyn(String str) {
        if (this.f4270a != null) {
            this.f4270a.searchPOIIdAsyn(str);
        }
    }

    public void setQuery(Query query) {
        if (this.f4270a != null) {
            this.f4270a.setQuery(query);
        }
    }

    public void setBound(SearchBound searchBound) {
        if (this.f4270a != null) {
            this.f4270a.setBound(searchBound);
        }
    }

    public Query getQuery() {
        if (this.f4270a != null) {
            return this.f4270a.getQuery();
        }
        return null;
    }

    public SearchBound getBound() {
        if (this.f4270a != null) {
            return this.f4270a.getBound();
        }
        return null;
    }
}
